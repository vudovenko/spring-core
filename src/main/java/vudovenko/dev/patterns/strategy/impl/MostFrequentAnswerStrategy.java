package vudovenko.dev.patterns.strategy.impl;

import vudovenko.dev.patterns.strategy.AbstractFrequencyAnswerStrategy;

import java.util.Collection;
import java.util.Optional;

public class MostFrequentAnswerStrategy extends AbstractFrequencyAnswerStrategy {


    @Override
    protected Optional<Integer> findExtremeValue(Collection<Integer> values) {
        return values.stream().max(Integer::compareTo);
    }

    @Override
    protected String getMessage() {
        return "The most popular answers: %s for question: \"%s\"%n";
    }
}
