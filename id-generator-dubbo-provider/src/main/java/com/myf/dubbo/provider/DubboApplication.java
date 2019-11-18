package com.myf.dubbo.provider;

import com.myf.id.intf.IdService;
import com.myf.id.service.IdServiceImpl;
import com.myf.id.service.provider.PropertyMachineIdProvider;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Autowired;
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

        @Autowired
        private ApplicationConfig applicationConfig;
        @Autowired
        private ProtocolConfig protocolConfig;

        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("zookeeper://10.100.129.203:2181");
            return registryConfig;
        }

        @Bean
        public ServiceConfig<IdService> idServiceServiceConfig(){
            ServiceConfig<IdService> serviceConfig = new ServiceConfig<>();
            serviceConfig.setApplication(applicationConfig);
            serviceConfig.setRegistry(registryConfig()); // 多个注册中心可以用setRegistries()
            serviceConfig.setProtocol(protocolConfig); // 多个协议可以用setProtocols()
            serviceConfig.setInterface(IdService.class);
            serviceConfig.setRef(idService());
            //serviceConfig.setVersion("1.0.0");
            serviceConfig.export();
            return serviceConfig;
        }

        @Bean
        public IdService idService(){
            PropertyMachineIdProvider propertyMachineIdProvider = new PropertyMachineIdProvider();
            propertyMachineIdProvider.setMachineId(machineId);

            IdServiceImpl idService = new IdServiceImpl();
            idService.setMachineIdProvider(propertyMachineIdProvider);

            idService.init();
            return idService;
        }
    }
}
