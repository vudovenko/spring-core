package vudovenko.dev.patterns.builder.poll.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vudovenko.dev.patterns.builder.pollQuestion.models.PollQuestionResponse;

import java.util.List;

/**
 * Класс результата заполнения пользователями опроса
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollFillingData {

    private String username;
    private List<PollQuestionResponse> responses;
}
