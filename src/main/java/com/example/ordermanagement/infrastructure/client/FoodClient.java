package com.example.ordermanagement.infrastructure.client;

import com.example.ordermanagement.infrastructure.client.dto.FoodDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "food-management", url = "http:localhost:8082")
public interface FoodClient {

    @GetMapping("/foods")
    List<FoodDTO> findByCodes(@RequestParam(value = "codes") List<String> codes);
}
