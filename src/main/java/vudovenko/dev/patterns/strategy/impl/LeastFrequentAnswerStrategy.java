package vudovenko.dev.patterns.strategy.impl;

import vudovenko.dev.patterns.strategy.AbstractFrequencyAnswerStrategy;

import java.util.Collection;
import java.util.Optional;

public class LeastFrequentAnswerStrategy extends AbstractFrequencyAnswerStrategy {

    @Override
    protected Optional<Integer> findExtremeValue(Collection<Integer> values) {
        return values.stream().min(Integer::compareTo);
    }

    @Override
    protected String getMessage() {
        return "Unpopular answers: %s for question: %s%n";
    }
}
