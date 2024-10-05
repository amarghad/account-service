package ma.amarghad.accountservice.mappers;

import ma.amarghad.accountservice.dtos.AccountDto;
import ma.amarghad.accountservice.entities.Account;

public interface AccountMapper {
    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);
}
