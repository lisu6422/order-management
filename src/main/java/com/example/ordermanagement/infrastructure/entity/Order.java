package com.example.ordermanagement.infrastructure.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order")
public class Order {

    @Id
    private Long id;

    private String orderNo;

    @OneToMany
    private List<OrderItem> items;

    private String createdBy;
}
