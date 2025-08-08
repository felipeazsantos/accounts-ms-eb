package dev.felipeazsantos.accounts.service;

import dev.felipeazsantos.accounts.Dto.CustomerDto;

public interface IAccountsService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);
}
