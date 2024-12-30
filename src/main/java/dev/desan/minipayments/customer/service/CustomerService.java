package dev.desan.minipayments.customer.service;

import dev.desan.minipayments.customer.dto.CustomerDTO;
import dev.desan.minipayments.customer.mapper.CustomerMapper;
import dev.desan.minipayments.customer.model.Customer;
import dev.desan.minipayments.customer.repository.CustomerRepository;
import dev.desan.minipayments.location.model.Location;
import dev.desan.minipayments.location.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final LocationRepository locationRepository;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, LocationRepository locationRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.locationRepository = locationRepository;

    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findCustomerByFirstNameAndLastName(
                customerDTO.firstName(), customerDTO.lastName());
        if (existingCustomer.isPresent()) {
            return null;
        }

        Location location = locationRepository.findLocationByCity(customerDTO.locationCity())
                .orElseGet(() -> {
                    Location newLocation = new Location();
                    newLocation.setCity(customerDTO.locationCity());
                    return locationRepository.save(newLocation);
                });

        Customer customer = customerMapper.dtoToEntity(customerDTO);
        customer.setLocation(location);
        customer = customerRepository.save(customer);
        return customerMapper.entityToDto(customer);
    }

    public CustomerDTO getCustomerById(UUID uuid) {
        Optional<Customer> customer = customerRepository.findById(uuid);
        return customer.map(customerMapper::entityToDto).orElse(null);
    }

    public CustomerDTO getCustomerByFullName(String firstName, String lastName) {
        Optional<Customer> customer = customerRepository.findCustomerByFirstNameAndLastName(firstName,lastName);
        return customer.map(customerMapper::entityToDto).orElse(null);
    }

    public Page<CustomerDTO> getCustomersByCity(String city, Pageable pageable) {
        Page<Customer> customersPage = customerRepository.findCustomersByLocation_City(city, pageable);
        return customersPage.map(customerMapper::entityToDto);
    }

    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        Page<Customer> customersPage = customerRepository.findAll(pageable);
        return customersPage.map(customerMapper::entityToDto);
    }

    public CustomerDTO updateCustomer(UUID uuid, CustomerDTO customerDTO) {
        return customerRepository.findById(uuid).map(customer -> {
            customer.setFirstName(customerDTO.firstName());
            customer.setLastName(customerDTO.lastName());

            Location location = locationRepository.findLocationByCity(customerDTO.locationCity())
                    .orElseGet(() -> {
                        Location newLocation = new Location();
                        newLocation.setCity(customerDTO.locationCity());
                        return locationRepository.save(newLocation);
                    });

            customer.setLocation(location);
            customer = customerRepository.save(customer);
            return customerMapper.entityToDto(customer);
        }).orElse(null);
    }

    public CustomerDTO patchUpdateCustomer(UUID uuid, CustomerDTO customerDTO) {
        return customerRepository.findById(uuid).map(customer -> {
            if (customerDTO.firstName() != null) {
                customer.setFirstName(customerDTO.firstName());
            }
            if (customerDTO.lastName() != null) {
                customer.setLastName(customerDTO.lastName());
            }
            if (customerDTO.locationCity() != null) {

                Location location = locationRepository.findLocationByCity(customerDTO.locationCity())
                        .orElseGet(() -> {
                            Location newLocation = new Location();
                            newLocation.setCity(customerDTO.locationCity());
                            return locationRepository.save(newLocation);
                        });

                customer.setLocation(location);
            }

            customer = customerRepository.save(customer);
            return customerMapper.entityToDto(customer);
        }).orElse(null);
    }

    public void deleteCustomer(UUID uuid) {
        customerRepository.deleteById(uuid);
    }
}

