package dev.desan.minipayments.payment.mapper;

import dev.desan.minipayments.customer.mapper.CustomerMapper;
import dev.desan.minipayments.infrastructure.mapper.GeneralMapper;
import dev.desan.minipayments.payment.dto.PaymentDTO;
import dev.desan.minipayments.payment.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface PaymentMapper extends GeneralMapper<PaymentDTO, Payment> {

    @Override
    PaymentDTO entityToDto(Payment payment);

    @Override
    Payment dtoToEntity(PaymentDTO paymentDTO);
}
