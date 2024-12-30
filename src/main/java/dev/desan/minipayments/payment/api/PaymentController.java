package dev.desan.minipayments.payment.api;

import dev.desan.minipayments.infrastructure.EndPoints;
import dev.desan.minipayments.payment.dto.PaymentDTO;
import dev.desan.minipayments.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPoints.PAYMENT)
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        paymentService.processPayment(paymentDTO);
        return ResponseEntity.ok().build();
    }
}
