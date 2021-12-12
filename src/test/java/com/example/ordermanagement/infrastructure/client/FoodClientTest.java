package com.example.ordermanagement.infrastructure.client;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.ordermanagement.OrderManagementApplication;
import com.example.ordermanagement.infrastructure.client.dto.FoodDTO;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs", port = 8082)
@SpringBootTest(classes = OrderManagementApplication.class)
class FoodClientTest {

    @Autowired
    private FoodClient foodClient;

    @Test
    void should_find_food_by_codes_success() {
        List<FoodDTO> foodDTOS = foodClient.findByCodes(Collections.singletonList("A01"));
        assertEquals(foodDTOS.size(), 1);
    }
}