package vudovenko.dev.accounts.models;


import lombok.*;

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
public class Account {

    private Long id;
    private Long userId;
    private Double moneyAmount;

    public Double addMoney(Double amount) {
        return moneyAmount += amount;
    }

    public Double takeAwayMoney(Double amount) {
        return moneyAmount -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;

        if (id != null && account.id != null) {
            return Objects.equals(id, account.id);
        }

        return Objects.equals(userId, account.userId) &&
                Objects.equals(moneyAmount, account.moneyAmount);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }

        return Objects.hash(userId, moneyAmount);
    }
}
