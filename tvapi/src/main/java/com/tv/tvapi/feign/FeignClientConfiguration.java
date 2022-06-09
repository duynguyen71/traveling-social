package com.tv.tvapi.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration{

        /**
         * Enable this bean if you want to add headers in HTTP request
         */
        @Bean
        public RequestInterceptor requestInterceptor() {
            return requestTemplate -> {
                requestTemplate.header("Content-Type", "application/json");
                requestTemplate.header("Accept", "application/json");
//                requestTemplate.header("Authorization", "value_1");
//                requestTemplate.header("header_2", "value_2");
//                requestTemplate.header("header_3", "value_3");
            };
        }


}
