package dev.desan.minipayments.payment.dto;

import dev.desan.minipayments.customer.dto.CustomerDTO;
import dev.desan.minipayments.payment.model.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDTO(
        LocalDateTime paymentDateTime,
        BigDecimal amount,
        PaymentType paymentType,
        CustomerDTO customer) {
}
