package vudovenko.dev.patterns.builder.pollQuestion.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Класс ответа на вопрос из опроса
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollQuestionResponse {

    private PollQuestion pollQuestion;
    private List<String> selectedVariants;
}
