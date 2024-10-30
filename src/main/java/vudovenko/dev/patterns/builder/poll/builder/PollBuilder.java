package vudovenko.dev.patterns.builder.poll.builder;

import vudovenko.dev.patterns.builder.poll.models.Poll;
import vudovenko.dev.patterns.builder.pollQuestion.builder.PollQuestionBuilder;
import vudovenko.dev.patterns.builder.pollQuestion.models.PollQuestion;

import java.util.ArrayList;
import java.util.List;

public class PollBuilder {

    private String pollName;
    private final List<PollQuestion> pollQuestions;

    public PollBuilder() {
        pollQuestions = new ArrayList<>();
    }

    /**
     * Добавляет вопрос в опрос
     *
     * @param pollQuestion вопрос
     */
    public void addQuestionToPoll(PollQuestion pollQuestion) {
        pollQuestions.add(pollQuestion);
    }

    /**
     * Добавляет имя опросу
     *
     * @param pollName имя опроса
     * @return строитель опроса
     */
    public PollBuilder withPollName(String pollName) {
        this.pollName = pollName;
        return this;
    }

    /**
     * Добавляет вопрос в опрос
     *
     * @param title вопрос
     * @return строитель вопроса
     */
    public PollQuestionBuilder pollQuestion(String title) {
        return new PollQuestionBuilder(this)
                .withTitle(title);
    }

    /**
     * Добавляет вопрос с одним вариантом ответа
     *
     * @param title вопрос
     * @return строитель вопроса
     */
    public PollQuestionBuilder oneVariantPollQuestion(String title) {
        return new PollQuestionBuilder(this)
                .withTitle(title)
                .withMinAnswers(1)
                .withMaxAnswers(1);
    }

    /**
     * Добавляет вопрос с необязательным ответом
     *
     * @param title вопрос
     * @return строитель вопроса
     */
    public PollQuestionBuilder optionalResponsesPollQuestion(String title) {
        return new PollQuestionBuilder(this)
                .withTitle(title)
                .withMinAnswers(0);
    }

    /**
     * Добавляет вопрос с вариантами ответа "Yes"/"No".
     *
     * @param title вопрос
     * @return строитель опросника
     */
    public PollBuilder yesNoPollQuestion(String title) {
        return new PollQuestionBuilder(this)
                .withTitle(title)
                .withMinAnswers(1)
                .withMaxAnswers(1)
                .withAnswerVariant("Yes")
                .withAnswerVariant("No")
                .and();
    }

    /**
     * Завершает построение опроса
     *
     * @return объект опроса
     */
    public Poll build() {
        return new Poll(pollName, pollQuestions);
    }
}
