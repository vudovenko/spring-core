package vudovenko.dev.users.models;

import lombok.*;
import vudovenko.dev.accounts.models.Account;

import java.util.Set;

/**
 * Модель пользователя
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Long id;
    private String login;
    private Set<Account> accountList;
}
