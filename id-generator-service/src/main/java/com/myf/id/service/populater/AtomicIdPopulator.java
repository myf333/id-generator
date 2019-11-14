package com.myf.id.service.populater;

import com.myf.id.bean.Id;
import com.myf.id.service.bean.IdMeta;
import com.myf.id.service.bean.IdType;
import com.myf.id.service.util.TimeUtils;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by maoyf0503 on 2019-11-11.
 *
 * @author maoyf0503
 */
public class AtomicIdPopulator implements IdPopulator,ResetPopulator{
    private class Variant {
        private long sequence = 0;
        private long lastTimestamp = -1;
    }

    private AtomicReference<Variant> variant = new AtomicReference<Variant>(new Variant());

    public AtomicIdPopulator() {
        super();
    }

    public void populateId(Id id, IdMeta idMeta) {
        Variant varOld, varNew;
        long timestamp, sequence;
        while (true){
            varOld = variant.get();
            timestamp = TimeUtils.genTime(IdType.parse(id.getType()));
            TimeUtils.validateTimestamp(varOld.lastTimestamp, timestamp);
            sequence = varOld.sequence;

            if (timestamp == varOld.lastTimestamp) {
                sequence++;
                sequence &= idMeta.getSeqBitsMask();
                if (sequence == 0) {
                    timestamp = TimeUtils.tillNextTimeUnit(varOld.lastTimestamp, IdType.parse(id.getType()));
                }
            } else {
                sequence = 0;
            }

            varNew = new Variant();
            varNew.sequence = sequence;
            varNew.lastTimestamp = timestamp;

            if (variant.compareAndSet(varOld, varNew)) {
                id.setSeq(sequence);
                id.setTime(timestamp);
                break;
            }
        }
    }

    public void reset() {
        variant = new AtomicReference<Variant>(new Variant());
    }
}
