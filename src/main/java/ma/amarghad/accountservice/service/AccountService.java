package ma.amarghad.accountservice.service;

import ma.amarghad.accountservice.dtos.AccountDto;
import ma.amarghad.accountservice.enums.AccountType;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface AccountService {
    AccountDto addAccount(AccountType type, Double balance);
    AccountDto updateAccount(AccountDto accountDto);
    AccountDto getAccount(String id);
    void deleteAccount(String id);
    Page<AccountDto> getAllAccounts(Pageable pageable);

}
