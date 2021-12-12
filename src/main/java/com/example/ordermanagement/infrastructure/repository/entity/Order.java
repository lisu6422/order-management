package com.example.ordermanagement.infrastructure.repository.entity;

import static com.example.ordermanagement.infrastructure.repository.entity.OrderStatus.IN_PREPARATION;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order")
@EntityListeners({AuditingEntityListener.class})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String createdBy;

    @CreatedDate
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
