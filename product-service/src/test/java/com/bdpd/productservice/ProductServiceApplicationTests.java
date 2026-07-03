package com.bdpd.productservice;

import com.bdpd.productservice.dto.ProductRequest;
import com.bdpd.productservice.repository.ProductRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
//import org.testcontainers.mongodb.MongoDBContainer;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc // we need to configure mockMVC to make request in mock controller
class ProductServiceApplicationTests {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper; //Helps converting json to pojo object and vice versa

    //When the test starts the mongodb container will be up and running
    //Then we want the mongodb uri to be added dynamically to the test application properties
    @DynamicPropertySource // this annotation is to run this method and add properties dynamically at runtime
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistryp){
        dynamicPropertyRegistryp.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequst();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());
        Assertions.assertTrue(productRepository.findAll().size() == 1);
    }

    private ProductRequest getProductRequst() {
        return ProductRequest.builder()
                .name("iphone_12")
                .price(BigDecimal.valueOf(1100))
                .description("iphone_12 with standard feature")
                .build();
    }

}
