package com.myf.id.dubbo;

import com.myf.id.bean.Id;
import com.myf.id.intf.IdService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * Created by maoyf0503 on 2019-11-14.
 *
 * @author maoyf0503
 */
@Component("IdServiceComponent")
public class IdServiceImpl implements IdService {

    @Reference
    private IdService idService;

    @Override
    public long genId() {
        return idService.genId();
    }

    @Override
    public Id expId(long id) {
        return idService.expId(id);
    }
}
