package vudovenko.dev.users.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vudovenko.dev.accounts.models.Account;

import java.util.List;

/**
 * Модель пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String login;
    private List<Account> accountList;
}
