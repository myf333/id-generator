package com.myf.id.service.test;

import com.myf.id.bean.Id;
import com.myf.id.service.IdServiceImpl;
import com.myf.id.service.provider.PropertyMachineIdProvider;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by maoyf0503 on 2019-11-13.
 *
 * @author maoyf0503
 */

public class IdServiceTest {
    @Test
    public void testId(){
        long machineId = 1;
        PropertyMachineIdProvider propertyMachineIdProvider = new PropertyMachineIdProvider();
        propertyMachineIdProvider.setMachineId(machineId);

        IdServiceImpl idService = new IdServiceImpl();
        idService.setMachineIdProvider(propertyMachineIdProvider);

        idService.init();

        long id = idService.genId();
        Id idEntity = idService.expId(id);
        Assert.assertNotNull(idEntity);
    }
}
