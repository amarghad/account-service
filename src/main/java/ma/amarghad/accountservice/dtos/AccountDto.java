package ma.amarghad.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.amarghad.accountservice.enums.AccountType;

import java.util.Date;

@AllArgsConstructor @NoArgsConstructor @Builder @Data
public class AccountDto {
    private String id;
    private AccountType type;
    private Double balance;
    private Date createdAt;
}
