package ma.amarghad.accountservice;

import ma.amarghad.accountservice.entities.Account;
import ma.amarghad.accountservice.enums.AccountType;
import ma.amarghad.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class CompteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompteServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository accountRepository) {
        return args -> {
            Account currentAccount = Account.builder()
                    .type(AccountType.CURRENT_ACCOUNT)
                    .balance(1391.09)
                    .createdAt(new Date())
                    .build();
            accountRepository.save(currentAccount);

            Account savingAccount = Account.builder()
                            .type(AccountType.SAVING_ACCOUNT)
                            .balance(1493.00)
                            .createdAt(new Date())
                            .build();

            accountRepository.save(savingAccount);
        };
    }

}
