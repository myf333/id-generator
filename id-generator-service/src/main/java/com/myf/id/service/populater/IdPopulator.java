package com.myf.id.service.populater;

import com.myf.id.bean.Id;
import com.myf.id.service.bean.IdMeta;

/**
 * Created by maoyf0503 on 2019-11-11.
 *
 * @author maoyf0503
 */
public interface IdPopulator {
    void populateId(Id id, IdMeta idMeta);
}
