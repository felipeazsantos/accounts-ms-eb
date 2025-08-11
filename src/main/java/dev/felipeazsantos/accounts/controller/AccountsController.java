package dev.felipeazsantos.accounts.controller;

import dev.felipeazsantos.accounts.dto.CustomerDto;
import dev.felipeazsantos.accounts.dto.ResponseDto;
import dev.felipeazsantos.accounts.constants.AccountsConstants;
import dev.felipeazsantos.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    private IAccountsService accountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto) {
       boolean isUpdated = accountsService.updateAccount(customerDto);
       if (isUpdated) {
           return ResponseEntity.ok(new ResponseDto(
                   AccountsConstants.STATUS_200,
                   AccountsConstants.MESSAGE_200));
       }

        return ResponseEntity.internalServerError().body(new ResponseDto(
                AccountsConstants.STATUS_500,
                AccountsConstants.MESSAGE_500));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseDto(
                    AccountsConstants.STATUS_200,
                    AccountsConstants.MESSAGE_200));
        }

        return ResponseEntity.internalServerError().body(new ResponseDto(
                AccountsConstants.STATUS_500,
                AccountsConstants.MESSAGE_500));
    }
}
