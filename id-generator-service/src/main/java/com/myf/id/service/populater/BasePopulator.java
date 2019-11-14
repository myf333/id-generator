package com.myf.id.service.populater;

import com.myf.id.bean.Id;
import com.myf.id.service.bean.IdMeta;
import com.myf.id.service.bean.IdType;
import com.myf.id.service.util.TimeUtils;

/**
 * Created by maoyf0503 on 2019-11-11.
 *
 * @author maoyf0503
 */
public class BasePopulator  implements IdPopulator, ResetPopulator {
    protected long sequence = 0;
    protected long lastTimestamp = -1;

    public BasePopulator() {
        super();
    }

    public void populateId(Id id, IdMeta idMeta) {
        long timestamp = TimeUtils.genTime(IdType.parse(id.getType()));
        TimeUtils.validateTimestamp(lastTimestamp, timestamp);

        if (timestamp == lastTimestamp) {
            sequence++;
            sequence &= idMeta.getSeqBitsMask();
            if (sequence == 0) {
                timestamp = TimeUtils.tillNextTimeUnit(lastTimestamp, IdType.parse(id.getType()));
            }
        } else {
            lastTimestamp = timestamp;
            sequence = 0;
        }

        id.setSeq(sequence);
        id.setTime(timestamp);
    }

    public void reset() {
        this.sequence = 0;
        this.lastTimestamp = -1;
    }
}
