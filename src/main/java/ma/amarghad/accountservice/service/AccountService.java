package ma.amarghad.accountservice.service;

import ma.amarghad.accountservice.dtos.AccountDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface AccountService {
    AccountDto addAccount(AccountDto accountDto);
    AccountDto updateAccount(AccountDto accountDto);
    AccountDto getAccount(String id);
    void deleteAccount(String id);
    Page<AccountDto> getAllAccounts(Pageable pageable);

}
