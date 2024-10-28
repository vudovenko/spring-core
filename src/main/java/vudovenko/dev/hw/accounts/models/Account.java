package vudovenko.dev.hw.accounts.models;


import jakarta.persistence.*;
import lombok.*;
import vudovenko.dev.hw.users.models.User;
import vudovenko.dev.hw.utils.math.MathUtils;

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

    public Double deposit(Double amount) {
        checkAmount(amount);
        moneyAmount = MathUtils.round(moneyAmount + amount, 2);

        return moneyAmount;
    }

    public Double withdraw(Double amount) {
        checkAmount(amount);
        checkForSufficientFunds(this, amount);
        moneyAmount = MathUtils.round(moneyAmount - amount, 2);

        return moneyAmount;
    }

    public void transfer(
            Account targetAccount,
            Double amount,
            Double transferCommission
    ) {
        this.withdraw(amount);
        if (!Objects.equals(this.getUser(), targetAccount.getUser())) {
            amount = (1.0 - transferCommission) * amount;
            targetAccount.deposit(amount);
        } else {
            targetAccount.deposit(amount);
        }
    }

    private static void checkAmount(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private static void checkForSufficientFunds(Account account, Double amount) {
        if (account.getMoneyAmount() < amount) {
            throw new IllegalArgumentException("Not enough money. On account with ID %d is only %.2f"
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
