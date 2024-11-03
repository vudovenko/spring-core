package vudovenko.dev.patterns.strategy;

import vudovenko.dev.patterns.pollQuestion.statistic.QuestionStatistics;

import java.util.List;

public interface AnalyzeStrategy {

    void makeAnalyze(List<QuestionStatistics> questionStatisticsList);
}
