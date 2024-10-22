package vudovenko.dev.multithreading.hw2;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class CalculateSumTask implements Callable<Integer> {

    private final String taskName;
    private final List<Integer> numbers;

    public CalculateSumTask(List<Integer> numb, String taskName) {
        this.numbers = numb;
        this.taskName = taskName;
    }

    @Override
    public Integer call() throws InterruptedException {
        int workDuration = new Random().nextInt(4000) + 1000;
        System.out.printf("Текущая задача - %s, поток - %s в работе на %d миллисекунд\n",
                taskName,
                Thread.currentThread().getName(),
                workDuration);
        int sum = numbers
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        Thread.sleep(workDuration);
        System.out.printf("Текущая задача - %s, поток - %s завершилась\n",
                taskName,
                Thread.currentThread().getName());

        return sum;
    }
}
