package vudovenko.dev.patterns.poll.models;

import vudovenko.dev.patterns.pollQuestion.models.PollQuestionResponse;

import java.util.List;

/**
 * Результат заполнения опроса пользователем
 *
 * @param userLogin логин пользователя
 * @param pollQuestionResponseList список ответов пользователя
 */
public record PollFillingData(
        String userLogin,
        List<PollQuestionResponse> pollQuestionResponseList
) {
}
