package vudovenko.dev.patterns.builder.pollQuestion.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Класс вопроса из опроса
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollQuestion {

    private String title;
    private Integer minAnswers;
    private Integer maxAnswers;
    private List<String> answers;
}
