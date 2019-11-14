package com.myf.dubbo.provider;

import com.myf.id.bean.Id;
import com.myf.id.intf.IdService;
import org.apache.dubbo.config.annotation.Service;

/**
 * Created by maoyf0503 on 2019-11-14.
 *
 * @author maoyf0503
 */
@Service
public class IdServiceMock  implements IdService {
    @Override
    public long genId() {
        return 1;
    }

    @Override
    public Id expId(long id) {
        return new Id();
    }
}
