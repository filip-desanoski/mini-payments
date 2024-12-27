package dev.desan.minipayments.customer.dto;

import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.payment.dto.PaymentDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CustomerDTO(
        @NotBlank(message = "First name is mandatory")
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        String lastName,

        @NotBlank(message = "Location name is mandatory")
        LocationDTO location,
        Set<PaymentDTO> payments) {
}
