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

    public Double deposit(Double amount) {
        checkAmount(amount);
        return moneyAmount += amount;
    }

    public Double withdrawn(Double amount) {
        checkAmount(amount);
        checkForSufficientFunds(this, amount);
        return moneyAmount -= amount;
    }

    private static void checkAmount(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private static void checkForSufficientFunds(Account account, Double amount) {
        if (account.getMoneyAmount() < amount) {
            throw new IllegalArgumentException("Not enough money. On account with ID %d is only %.1f"
                    .formatted(account.getId(), account.getMoneyAmount()));
        }
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
