package ma.amarghad.accountservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.amarghad.accountservice.enums.AccountType;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;
    private AccountType type;
    private BigDecimal balance;
    private Date createdAt;
}
