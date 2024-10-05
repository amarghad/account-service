package ma.amarghad.accountservice.projections;

import ma.amarghad.accountservice.entities.Account;
import ma.amarghad.accountservice.enums.AccountType;
import org.springframework.data.rest.core.config.Projection;
import java.util.Date;

@Projection(types = Account.class)
public interface AccountProjection {
    String getId();
    AccountType getType();
    Double getBalance();
    Date getCreatedAt();
}
