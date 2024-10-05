package ma.amarghad.accountservice.web;

import lombok.AllArgsConstructor;
import ma.amarghad.accountservice.entities.Account;
import ma.amarghad.accountservice.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("accounts-rs")
public class AccountRestController {

    private AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @PostMapping
    public Account saveAccount(@RequestBody Account Account) {
        return accountRepository.save(Account);
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable String id) {
        return accountRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable String id) {
        accountRepository.deleteById(id);
    }
}

