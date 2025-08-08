package dev.felipeazsantos.accounts.service.impl;

import dev.felipeazsantos.accounts.Dto.CustomerDto;
import dev.felipeazsantos.accounts.repository.AccountsRepository;
import dev.felipeazsantos.accounts.repository.CustomerRepository;
import dev.felipeazsantos.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

    }
}
