package com.myf.id.dubbo;

import com.myf.id.bean.Id;
import com.myf.id.intf.IdService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by maoyf0503 on 2019-11-14.
 *
 * @author maoyf0503
 */
public class ConsumerApplication {
    public static void main(String[]args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DubboConfig.class);
        context.start();
        IdService service = context.getBean("IdServiceComponent",IdServiceImpl.class);
        long id = service.genId();
        System.out.println("gen id:"+id);
        Id idEntity  = service.expId(id);
        System.out.println(idEntity.toString());
    }

    @Configuration
    @EnableDubbo
    @PropertySource("classpath:dubbo-consumer.properties")
    @ComponentScan(value = {"com.myf.id.dubbo"})
    static class DubboConfig{

    }
}
