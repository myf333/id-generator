package com.myf.id.service;

import com.myf.id.bean.Id;
import com.myf.id.service.bean.IdType;
import com.myf.id.service.populater.IdPopulator;
import com.myf.id.service.populater.ResetPopulator;
import com.myf.id.service.provider.MachineIdsProvider;
import com.myf.id.service.util.TimeUtils;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by maoyf0503 on 2019-11-13.
 *
 * @author maoyf0503
 */
public class MachineIdsIdServiceImpl extends IdServiceImpl {
    private  long lastTimestamp = -1;
    private Map<Long, Long> machineIdMap = new ConcurrentHashMap<Long, Long>();
    private static final String STORE_FILE_NAME = "machineIdInfo.store";

    private String storeFilePath;
    private File storeFile;
    private Lock lock = new ReentrantLock();

    @Override
    public void init() {
        if (!(this.machineIdProvider instanceof MachineIdsProvider)) {
            logger.error("The machineIdProvider is not a MachineIdsProvider instance so that Vesta Service refuses to start.");
            throw new RuntimeException(
                    "The machineIdProvider is not a MachineIdsProvider instance so that Vesta Service refuses to start.");
        }
        super.init();
        initStoreFile();
        initMachineId();
    }

    @Override
    protected void populateId(Id id) {
        supportChangeMachineId(id);
    }

    private void supportChangeMachineId(Id id) {
        try {
            id.setMachine(this.machineId);
            idPopulator.populateId(id, this.idMeta);
            this.lastTimestamp = id.getTime();
        } catch (IllegalStateException e) {
            logger.warn("Clock moved backwards, change MachineId and reset IdPopulator");
            lock.lock();
            try {
                if (id.getMachine() == this.machineId) {
                    changeMachineId();
                    resetIdPopulator();
                }
            } finally {
                lock.unlock();
            }
            supportChangeMachineId(id);
        }
    }

    protected void changeMachineId() {
        this.machineIdMap.put(this.machineId, this.lastTimestamp);
        storeInFile();
        initMachineId();
    }

    protected void resetIdPopulator() {
        if (idPopulator instanceof ResetPopulator) {
            ((ResetPopulator) idPopulator).reset();
        } else {
            try {
                IdPopulator newIdPopulator = this.idPopulator.getClass().newInstance();
                this.idPopulator = newIdPopulator;
            } catch (InstantiationException e1) {
                throw new RuntimeException("Reset IdPopulator <[" + this.idPopulator.getClass().getCanonicalName() + "]> instance error", e1);
            } catch (IllegalAccessException e1) {
                throw new RuntimeException("Reset IdPopulator <[" + this.idPopulator.getClass().getCanonicalName() + "]> instance error", e1);
            }
        }
    }

    void initStoreFile() {
        if (storeFilePath == null || storeFilePath.length() == 0) {
            storeFilePath = System.getProperty("user.dir") + File.separator + STORE_FILE_NAME;
        }
        try {
            logger.info("machineId info store in <[" + storeFilePath + "]>");
            storeFile = new File(storeFilePath);
            if (storeFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(storeFile));
                String line = reader.readLine();
                while (line != null && line.length() > 0) {
                    String[] kvs = line.split(":");
                    if (kvs.length == 2) {
                        this.machineIdMap.put(Long.parseLong(kvs[0]), Long.parseLong(kvs[1]));
                    } else {
                        throw new IllegalArgumentException(storeFile.getAbsolutePath() + " has illegal value <[" + line + "]>");
                    }
                    line = reader.readLine();
                }
                reader.close();
            }
        } catch (FileNotFoundException e) {
            logger.error("machineId store file not find:{}",e.getMessage());
        } catch (IOException e) {
            logger.error("machineId store file can not read:{}",e.getMessage());
        }
    }

    void initMachineId() {
        long startId = this.machineId;
        long newMachineId = this.machineId;
        while(true) {
            if (this.machineIdMap.containsKey(newMachineId)) {
                long timestamp = TimeUtils.genTime(IdType.parse(this.type));
                if (this.machineIdMap.get(newMachineId) < timestamp) {
                    this.machineId = newMachineId;
                    break;
                } else {
                    newMachineId = ((MachineIdsProvider)this.machineIdProvider).getNextMachineId();
                }
                if(newMachineId == startId){
                    throw new RuntimeException("No machineId is available");
                }
            } else {
                this.machineId = newMachineId;
                break;
            }
        }
    }

    protected void storeInFile() {
        Writer writer = null;
        try {
            writer = new FileWriter(storeFile, false);
            for (Map.Entry<Long, Long> entry : this.machineIdMap.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            logger.error("Write machineId info to File<[" + storeFile.getAbsolutePath() + "]> error");
            throw new RuntimeException("Write machineId info to File<[" + storeFile.getAbsolutePath() + "]> error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void setStoreFilePath(String storeFilePath) {
        this.storeFilePath = storeFilePath;
    }
}
