package vudovenko.dev.patterns.pollQuestion.responseGenerator;

import vudovenko.dev.patterns.poll.models.Poll;
import vudovenko.dev.patterns.pollQuestion.models.PollQuestionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ResponseGenerator {

    public List<PollQuestionResponse> generateResponses(Poll poll) {
        return poll.getPollQuestions()
                .stream()
                .map(pollQuestion ->
                        new PollQuestionResponse(
                                pollQuestion,
                                getRandomAnswers(
                                        pollQuestion.getAnswers(),
                                        pollQuestion.getMinAnswers(),
                                        pollQuestion.getMaxAnswers()
                                )
                        ))
                .toList();
    }

    private List<String> getRandomAnswers(List<String> answers, int minAnswers, int maxAnswers) {
        minAnswers = Math.max(0, minAnswers);
        maxAnswers = Math.min(answers.size(), maxAnswers);

        Integer numberAnswers = getNumberAnswers(minAnswers, maxAnswers);

        return selectAnswers(answers, numberAnswers);
    }

    private Integer getNumberAnswers(int minAnswers, int maxAnswers) {
        int delta = maxAnswers - minAnswers;
        return delta == 0 ? minAnswers : new Random().nextInt(delta + 1) + minAnswers;
    }

    private List<String> selectAnswers(List<String> answers, Integer numberAnswers) {
        if (numberAnswers > answers.size() || answers.isEmpty()) {
            return answers;
        }

        List<String> answersToSelect = new ArrayList<>(answers);
        Collections.shuffle(answersToSelect);

        return answersToSelect.subList(0, numberAnswers);
    }
}
