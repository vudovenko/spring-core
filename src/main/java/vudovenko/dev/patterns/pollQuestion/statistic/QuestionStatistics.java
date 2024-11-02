package vudovenko.dev.patterns.pollQuestion.statistic;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения статистики по вопросу
 */
@Getter
@Setter
public class QuestionStatistics {

    private String questionTitle;
    private Map<String, Integer> selectedVariantsCount;
    private Map<String, Integer> userSelectedVariantsCount;

    public QuestionStatistics(String questionTitle) {
        this.questionTitle = questionTitle;
        this.selectedVariantsCount = new HashMap<>();
        this.userSelectedVariantsCount = new HashMap<>();
    }
}
