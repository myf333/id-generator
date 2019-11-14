package com.myf.id.service.populater;

import com.myf.id.bean.Id;
import com.myf.id.service.bean.IdMeta;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by maoyf0503 on 2019-11-11.
 *
 * @author maoyf0503
 */
public class LockIdPopulator extends BasePopulator{
    private Lock lock = new ReentrantLock();
    public LockIdPopulator(){
        super();
    }

    @Override
    public void populateId(Id id, IdMeta idMeta) {
        lock.lock();
        try {
            super.populateId(id,idMeta);
        }finally {
            lock.unlock();
        }
    }
}
