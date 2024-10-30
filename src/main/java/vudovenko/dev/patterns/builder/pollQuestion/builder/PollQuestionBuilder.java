package vudovenko.dev.patterns.builder.pollQuestion.builder;

import vudovenko.dev.patterns.builder.poll.builder.PollBuilder;
import vudovenko.dev.patterns.builder.pollQuestion.models.PollQuestion;

import java.util.ArrayList;
import java.util.List;

public class PollQuestionBuilder {

    private String title;
    private Integer minAnswers;
    private Integer maxAnswers;
    private final PollBuilder pollBuilder;
    private final List<String> answers;

    public PollQuestionBuilder(PollBuilder pollBuilder) {
        answers = new ArrayList<>();
        this.pollBuilder = pollBuilder;
    }

    /**
     * Добавляет название вопросу
     *
     * @param title название вопроса
     * @return строитель вопроса
     */
    public PollQuestionBuilder withTitle(String title) {
        this.title = title;

        return this;
    }

    /**
     * Добавляет минимальное количество ответов
     *
     * @param minAnswers минимальное количество ответов
     * @return строитель вопроса
     */
    public PollQuestionBuilder withMinAnswers(int minAnswers) {
        this.minAnswers = minAnswers;

        return this;
    }

    /**
     * Добавляет максимальное количество ответов
     *
     * @param maxAnswers максимальное количество ответов
     * @return строитель вопроса
     */
    public PollQuestionBuilder withMaxAnswers(int maxAnswers) {
        this.maxAnswers = maxAnswers;

        return this;
    }

    /**
     * Добавляет вариант ответа
     *
     * @param answerVariant вариант ответа к добавляемому вопросу
     * @return строитель вопроса
     */
    public PollQuestionBuilder withAnswerVariant(String answerVariant) {
        answers.add(answerVariant);

        return this;
    }

    /**
     * Завершает построение вопроса и возвращает к PollBuilder
     *
     * @return строитель опроса
     */
    public PollBuilder and() {
        pollBuilder
                .addQuestionToPoll(new PollQuestion(title, minAnswers, maxAnswers, answers));

        return pollBuilder;
    }
}
