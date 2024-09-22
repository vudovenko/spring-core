package vudovenko.dev.accounts.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель счета пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Long id;
    private Long userId;
    private Long moneyAmount;
}
