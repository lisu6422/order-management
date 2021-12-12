package com.example.ordermanagement.infrastructure.entity;

import static com.example.ordermanagement.infrastructure.entity.OrderStatus.IN_PREPARATION;

import java.time.Duration;
import java.time.LocalDateTime;
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

    private OrderStatus status;

    private String createdBy;

    private LocalDateTime createdAt;

    private Long preparationTime;

    public boolean canBeCancel() {
        long duration = Duration.between(createdAt, LocalDateTime.now()).toMinutes();
        return status == IN_PREPARATION && duration > preparationTime;
    }

    public void cancel() {
        this.status = OrderStatus.CANCELED;
    }
}
