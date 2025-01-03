package dev.desan.minipayments.location.model;

import dev.desan.minipayments.customer.model.Customer;
import dev.desan.minipayments.infrastructure.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location extends BaseEntity {

    @Column(unique = true, nullable = false, length = 50)
    private String city;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Customer> customers;
}
