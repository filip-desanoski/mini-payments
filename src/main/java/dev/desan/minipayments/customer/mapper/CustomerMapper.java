package dev.desan.minipayments.customer.mapper;

import dev.desan.minipayments.customer.dto.CustomerDTO;
import dev.desan.minipayments.customer.model.Customer;
import dev.desan.minipayments.infrastructure.mapper.GeneralMapper;
import dev.desan.minipayments.location.mapper.LocationMapper;
import dev.desan.minipayments.payment.mapper.PaymentMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LocationMapper.class, PaymentMapper.class})
public interface CustomerMapper extends GeneralMapper<CustomerDTO, Customer> {

    @Override
    CustomerDTO entityToDto(Customer customer);

    @Override
    Customer dtoToEntity(CustomerDTO customerDTO);
}
