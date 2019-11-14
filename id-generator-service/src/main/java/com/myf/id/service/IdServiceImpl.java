package com.myf.id.service;

import com.myf.id.bean.Id;
import com.myf.id.service.populater.AtomicIdPopulator;
import com.myf.id.service.populater.IdPopulator;
import com.myf.id.service.populater.LockIdPopulator;
import com.myf.id.service.populater.SyncIdPopulator;
import com.myf.id.service.util.CommonUtils;

/**
 * Created by maoyf0503 on 2019-11-11.
 *
 * @author maoyf0503
 */
public class IdServiceImpl extends AbstractIdServiceImpl {
    private static final String SYNC_LOCK_IMPL_KEY = "vesta.sync.lock.impl.key";
    private static final String ATOMIC_IMPL_KEY = "vesta.atomic.impl.key";

    protected IdPopulator idPopulator;

    public IdServiceImpl() {
        super();
        initPopulator();
    }

    public void initPopulator() {
        if(idPopulator != null){
            logger.info("The " + idPopulator.getClass().getCanonicalName() + " is used.");
        } else if (CommonUtils.isPropKeyOn(SYNC_LOCK_IMPL_KEY)) {
            logger.info("The SyncIdPopulator is used.");
            idPopulator = new SyncIdPopulator();
        } else if (CommonUtils.isPropKeyOn(ATOMIC_IMPL_KEY)) {
            logger.info("The AtomicIdPopulator is used.");
            idPopulator = new AtomicIdPopulator();
        } else {
            logger.info("The default LockIdPopulator is used.");
            idPopulator = new LockIdPopulator();
        }
    }

    @Override
    protected void populateId(Id id) {
        idPopulator.populateId(id, this.idMeta);
    }

    public void setIdPopulator(IdPopulator idPopulator) {
        this.idPopulator = idPopulator;
    }
}
