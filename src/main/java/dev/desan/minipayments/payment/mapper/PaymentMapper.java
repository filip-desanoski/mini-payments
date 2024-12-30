package dev.desan.minipayments.payment.mapper;

import dev.desan.minipayments.customer.mapper.CustomerMapper;
import dev.desan.minipayments.infrastructure.mapper.GeneralMapper;
import dev.desan.minipayments.payment.dto.PaymentDTO;
import dev.desan.minipayments.payment.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface PaymentMapper extends GeneralMapper<PaymentDTO, Payment> {

    @Override
    @Mapping(target = "customerPaymentDto", source = "customer")
    PaymentDTO entityToDto(Payment payment);

    @Override
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "customer", source = "customerPaymentDto")
    Payment dtoToEntity(PaymentDTO paymentDTO);

}
