package vudovenko.dev.patterns.pollQuestion.statistic.analyzer;

import vudovenko.dev.patterns.poll.models.PollFillingData;
import vudovenko.dev.patterns.strategy.AnalyzeStrategy;

import java.util.List;

public interface PollAnalyzer {

    void analyzePoll(List<PollFillingData> pollFillingDataList);

    AnalyzeStrategy getStrategy();

    void changeAnalyzerStrategy(AnalyzeStrategy strategy);
}
