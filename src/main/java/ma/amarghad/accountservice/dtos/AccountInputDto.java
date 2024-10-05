package ma.amarghad.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.amarghad.accountservice.enums.AccountType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInputDto {
    private AccountType type;
    private double balance;
}
