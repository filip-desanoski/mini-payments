package dev.desan.minipayments.customer.mapper;

import dev.desan.minipayments.customer.dto.CustomerDTO;
import dev.desan.minipayments.customer.dto.CustomerPaymentDto;
import dev.desan.minipayments.customer.model.Customer;
import dev.desan.minipayments.infrastructure.mapper.GeneralMapper;
import dev.desan.minipayments.location.mapper.LocationMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface CustomerMapper extends GeneralMapper<CustomerDTO, Customer> {

    @Override
    @Mapping(target = "locationCity", source = "location.city")
    CustomerDTO entityToDto(Customer customer);

    @Override
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "location.city", source = "locationCity")
    Customer dtoToEntity(CustomerDTO dto);

    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    CustomerPaymentDto entityToCustomerPaymentDto(Customer customer);

    @Mapping(target = "uuid", source = "uuid")
    Customer customerPaymentDtoToEntity(CustomerPaymentDto customerPaymentDto);
}

