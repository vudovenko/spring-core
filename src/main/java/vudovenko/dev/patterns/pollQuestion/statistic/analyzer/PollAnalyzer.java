package vudovenko.dev.patterns.pollQuestion.statistic.analyzer;

import vudovenko.dev.patterns.poll.models.PollFillingData;
import vudovenko.dev.patterns.pollQuestion.models.PollQuestion;
import vudovenko.dev.patterns.pollQuestion.models.PollQuestionResponse;
import vudovenko.dev.patterns.pollQuestion.statistic.QuestionStatistics;
import vudovenko.dev.patterns.strategy.AnalyzeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PollAnalyzer {

    private AnalyzeStrategy strategy;

    public PollAnalyzer(AnalyzeStrategy strategy) {
        this.strategy = strategy;
    }

    public void changeAnalyzerStrategy(AnalyzeStrategy strategy) {
        this.strategy = strategy;
    }

    public void analyzePoll(List<PollFillingData> pollFillingDataList) {
        List<QuestionStatistics> statistics = collectStatistics(pollFillingDataList);
        strategy.makeAnalyze(statistics);
    }

    private List<QuestionStatistics> collectStatistics(List<PollFillingData> pollFillingDataList) {
        // PollQuestion
        // ------------------
        // - название вопроса
        // - (варианты ответа - частота выбора)
        // - (пользователи - количество выбранных ответов)
        if (pollFillingDataList.isEmpty()) {
            return new ArrayList<>();
        }
        List<PollQuestion> questions = getQuestions(pollFillingDataList);
        Map<PollQuestion, QuestionStatistics> questionToStatistics = initializeStatistics(questions);
        calculateStatistics(pollFillingDataList, questionToStatistics);

        return questionToStatistics.values().stream().toList();
    }

    private List<PollQuestion> getQuestions(List<PollFillingData> pollFillingDataList) {
        return pollFillingDataList
                .getFirst()
                .pollQuestionResponseList().stream()
                .map(PollQuestionResponse::pollQuestion)
                .toList();
    }

    private Map<PollQuestion, QuestionStatistics> initializeStatistics(
            List<PollQuestion> pollQuestions
    ) {
        return pollQuestions
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        pollQuestion -> {
                            QuestionStatistics questionStatistics
                                    = new QuestionStatistics(pollQuestion.getTitle());
                            Map<String, Integer> selectedVariantsCount
                                    = questionStatistics.getSelectedVariantsCount();
                            pollQuestion.getAnswers()
                                    .forEach(answer -> selectedVariantsCount
                                            .put(answer, 0));

                            return questionStatistics;
                        })
                );
    }

    private void calculateStatistics(
            List<PollFillingData> pollFillingDataList,
            Map<PollQuestion, QuestionStatistics> questionToStatistics
    ) {
        for (PollFillingData pollFillingData : pollFillingDataList) {
            calculateSelectedVariantsCount(questionToStatistics, pollFillingData);
            calculateUserSelectedVariantsCount(questionToStatistics, pollFillingData);
        }
    }

    private void calculateSelectedVariantsCount(
            Map<PollQuestion, QuestionStatistics> questionToStatistics,
            PollFillingData pollFillingData
    ) {
        for (PollQuestionResponse pollQuestionResponse
                : pollFillingData.pollQuestionResponseList()) {
            Map<String, Integer> selectedVariantsCount
                    = questionToStatistics
                    .get(pollQuestionResponse.pollQuestion())
                    .getSelectedVariantsCount();
            pollQuestionResponse.selectedVariants()
                    .forEach(selectedVariant -> {
                        selectedVariantsCount
                                .compute(selectedVariant,
                                        (_, selectionCounter) -> selectionCounter == null
                                                ? 1
                                                : selectionCounter + 1);
                    });
        }
    }

    private void calculateUserSelectedVariantsCount(
            Map<PollQuestion, QuestionStatistics> questionToStatistics,
            PollFillingData pollFillingData
    ) {
        for (PollQuestionResponse pollQuestionResponse : pollFillingData.pollQuestionResponseList()) {
            int numberSelectedVariants = pollQuestionResponse.selectedVariants().size();
            QuestionStatistics questionStatistics = questionToStatistics
                    .get(pollQuestionResponse.pollQuestion());
            questionStatistics
                    .getUserSelectedVariantsCount()
                    .compute(pollFillingData.userLogin(),
                            (_, selectionCounter) -> selectionCounter == null
                                    ? numberSelectedVariants
                                    : selectionCounter + numberSelectedVariants);
        }
    }
}
