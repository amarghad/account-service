package ma.amarghad.accountservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.amarghad.accountservice.dtos.AccountDto;
import ma.amarghad.accountservice.entities.Account;
import ma.amarghad.accountservice.enums.AccountType;
import ma.amarghad.accountservice.mappers.AccountMapper;
import ma.amarghad.accountservice.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Transactional
public class RepositoryAccountService implements AccountService {
    private AccountRepository accountRepository;
    private AccountMapper accountMapper;

    @Override
    public AccountDto addAccount(AccountType type, Double balance) {
        Account account = Account.builder()
                .type(type)
                .balance(balance)
                .createdAt(new Date())
                .build();
        return accountMapper.toDto(
                accountRepository.save(account)
        );
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {

        Account account = accountMapper.toEntity(
                getAccount(accountDto.getId())
        );
        return accountMapper.toDto(
                accountRepository.save(account)
        );
    }

    @Override
    public AccountDto getAccount(String id) {
        return accountMapper.toDto(
                accountRepository.findById(id).orElseThrow()
        );
    }

    @Override
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(accountMapper::toDto);
    }

}
