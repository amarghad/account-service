package ma.amarghad.accountservice.repository;

import ma.amarghad.accountservice.entities.Account;
import ma.amarghad.accountservice.projections.AccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = AccountProjection.class)
public interface AccountRepository extends JpaRepository<Account, String> {
}
