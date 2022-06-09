package com.tc.tvapi;

import com.tc.tvapi.service.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableConfigurationProperties({FileUploadProperties.class})
public class TravelingCrewApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelingCrewApplication.class, args);
    }
}
