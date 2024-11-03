package vudovenko.dev.patterns.strategy.impl;

import vudovenko.dev.patterns.pollQuestion.statistic.QuestionStatistics;
import vudovenko.dev.patterns.strategy.AnalyzeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MostFrequentAnswerStrategy implements AnalyzeStrategy {

    @Override
    public void makeAnalyze(List<QuestionStatistics> questionStatisticsList) {
        for (QuestionStatistics questionStatistics : questionStatisticsList) {
            int max = Integer.MIN_VALUE;
            List<String> popularAnswers = new ArrayList<>();
            for (Map.Entry<String, Integer> entry
                    : questionStatistics.getSelectedVariantsCount().entrySet()) {
                String variant = entry.getKey();
                int count = entry.getValue();
                if (count > max) {
                    max = count;
                    popularAnswers.clear();
                    popularAnswers.add(variant);
                } else if (count == max) {
                    popularAnswers.add(variant);
                }
            }
            System.out.printf(
                    "The most popular answers: %s for question: \"%s\"%n",
                    popularAnswers,
                    questionStatistics.getQuestionTitle()
            );
        }
    }
}
