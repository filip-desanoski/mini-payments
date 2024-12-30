package dev.desan.minipayments.payment.service;

import dev.desan.minipayments.customer.model.Customer;
import dev.desan.minipayments.customer.repository.CustomerRepository;
import dev.desan.minipayments.payment.dto.PaymentDTO;
import dev.desan.minipayments.payment.model.Payment;
import dev.desan.minipayments.payment.model.PaymentType;
import dev.desan.minipayments.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;

    public PaymentService(PaymentRepository paymentRepository, CustomerRepository customerRepository) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void processPayment(PaymentDTO paymentDTO) {
        Customer sender = customerRepository.findById(UUID.fromString(paymentDTO.customerPaymentDto().uuid()))
                .orElseThrow(() -> new IllegalArgumentException("Sender customer not found"));

        Customer receiver = customerRepository.findById(UUID.fromString(paymentDTO.targetUuid()))
                .orElseThrow(() -> new IllegalArgumentException("Receiver customer not found"));

        if (sender.getBalance().compareTo(paymentDTO.amount()) < 0) {
            throw new IllegalStateException("Insufficient balance for the payment");
        }

        sender.setBalance(sender.getBalance().subtract(paymentDTO.amount()));
        receiver.setBalance(receiver.getBalance().add(paymentDTO.amount()));

        customerRepository.save(sender);
        customerRepository.save(receiver);

        Payment outcomePayment = new Payment();
        outcomePayment.setAmount(paymentDTO.amount());
        outcomePayment.setPaymentType(PaymentType.OUTCOME);
        outcomePayment.setCustomer(sender);
        paymentRepository.save(outcomePayment);

        Payment incomePayment = new Payment();
        incomePayment.setAmount(paymentDTO.amount());
        incomePayment.setPaymentType(PaymentType.INCOME);
        incomePayment.setCustomer(receiver);
        paymentRepository.save(incomePayment);
    }
}
