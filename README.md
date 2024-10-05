# Micro-service pour la gestion des comptes

## Description du TP

Ce TP est un micro-service de gestion des comptes bancaires basée sur **Spring Boot**. Il expose une API Restful et GraphQL qui permet de créer, consulter, modifier et supprimer des comptes. Le service utilise une base de données **H2**.

### Objectifs du projet

1. Créer un projet Spring Boot avec les dépendances nécessaires.
2. Implémenter une entité JPA `Account`.
3. Créer une interface de gestion des comptes à l'aide de Spring Data JPA.
4. Exposer des API REST pour manipuler les comptes.
5. Tester les API avec un client REST
6. Générer la documentation des API à l'aide de Swagger.
7. Utiliser Spring Data Rest pour exposer certaines API automatiquement, avec des projections.
8. Créer des DTOs et des mappers pour structurer les données.
9. Ajouter une couche de service pour implémenter la logique métier.
10. Créer un Web service GraphQL


## 1. Créer un projet Spring Boot

Le projet a été créé via **Spring Initializr** avec les dépendances suivantes :

- **Spring Web** : pour la gestion des API REST.
- **Spring Data JPA** : pour interagir avec la base de données.
- **H2** : base de données embarquée en mémoire.
- **Lombok** : pour réduire le code boilerplate (getters, setters, etc.).


## 2. Créer l'entité JPA `Account`

Nous avons créé une classe `Account` annotée avec `@Entity` pour représenter un compte bancaire.
**Code :**
```java
@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;
    private AccountType type;
    private Double balance;
    private Date createdAt;
}
```

le type d'un compte est décrit par enum suivant:
```java
public enum AccountType {
    SAVING_ACCOUNT,
    CURRENT_ACCOUNT,
}
```

## 3. Créer l'interface AccountRepository
L'interface CompteRepository hérite de JpaRepository, qui fournit des méthodes CRUD par défaut pour manipuler les entités.

```java
public interface AccountRepository extends JpaRepository<Compte, Long> {
}
```

## 4. Créer le Web Service Restful
Nous avons créé un contrôleur CompteController pour exposer les API REST pour la gestion des comptes. Les principales opérations sont :

- GET /accounts-rs/ : pour récupérer la liste des comptes.
- POST /accounts-rs/ : pour créer un nouveau compte.
- GET /accounts-rs/{id} : pour obtenir un compte spécifique.
- DELETE /accounts-rs/{id} : pour supprimer un compte.

```java
@RestController
@AllArgsController
@RequestMapping("accounts-rs")
public class AccountRestController {

    private final AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @PostMapping
    public Account saveAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);
    }
}
```

## 5. Tester les APIs avec CURL
On Utilise `curl` pour tester le webservice restful

### Consulter tous les comptes avec pagination
```bash
curl http://localhost:8080/accounts-rs?page
```

```json
{
  "content": [
    {
      "id": "c0a8089f-925d-1a33-8192-5d4a3cf90000",
      "type": "CURRENT_ACCOUNT",
      "balance": 1391.09,
      "createdAt": "2024-10-05T15:26:39.338+00:00"
    },
    {
      "id": "c0a8089f-925d-1a33-8192-5d4a3d280001",
      "type": "SAVING_ACCOUNT",
      "balance": 1493,
      "createdAt": "2024-10-05T15:26:39.400+00:00"
    },
    {
      "id": "c0a8089f-925d-1a33-8192-5d4abb260002",
      "type": "SAVING_ACCOUNT",
      "balance": 1490,
      "createdAt": "2024-10-05T15:27:11.653+00:00"
    }
  ],
  "page": {
    "size": 10,
    "number": 0,
    "totalElements": 3,
    "totalPages": 1
  }
}
```

### Obtenir an account by id
```bash
curl http://localhost:8080/accounts-rs/c0a8089f-925d-1940-8192-5d194c2f0001
```
```json
{
  "id": "c0a8089f-925d-1a33-8192-5d4abb260002",
  "type": "SAVING_ACCOUNT",
  "balance": 1490,
  "createdAt": "2024-10-05T15:27:11.653+00:00"
}
```

### Ajouter un nouveau compte
```bash
curl -X POST -H 'Content-Type: application/json' -d '{"type": "SAVING_ACCOUNT", "balance": 1490}' http://localhost:8080/accounts-rs
```

```json
{
    "id":"c0a8089f-925d-1a33-8192-5d4abb260002",
    "type":"SAVING_ACCOUNT",
    "balance":1490.0,
    "createdAt":"2024-10-05T15:27:11.653+00:00"
}
```

### Supprimer une compte



## 6. Générer la documentation Swagger
Nous avons ajouté Swagger pour générer la documentation des API automatiquement. Ajoutez la dépendance suivante dans pom.xml :
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

Accédez à la documentation Swagger via l'URL : http://localhost:8080/swagger-ui/.


## 7. Exposer une API Restful avec Spring Data Rest
Nous avons utilisé Spring Data Rest pour exposer automatiquement des API basées sur AccountRepository. Nous avons également créé une projection pour n'exposer que certains champs (par exemple, le solde d'un compte).


```java
@Projection(types = Account.class)
public interface AccountProjection {
    String getId();
    AccountType getType();
    Double getBalance();
    Date getCreatedAt();
}
```

## 8. Créer les DTOs et Mappers
Nous avons créé des DTOs pour structurer les données retournées par nos service, ainsi qu'un mapper pour convertir les entités vers ces DTOs.
```java
@AllArgsConstructor @NoArgsConstructor @Builder @Data
public class AccountDto {
    private String id;
    private AccountType type;
    private Double balance;
    private Date createdAt;
}
```

**Le service Mapper**
```java
public interface AccountMapper {
    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);
}
```

```java
@Service
public class AccountMapperImpl implements AccountMapper {
    @Override
    public AccountDto toDto(Account account) {
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);
        return accountDto;
    }

    @Override
    public Account toEntity(AccountDto accountDto) {
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        return account;
    }
}
```

## 9. Créer la couche Service

Nous avons créé une couche service pour implémenter la logique métier liée à la gestion des comptes.

```java
public interface AccountService {
    AccountDto addAccount(AccountDto accountDto);
    AccountDto updateAccount(AccountDto accountDto);
    AccountDto getAccount(String id);
    void deleteAccount(String id);
    Page<AccountDto> getAllAccounts(Pageable pageable);

}
```

```java
@Service
@AllArgsConstructor
@Transactional
public class RepositoryAccountService implements AccountService {
    private AccountRepository accountRepository;
    private AccountMapper accountMapper;

    @Override
    public AccountDto addAccount(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        return accountMapper.toDto(
                accountRepository.save(account)
        );
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {

        Account account = accountMapper.toEntity(
                getAccount(accountDto.getId())
        );
        return accountMapper.toDto(
                accountRepository.save(account)
        );
    }

    @Override
    public AccountDto getAccount(String id) {
        return accountMapper.toDto(
                accountRepository.findById(id).orElseThrow()
        );
    }

    @Override
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(accountMapper::toDto);
    }

}
```

## 10. Crée un web service GraphQL
Nous allons intégrer GraphQL pour offrir une manière flexible de demander et manipuler les données.
```graphql
type Query {
  accounts(page: Int = 0, size: Int = 10): AccountPage!
  accountById(id: ID!): AccountDto!
}

type Mutation {
  saveAccount(type: AccountType!, balance: Float!): AccountDto!
  deleteAccount(id: ID!): Boolean!
}

type Page {
  size: Int
  number: Int
  totalElements: Int
  totalPages: Int
}

type AccountDto {
  id: ID!
  type: AccountType!
  balance: Float!
  createdAt: String!
}

enum AccountType {
  SAVING_ACCOUNT,
  CURRENT_ACCOUNT,
}

type AccountPage {
  content: [AccountDto!]!
  page: Page!
}

```
```java
@Controller
@AllArgsConstructor
public class AccountGraphQlController {
    private AccountService accountService;

    @QueryMapping
    public PagedModel<AccountDto> getAccounts(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new PagedModel<>(accountService.getAllAccounts(pageRequest));
    }

    @QueryMapping
    public AccountDto getAccountById(@Argument String id) {
        return accountService.getAccount(id);
    }

    @MutationMapping
    public AccountDto saveAccount(@Argument AccountDto accountDto) {
        return accountService.addAccount(accountDto);
    }

    @MutationMapping
    public boolean deleteAccount(@Argument String id) {
        accountService.deleteAccount(id);
        return true;
    }
}
```