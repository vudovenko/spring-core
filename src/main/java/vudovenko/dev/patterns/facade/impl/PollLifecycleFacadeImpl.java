package vudovenko.dev.patterns.facade.impl;

import vudovenko.dev.patterns.facade.PollLifecycleFacade;
import vudovenko.dev.patterns.poll.models.Poll;
import vudovenko.dev.patterns.poll.models.PollFillingData;
import vudovenko.dev.patterns.pollQuestion.responseGenerator.ResponseGenerator;
import vudovenko.dev.patterns.pollQuestion.statistic.analyzer.PollAnalyzer;
import vudovenko.dev.patterns.pollQuestion.statistic.analyzer.impl.PollAnalyzerImpl;
import vudovenko.dev.patterns.pollQuestion.statistic.analyzer.impl.proxy.PollAnalyzerProxy;
import vudovenko.dev.patterns.strategy.impl.FullCountStrategy;
import vudovenko.dev.patterns.strategy.impl.LeastFrequentAnswerStrategy;
import vudovenko.dev.patterns.strategy.impl.MostFrequentAnswerStrategy;

import java.util.List;

public class PollLifecycleFacadeImpl implements PollLifecycleFacade {

    @Override
    public Poll createPoll() {
        return Poll.builder()
                .withPollName("Programming Survey")
                .yesNoPollQuestion("Are you programmer?")

                .oneVariantPollQuestion("How many years of programming experience do you have?")
                .withAnswerVariant("0-1 years")
                .withAnswerVariant("1-3 years")
                .withAnswerVariant("3-5 years")
                .withAnswerVariant("5+ years")
                .and()
                .oneVariantPollQuestion("What is your favorite programming language?")
                .withAnswerVariant("Java")
                .withAnswerVariant("Python")
                .withAnswerVariant("C++")
                .and()
                .optionalResponsesPollQuestion("Do you like your job?")
                .withMaxAnswers(1)
                .withAnswerVariant("Who knows...")
                .withAnswerVariant("Of course")
                .and()
                .pollQuestion("What are your strong qualities?")
                .withMinAnswers(0)
                .withMaxAnswers(5)
                .withAnswerVariant("Leadership")
                .withAnswerVariant("Teamwork")
                .withAnswerVariant("Problem-solving")
                .withAnswerVariant("Communication")
                .withAnswerVariant("Creativity")
                .and()
                .build();
    }

    @Override
    public List<PollFillingData> getUserResponses(Poll poll) {
        ResponseGenerator responseGenerator = new ResponseGenerator();

        return List.of(
                new PollFillingData("user1", responseGenerator.generateResponses(poll)),
                new PollFillingData("user2", responseGenerator.generateResponses(poll)),
                new PollFillingData("user3", responseGenerator.generateResponses(poll)),
                new PollFillingData("user4", responseGenerator.generateResponses(poll)),
                new PollFillingData("user5", responseGenerator.generateResponses(poll)),
                new PollFillingData("user6", responseGenerator.generateResponses(poll))
        );
    }

    @Override
    public void makeAnalyzePoll(List<PollFillingData> pollFillingDataList) {
        PollAnalyzer pollAnalyzer = new PollAnalyzerImpl(new FullCountStrategy());
        PollAnalyzer pollAnalyzerProxy = new PollAnalyzerProxy(pollAnalyzer);
        pollAnalyzerProxy.analyzePoll(pollFillingDataList);
        System.out.println("-----------------------------------------");
        pollAnalyzerProxy.changeAnalyzerStrategy(new LeastFrequentAnswerStrategy());
        pollAnalyzerProxy.analyzePoll(pollFillingDataList);
        System.out.println("-----------------------------------------");
        pollAnalyzerProxy.changeAnalyzerStrategy(new MostFrequentAnswerStrategy());
        pollAnalyzerProxy.analyzePoll(pollFillingDataList);
    }
}
