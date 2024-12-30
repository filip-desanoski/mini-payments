package dev.desan.minipayments.payment.api;

import dev.desan.minipayments.infrastructure.EndPoints;
import dev.desan.minipayments.payment.dto.PaymentDTO;
import dev.desan.minipayments.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(EndPoints.PAYMENT)
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        paymentService.createPayment(paymentDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PaymentDTO> getPaymentByUuid(@PathVariable UUID uuid) {
        PaymentDTO paymentDTO = paymentService.getPaymentByUuid(uuid);
        return paymentDTO != null ?
                new ResponseEntity<>(paymentDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments(Pageable pageable) {
        List<PaymentDTO> paymentDTOS = paymentService.getAllPayments(pageable).getContent();
        return new ResponseEntity<>(paymentDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<PaymentDTO> deletePayment(@PathVariable UUID uuid) {
        paymentService.deletePayment(uuid);
        return ResponseEntity.noContent().build();
    }
}
