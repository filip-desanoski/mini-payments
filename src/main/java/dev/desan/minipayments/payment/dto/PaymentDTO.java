package dev.desan.minipayments.payment.dto;

import dev.desan.minipayments.customer.dto.CustomerPaymentDto;
import dev.desan.minipayments.payment.model.PaymentType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentDTO(
        @NotNull(message = "The amount is mandatory")
        @Digits(integer = 17, fraction = 2, message = "Amount must be a valid decimal number with up to 17 digits and 2 decimal places")
        BigDecimal amount,

        @NotNull(message = "Payment type is mandatory")
        PaymentType paymentType,

        @NotBlank(message = "Target customer information is mandatory")
        String targetUuid,

        @NotNull(message = "Sender customer UUID is mandatory")
        CustomerPaymentDto customerPaymentDto) {
}
