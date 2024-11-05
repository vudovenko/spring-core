package vudovenko.dev.patterns.pollQuestion.statistic.analyzer.impl.proxy;

import vudovenko.dev.patterns.poll.models.PollFillingData;
import vudovenko.dev.patterns.pollQuestion.statistic.analyzer.PollAnalyzer;
import vudovenko.dev.patterns.strategy.AnalyzeStrategy;

import java.util.List;

public class PollAnalyzerProxy implements PollAnalyzer {

    private final PollAnalyzer pollAnalyzerDelegate;

    public PollAnalyzerProxy(PollAnalyzer pollAnalyzerDelegate) {
        this.pollAnalyzerDelegate = pollAnalyzerDelegate;
    }

    @Override
    public void analyzePoll(List<PollFillingData> pollFillingDataList) {
        String strategyName = pollAnalyzerDelegate.getStrategy().getClass().getSimpleName();
        System.out.printf("*****Starting analyze poll by strategy: %s%n",
                strategyName);
        long startTime = System.currentTimeMillis();
        pollAnalyzerDelegate.analyzePoll(pollFillingDataList);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.printf("*****Ending analyze poll by strategy:" +
                " %s, totalAnalyzeTimeMS: %d%n", strategyName, executionTime);
    }

    @Override
    public AnalyzeStrategy getStrategy() {
        return pollAnalyzerDelegate.getStrategy();
    }

    @Override
    public void changeAnalyzerStrategy(AnalyzeStrategy strategy) {
        pollAnalyzerDelegate.changeAnalyzerStrategy(strategy);
    }
}
