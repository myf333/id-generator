package com.myf.id.service.provider;

/**
 * Created by maoyf0503 on 2019-11-11.
 *
 * @author maoyf0503
 */
public class PropertyMachineIdProvider implements MachineIdProvider {
    private long machineId;

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
}
