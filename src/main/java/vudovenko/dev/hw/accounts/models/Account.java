package vudovenko.dev.hw.accounts.models;


import jakarta.persistence.*;
import lombok.*;
import vudovenko.dev.hw.users.models.User;

import java.util.Objects;

/**
 * Модель счета пользователя
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "money_amount")
    private Double moneyAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;

        if (id != null && account.id != null) {
            return Objects.equals(id, account.id);
        }

        return Objects.equals(user, account.user) &&
                Objects.equals(moneyAmount, account.moneyAmount);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }

        return Objects.hash(user, moneyAmount);
    }
}
