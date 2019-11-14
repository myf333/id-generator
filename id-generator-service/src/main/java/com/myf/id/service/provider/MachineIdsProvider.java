package com.myf.id.service.provider;

/**
 * Created by maoyf0503 on 2019-11-11.
 *
 * @author maoyf0503
 */
public interface MachineIdsProvider extends MachineIdProvider {
    long getNextMachineId();
}
