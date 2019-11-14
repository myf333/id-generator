package com.myf.id.service.converter;

import com.myf.id.bean.Id;

/**
 * Created by maoyf0503 on 2019-11-7.
 *
 * @author maoyf0503
 */
public interface IdConverter {
    long convert(Id id);
    Id convert(long id);
}
