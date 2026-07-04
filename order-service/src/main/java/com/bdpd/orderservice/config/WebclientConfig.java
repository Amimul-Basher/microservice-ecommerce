package com.bdpd.orderservice.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {
    //If load balancing is not required
//    @Bean
//    public WebClient webClient(@Value("${inventory.service.url}") String inventoryServiceUrl, WebClient.Builder builder){
//        return builder
//                .baseUrl(inventoryServiceUrl)
//                .build();
//    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
    @Bean
    public WebClient webClient(@Value("${inventory.service.url}") String inventoryServiceUrl, WebClient.Builder builder){
        return builder.baseUrl(inventoryServiceUrl)
                .build();
    }
}
