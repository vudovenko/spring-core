package vudovenko.dev.patterns.strategy.impl;

import vudovenko.dev.patterns.pollQuestion.statistic.QuestionStatistics;
import vudovenko.dev.patterns.strategy.AnalyzeStrategy;

import java.util.List;

public class FullCountStrategy implements AnalyzeStrategy {

    @Override
    public void makeAnalyze(List<QuestionStatistics> questionStatisticsList) {
        questionStatisticsList.forEach(questionStatistics -> {
            System.out.printf("Question: \"%s\"%n", questionStatistics.getQuestionTitle());

            questionStatistics
                    .getSelectedVariantsCount()
                    .forEach((key, value) -> System.out.printf("%s: %d%n", key, value));
            System.out.println();
        });
    }
}
