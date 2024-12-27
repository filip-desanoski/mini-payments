package dev.desan.minipayments.customer.api;

import dev.desan.minipayments.customer.dto.CustomerDTO;
import dev.desan.minipayments.customer.service.CustomerService;
import dev.desan.minipayments.infrastructure.EndPoints;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(EndPoints.CUSTOMER)
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerDTO> getCustomerByUuid(@PathVariable UUID uuid) {
        CustomerDTO customerDTO = customerService.getCustomerById(uuid);
        return customerDTO != null ?
                new ResponseEntity<>(customerDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/full-name")
    public ResponseEntity<CustomerDTO> getCustomerByFullName(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        CustomerDTO customerDTO = customerService.getCustomerByFullName(firstName, lastName);
        return customerDTO != null ?
                new ResponseEntity<>(customerDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(Pageable pageable) {
        Page<CustomerDTO> customerDTOs = customerService.getAllCustomers(pageable);
        return new ResponseEntity<>(customerDTOs, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable UUID uuid, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(uuid, customerDTO);
        return updatedCustomer != null ?
                new ResponseEntity<>(updatedCustomer, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID uuid) {
        customerService.deleteCustomer(uuid);
        return ResponseEntity.noContent().build();
    }
}
