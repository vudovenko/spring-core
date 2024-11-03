package vudovenko.dev.patterns.strategy;

import vudovenko.dev.patterns.pollQuestion.statistic.QuestionStatistics;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractFrequencyAnswerStrategy implements AnalyzeStrategy {

    protected abstract Optional<Integer> findExtremeValue(Collection<Integer> values);

    protected abstract String getMessage();

    @Override
    public void makeAnalyze(List<QuestionStatistics> questionStatisticsList) {
        for (QuestionStatistics questionStatistics : questionStatisticsList) {
            Map<String, Integer> selectedVariantsCount = questionStatistics.getSelectedVariantsCount();
            Optional<Integer> extremeValueOptional = findExtremeValue(selectedVariantsCount.values());

            if (extremeValueOptional.isEmpty()) {
                System.out.printf(
                        "No answers available for question: \"%s\"%n",
                        questionStatistics.getQuestionTitle()
                );
                continue;
            }

            List<String> popularAnswers = selectedVariantsCount.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(extremeValueOptional.get()))
                    .map(Map.Entry::getKey)
                    .toList();

            System.out.printf(
                    getMessage(),
                    popularAnswers,
                    questionStatistics.getQuestionTitle()
            );
        }
    }
}
