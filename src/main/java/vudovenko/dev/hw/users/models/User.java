package vudovenko.dev.hw.users.models;

import jakarta.persistence.*;
import lombok.*;
import vudovenko.dev.hw.accounts.models.Account;

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
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Account> accounts;

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
