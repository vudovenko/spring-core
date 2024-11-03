package vudovenko.dev.patterns.strategy.impl;

import vudovenko.dev.patterns.pollQuestion.statistic.QuestionStatistics;
import vudovenko.dev.patterns.strategy.AnalyzeStrategy;

import java.util.List;

public class FullCountStrategy implements AnalyzeStrategy {

    @Override
    public void makeAnalyze(List<QuestionStatistics> questionStatisticsList) {
        questionStatisticsList
                .stream()
                .flatMap(questionStatistics -> questionStatistics.getSelectedVariantsCount().entrySet().stream())
                .forEach(System.out::println);
    }
}
