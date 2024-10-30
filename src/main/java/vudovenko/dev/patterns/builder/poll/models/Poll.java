package vudovenko.dev.patterns.builder.poll.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vudovenko.dev.patterns.builder.poll.builder.PollBuilder;
import vudovenko.dev.patterns.builder.pollQuestion.models.PollQuestion;

import java.util.List;

/**
 * Класс опроса
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Poll {

    private String pollName;
    List<PollQuestion> pollQuestions;

    /**
     * Создает строителя опроса
     *
     * @return строитель опроса
     */
    public static PollBuilder builder() {
        return new PollBuilder();
    }
}
