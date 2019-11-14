package com.myf.dubbo.provider;

import com.myf.id.intf.IdService;
import com.myf.id.service.IdServiceImpl;
import com.myf.id.service.provider.PropertyMachineIdProvider;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by maoyf0503 on 2019-11-14.
 *
 * @author maoyf0503
 */
public class DubboApplication {
    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DubboConfig.class);
        context.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (!"exit".equals(br.readLine())) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                // If error, print it to console
                e.printStackTrace();
            }
        }
    }

    @Configuration
    @EnableDubbo
    @PropertySource("classpath:dubbo-provider.properties")
    static class DubboConfig{
        @Value("${id.generator.machineId}")
        private long machineId;

        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("zookeeper://10.100.129.203:2181");
            return registryConfig;
        }

//        @Bean
//        public IdService idService(){
//            PropertyMachineIdProvider propertyMachineIdProvider = new PropertyMachineIdProvider();
//            propertyMachineIdProvider.setMachineId(machineId);
//
//            IdServiceImpl idService = new IdServiceImpl();
//            idService.setMachineIdProvider(propertyMachineIdProvider);
//
//            idService.init();
//            return idService;
//        }
    }
}
