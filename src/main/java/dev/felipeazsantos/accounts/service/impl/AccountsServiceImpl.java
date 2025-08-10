package dev.felipeazsantos.accounts.service.impl;

import dev.felipeazsantos.accounts.dto.AccountsDto;
import dev.felipeazsantos.accounts.dto.CustomerDto;
import dev.felipeazsantos.accounts.constants.AccountsConstants;
import dev.felipeazsantos.accounts.entity.Accounts;
import dev.felipeazsantos.accounts.entity.Customer;
import dev.felipeazsantos.accounts.exception.CustomerAlreadyExistsException;
import dev.felipeazsantos.accounts.exception.ResourceNotFoundException;
import dev.felipeazsantos.accounts.mappers.AccountsMapper;
import dev.felipeazsantos.accounts.mappers.CustomerMapper;
import dev.felipeazsantos.accounts.repository.AccountsRepository;
import dev.felipeazsantos.accounts.repository.CustomerRepository;
import dev.felipeazsantos.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        var customerAlreadyExists =  customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (customerAlreadyExists.isPresent()) {
            throw new CustomerAlreadyExistsException(String.format("Customer already registered with given mobileNumber %s",
                    customerDto.getMobileNumber()));
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("anonymous");

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("anonymous");
        return newAccount;
    }

    @Override
    public CustomerDto fetcAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }
}
