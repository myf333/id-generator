package com.myf.id.intf;

import com.myf.id.bean.Id;

/**
 * Created by maoyf0503 on 2019-11-7.
 *
 * @author maoyf0503
 */
public interface IdService {
    public long genId();

    public Id expId(long id);
}
