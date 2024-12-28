package dev.desan.minipayments.customer.mapper;

import dev.desan.minipayments.customer.dto.CustomerDTO;
import dev.desan.minipayments.customer.model.Customer;
import dev.desan.minipayments.infrastructure.mapper.GeneralMapper;
import dev.desan.minipayments.location.mapper.LocationMapper;
import dev.desan.minipayments.payment.mapper.PaymentMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {PaymentMapper.class, LocationMapper.class})
public interface CustomerMapper extends GeneralMapper<CustomerDTO, Customer> {

    @Override
    @Mapping(target = "locationName", source = "location")
    CustomerDTO entityToDto(Customer customer);

    @Override
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "location", source = "locationName")
    Customer dtoToEntity(CustomerDTO dto);
}
