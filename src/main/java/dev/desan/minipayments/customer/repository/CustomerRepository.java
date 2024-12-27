package dev.desan.minipayments.customer.repository;

import dev.desan.minipayments.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findCustomerByFullName(String firstName, String lastName);

    Page<Customer> findCustomersByLocation(String location, Pageable pageable);
}
