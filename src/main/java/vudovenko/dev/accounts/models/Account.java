package vudovenko.dev.accounts.models;


import lombok.*;

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
    private Long moneyAmount;
}
