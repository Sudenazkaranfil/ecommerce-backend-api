package com.example.ecommerceapp.service;

import com.example.ecommerceapp.dto.CreateCustomerRequest;
import com.example.ecommerceapp.dto.CustomerDto;
import com.example.ecommerceapp.entity.Customer;
import com.example.ecommerceapp.exception.CustomerNotFoundException;
import com.example.ecommerceapp.exception.ResourceNotFoundException;
import com.example.ecommerceapp.repository.CustomerRepository;
import com.example.ecommerceapp.util.DtoConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final DtoConverter dtoConverter;

    // Tüm müşterileri getirir
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(dtoConverter::convertToCustomerDto)
                .collect(Collectors.toList());
    }

    // Belirli bir müşteriyi ID'ye göre getirir
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return dtoConverter.convertToCustomerDto(customer);
    }

    // Yeni müşteri oluşturur
    @Transactional
    public CustomerDto createCustomer(CreateCustomerRequest request) {
        // E-posta adresinin benzersizliğini kontrol et
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Customer with this email already exists: " + request.getEmail());
        }

        Customer customer = dtoConverter.convertToCustomer(request);
        customer = customerRepository.save(customer);
        return dtoConverter.convertToCustomerDto(customer);
    }

    // Mevcut müşteriyi günceller
    @Transactional
    public CustomerDto updateCustomer(Long id, CreateCustomerRequest request) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        // E-posta adresini güncelliyorsa benzersizliğini kontrol et
        if (!existingCustomer.getEmail().equals(request.getEmail()) && customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Customer with this email already exists: " + request.getEmail());
        }

        existingCustomer.setFirstName(request.getFirstName());
        existingCustomer.setLastName(request.getLastName());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setAddress(request.getAddress());
        existingCustomer.setPhone(request.getPhone());

        existingCustomer = customerRepository.save(existingCustomer);
        return dtoConverter.convertToCustomerDto(existingCustomer);
    }

    // Müşteriyi siler
    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        customerRepository.deleteById(id);
    }
}