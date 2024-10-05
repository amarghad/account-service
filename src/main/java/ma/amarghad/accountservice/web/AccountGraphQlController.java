package ma.amarghad.accountservice.web;

import lombok.AllArgsConstructor;
import ma.amarghad.accountservice.dtos.AccountDto;
import ma.amarghad.accountservice.enums.AccountType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ma.amarghad.accountservice.service.AccountService;

@Controller
@AllArgsConstructor
public class AccountGraphQlController {
    private AccountService accountService;

    @QueryMapping("accounts")
    public PagedModel<AccountDto> getAccounts(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new PagedModel<>(accountService.getAllAccounts(pageRequest));
    }

    @QueryMapping("accountById")
    public AccountDto getAccountById(@Argument String id) {
        return accountService.getAccount(id);
    }

    @MutationMapping
    public AccountDto saveAccount(@Argument AccountType type, @Argument Double balance) {
        return accountService.addAccount(type, balance);
    }

    @MutationMapping
    public boolean deleteAccount(@Argument String id) {
        accountService.deleteAccount(id);
        return true;
    }
}
