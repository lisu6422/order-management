package com.example.ordermanagement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateOrderItemDTO {

    @NotBlank
    private String code;

    @NotNull
    private Integer quantity;
}
