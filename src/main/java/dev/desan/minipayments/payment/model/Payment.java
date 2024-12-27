package dev.desan.minipayments.payment.model;

import dev.desan.minipayments.customer.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime paymentDateTime;

    @Column(nullable = false, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_uuid", nullable = false)
    private Customer customer;

    @Column(name = "updated_on", nullable = false)
    private LocalDateTime updatedOn;

    @PrePersist
    public void prePersist() {
        this.paymentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        this.updatedOn = LocalDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedOn = LocalDateTime.now(ZoneOffset.UTC);
    }
}
