package ma.amarghad.accountservice.web;

import lombok.AllArgsConstructor;
import ma.amarghad.accountservice.dtos.AccountDto;
import ma.amarghad.accountservice.dtos.AccountInputDto;
import ma.amarghad.accountservice.enums.AccountType;
import ma.amarghad.accountservice.service.AccountService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("accounts-rs")
public class AccountRestController {

    private AccountService accountService;

    @GetMapping
    public PagedModel<AccountDto> getAccounts(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new PagedModel<>(accountService.getAllAccounts(pageRequest));
    }

    @PostMapping
    public AccountDto saveAccount(@RequestBody AccountInputDto body) {
        return accountService.addAccount(body.getType(), body.getBalance());
    }

    @GetMapping("/{id}")
    public AccountDto getAccountById(@PathVariable String id) {
        return accountService.getAccount(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable String id) {
        accountService.deleteAccount(id);
    }
}

