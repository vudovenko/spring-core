package vudovenko.dev.hw.accounts.models;


import jakarta.persistence.*;
import vudovenko.dev.hw.users.models.User;

/**
 * Модель счета пользователя
 */
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "money_amount")
    private Double moneyAmount;

    public Account() {
    }

    public Account(Long id, User user, Double moneyAmount) {
        this.id = id;
        this.user = user;
        this.moneyAmount = moneyAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
