package com.tc.batchjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.tv.tvapi"})
@EnableFeignClients
public class BatchJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchJobApplication.class, args);
    }
}
