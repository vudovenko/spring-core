package vudovenko.dev.hw1.users.models;

import lombok.*;
import vudovenko.dev.hw1.accounts.models.Account;

import java.util.List;
import java.util.Objects;

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
    private List<Account> accountList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        if (id != null && user.id != null) {
            return Objects.equals(id, user.id);
        }

        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }

        return Objects.hash(login);
    }
}
