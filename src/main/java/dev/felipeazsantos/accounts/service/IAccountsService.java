package dev.felipeazsantos.accounts.service;

import dev.felipeazsantos.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    CustomerDto fetcAccount(String mobileNumber);
}
