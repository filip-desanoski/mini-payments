package dev.desan.minipayments.customer.dto;

import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.payment.dto.PaymentDTO;

import java.util.Set;

public record CustomerDTO(
        String firstName,
        String lastName,
        LocationDTO location,
        Set<PaymentDTO> payments) {
}
