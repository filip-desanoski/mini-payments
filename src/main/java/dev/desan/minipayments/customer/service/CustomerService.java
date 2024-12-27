package dev.desan.minipayments.customer.service;

import dev.desan.minipayments.customer.dto.CustomerDTO;
import dev.desan.minipayments.customer.mapper.CustomerMapper;
import dev.desan.minipayments.customer.model.Customer;
import dev.desan.minipayments.customer.repository.CustomerRepository;
import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.location.mapper.LocationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final LocationMapper locationMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, LocationMapper locationMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.locationMapper = locationMapper;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.dtoToEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.entityToDto(customer);
    }

    public CustomerDTO getCustomerById(UUID uuid) {
        Optional<Customer> customer = customerRepository.findById(uuid);
        return customer.map(customerMapper::entityToDto).orElse(null);
    }

    public CustomerDTO getCustomerByFullName(String firstName, String lastName) {
        Optional<Customer> customer = customerRepository.getCustomerByFullName(firstName, lastName);
        return customer.map(customerMapper::entityToDto).orElse(null);
    }

    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        Page<Customer> customersPage = customerRepository.findAll(pageable);
        return customersPage.map(customerMapper::entityToDto);
    }

    public CustomerDTO updateCustomer(UUID uuid, CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findById(uuid);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setFirstName(customerDTO.firstName());
            customer.setLastName(customerDTO.lastName());
            customer.setLocation(locationMapper.dtoToEntity(customerDTO.location()));
            customer = customerRepository.save(customer);
            return customerMapper.entityToDto(customer);
        }
        return null;
    }

    public void deleteCustomer(UUID uuid) {
        customerRepository.deleteById(uuid);
    }
}
