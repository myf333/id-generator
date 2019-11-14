package com.myf.id.service;

import com.myf.id.bean.Id;
import com.myf.id.intf.IdService;
import com.myf.id.service.bean.IdMeta;
import com.myf.id.service.bean.IdMetaFactory;
import com.myf.id.service.bean.IdType;
import com.myf.id.service.converter.IdConverter;
import com.myf.id.service.converter.IdConverterImpl;
import com.myf.id.service.provider.MachineIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by maoyf0503 on 2019-11-7.
 *
 * @author maoyf0503
 */
public abstract class AbstractIdServiceImpl implements IdService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected long machineId = -1;
    protected long genMethod = 0;
    protected long type = 0;
    protected long version = 0;

    protected IdType idType;
    protected IdMeta idMeta;
    protected MachineIdProvider machineIdProvider;
    protected IdConverter idConverter;


    AbstractIdServiceImpl() {
        idType = IdType.MAX_PEAK;
    }

    AbstractIdServiceImpl(String type) {
        idType = IdType.parse(type);
    }

    AbstractIdServiceImpl(IdType type) {
        idType = type;
    }

    public void init(){
        machineId = machineIdProvider.getMachineId();
        if (machineId < 0) {
            logger.error("The machine ID is not configured properly so that Vesta Service refuses to start.");
            throw new IllegalStateException(
                    "The machine ID is not configured properly so that Vesta Service refuses to start.");
        }
        if(this.idMeta == null){
            setIdMeta(IdMetaFactory.getIdMeta(idType));
            setType(idType.value());
        } else {
            if(this.idMeta.getTimeBits() == 30){
                setType(0);
            } else if(this.idMeta.getTimeBits() == 40){
                setType(1);
            } else {
                throw new RuntimeException("Init Error. The time bits in IdMeta should be set to 30 or 40!");
            }
        }
        setIdConverter(new IdConverterImpl(this.idMeta));
    }

    public long genId() {
        Id id = new Id();
        id.setMachine(machineId);
        id.setGenMethod(genMethod);
        id.setType(type);
        id.setVersion(version);
        populateId(id);
        long ret = idConverter.convert(id);
        // Use trace because it cause low performance
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Id: %s => %d", id, ret));
        }
        return ret;
    }

    protected abstract void populateId(Id id);

    public Id expId(long id) {
        return idConverter.convert(id);
    }

    public void setIdMeta(IdMeta idMeta) {
        this.idMeta = idMeta;
    }

    public void setType(long type) {
        this.type = type;
    }
    public void setIdConverter(IdConverter idConverter) {
        this.idConverter = idConverter;
    }

    public void setMachineIdProvider(MachineIdProvider machineIdProvider) {
        this.machineIdProvider = machineIdProvider;
    }
}
