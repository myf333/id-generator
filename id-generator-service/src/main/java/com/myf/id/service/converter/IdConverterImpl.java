package com.myf.id.service.converter;

import com.myf.id.bean.Id;
import com.myf.id.service.bean.IdMeta;
import com.myf.id.service.bean.IdMetaFactory;
import com.myf.id.service.bean.IdType;

/**
 * Created by maoyf0503 on 2019-11-7.
 *
 * @author maoyf0503
 */
public class IdConverterImpl implements IdConverter {
    private IdMeta idMeta;

    public IdConverterImpl() {
    }
    public IdConverterImpl(IdType idType) {
        this(IdMetaFactory.getIdMeta(idType));
    }

    public IdConverterImpl(IdMeta idMeta) {
        this.idMeta = idMeta;
    }

    public long convert(Id id) {
        return doConvert(id,this.idMeta);
    }

    protected long doConvert(Id id, IdMeta idMeta) {
        long ret = 0;

        ret |= id.getMachine();

        ret |= id.getSeq() << idMeta.getSeqBitsStartPos();

        ret |= id.getTime() << idMeta.getTimeBitsStartPos();

        ret |= id.getGenMethod() << idMeta.getGenMethodBitsStartPos();

        ret |= id.getType() << idMeta.getTypeBitsStartPos();

        ret |= id.getVersion() << idMeta.getVersionBitsStartPos();

        return ret;
    }

    public Id convert(long id) {
        return doConvert(id,this.idMeta);
    }

    protected Id doConvert(long id, IdMeta idMeta) {
        Id ret = new Id();

        ret.setMachine(id & idMeta.getMachineBitsMask());

        ret.setSeq((id >>> idMeta.getSeqBitsStartPos()) & idMeta.getSeqBitsMask());

        ret.setTime((id >>> idMeta.getTimeBitsStartPos()) & idMeta.getTimeBitsMask());

        ret.setGenMethod((id >>> idMeta.getGenMethodBitsStartPos()) & idMeta.getGenMethodBitsMask());

        ret.setType((id >>> idMeta.getTypeBitsStartPos()) & idMeta.getTypeBitsMask());

        ret.setVersion((id >>> idMeta.getVersionBitsStartPos()) & idMeta.getVersionBitsMask());

        return ret;
    }
}
