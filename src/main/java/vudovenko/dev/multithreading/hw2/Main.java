package vudovenko.dev.multithreading.hw2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        DataProcessor dataProcessor = new DataProcessor();

        for (int taskNumber = 0; taskNumber < 100; taskNumber++) {
            List<Integer> numbers = getNumbersForTask(taskNumber);
            dataProcessor.pushTask(numbers);
        }
        while (dataProcessor.getNumberActiveTasks() > 0) {
            System.out.printf("Количество активных задач: %d\n", dataProcessor.getNumberActiveTasks());
            Thread.sleep(500);
        }
        dataProcessor.getExecutorService().shutdown();
        boolean awaited = dataProcessor.getExecutorService()
                .awaitTermination(30, TimeUnit.MINUTES);
        dataProcessor.getExecutorService().shutdown();
        if (awaited) {
            System.out.println("Задачи завершены в течение 30 минут");
        }
        dataProcessor.getTaskResults()
                .keySet()
                .forEach(taskName -> {
                    dataProcessor.getResultByTaskName(taskName)
                            .ifPresent(result ->
                                    System.out.printf("Задача %s - %d\n",
                                            taskName,
                                            result));
                });

    }

    private static List<Integer> getNumbersForTask(int taskNumber) {
        List<Integer> numbers = new ArrayList<>();
        for (int j = 1; j <= 10; j++) {
            numbers.add(j + (taskNumber * 10));
        }
        return numbers;
    }
}
