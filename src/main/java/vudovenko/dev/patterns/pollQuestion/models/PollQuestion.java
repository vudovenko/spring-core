package vudovenko.dev.patterns.pollQuestion.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollQuestion that = (PollQuestion) o;
        return Objects.equals(title, that.title) && Objects.equals(minAnswers, that.minAnswers) && Objects.equals(maxAnswers, that.maxAnswers) && Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, minAnswers, maxAnswers, answers);
    }
}
