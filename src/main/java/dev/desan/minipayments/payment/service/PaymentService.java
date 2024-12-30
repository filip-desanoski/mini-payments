package dev.desan.minipayments.payment.service;

import dev.desan.minipayments.customer.model.Customer;
import dev.desan.minipayments.customer.repository.CustomerRepository;
import dev.desan.minipayments.payment.dto.PaymentDTO;
import dev.desan.minipayments.payment.mapper.PaymentMapper;
import dev.desan.minipayments.payment.model.Payment;
import dev.desan.minipayments.payment.model.PaymentType;
import dev.desan.minipayments.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, CustomerRepository customerRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.paymentMapper = paymentMapper;
    }

    @Transactional
    public void createPayment(PaymentDTO paymentDTO) {
        log.info("Creating payment {}", paymentDTO);
        Customer sender = customerRepository.findById(UUID.fromString(paymentDTO.customerPaymentDto().uuid()))
                .orElseThrow(() ->{
                    log.error("Customer not found");
                    return new IllegalArgumentException("Sender customer not found");
                });


        Customer receiver = customerRepository.findById(UUID.fromString(paymentDTO.targetUuid()))
                .orElseThrow(() -> {
                    log.error("Target customer not found");
                    return new IllegalArgumentException("Receiver customer not found");
                });

        if (sender.getBalance().compareTo(paymentDTO.amount()) < 0) {
            log.error("Insufficient funds for payment {}", paymentDTO);
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
        log.info("Outcome payment created for payment {}", outcomePayment);
        paymentRepository.save(outcomePayment);

        Payment incomePayment = new Payment();
        incomePayment.setAmount(paymentDTO.amount());
        incomePayment.setPaymentType(PaymentType.INCOME);
        incomePayment.setCustomer(receiver);
        log.info("Income payment created for payment {}", incomePayment);
        paymentRepository.save(incomePayment);
    }

    public PaymentDTO getPaymentByUuid(UUID uuid) {
        Optional<Payment> payment = paymentRepository.findById(uuid);
        log.info("Payment found with id {} ", uuid);
        return payment.map(paymentMapper::entityToDto).orElse(null);
    }

    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        Page<Payment> paymentPage = paymentRepository.findAll(pageable);
        log.info("Payments found");
        return paymentPage.map(paymentMapper::entityToDto);
    }

    public void deletePayment(UUID uuid) {
        paymentRepository.deleteById(uuid);
        log.info("Payment {} deleted", uuid);
    }
}
