package dev.desan.minipayments.payment.dto;

import dev.desan.minipayments.customer.dto.CustomerDTO;
import dev.desan.minipayments.payment.model.PaymentType;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record PaymentDTO(
        @NotBlank(message = "The amount is mandatory")
        BigDecimal amount,

        @NotBlank(message = "Payment type is mandatory")
        PaymentType paymentType,

        @NotBlank(message = "Customer UUID is mandatory")
        CustomerDTO customerDTO) {
}
