package ma.amarghad.accountservice.mappers;

import ma.amarghad.accountservice.dtos.AccountDto;
import ma.amarghad.accountservice.entities.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountMapperImpl implements AccountMapper {
    @Override
    public AccountDto toDto(Account account) {
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);
        return accountDto;
    }

    @Override
    public Account toEntity(AccountDto accountDto) {
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        return account;
    }
}
