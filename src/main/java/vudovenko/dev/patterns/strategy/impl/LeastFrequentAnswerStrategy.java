package vudovenko.dev.patterns.strategy.impl;

import vudovenko.dev.patterns.pollQuestion.statistic.QuestionStatistics;
import vudovenko.dev.patterns.strategy.AnalyzeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeastFrequentAnswerStrategy implements AnalyzeStrategy {

    @Override
    public void makeAnalyze(List<QuestionStatistics> questionStatisticsList) {
        for (QuestionStatistics questionStatistics : questionStatisticsList) {
            int min = Integer.MAX_VALUE;
            List<String> unpopularAnswers = new ArrayList<>();
            for (Map.Entry<String, Integer> entry
                    : questionStatistics.getSelectedVariantsCount().entrySet()) {
                String variant = entry.getKey();
                int count = entry.getValue();
                if (count < min) {
                    min = count;
                    unpopularAnswers.clear();
                    unpopularAnswers.add(variant);
                } else if (count == min) {
                    unpopularAnswers.add(variant);
                }
            }
            System.out.printf(
                    "Unpopular answers: %s for question: %s%n",
                    unpopularAnswers,
                    questionStatistics.getQuestionTitle()
            );
        }
    }
}
