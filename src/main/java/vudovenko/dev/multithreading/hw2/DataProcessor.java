package vudovenko.dev.multithreading.hw2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class DataProcessor {

    @Getter
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private final AtomicInteger taskCounter = new AtomicInteger(1);
    private final AtomicInteger activeTaskCounter = new AtomicInteger(0);
    @Getter
    private final Map<String, Integer> taskResults = new HashMap<>();

    public void pushTask(List<Integer> nums) {
        String taskName = "task %d".formatted(taskCounter.get());
        taskCounter.incrementAndGet();

        CompletableFuture<Integer> completableFutureResult = CompletableFuture.supplyAsync(() -> {
            try {
                return new CalculateSumTask(nums, taskName).call();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);

        completableFutureResult
                .thenAccept(taskResult -> {
                    activeTaskCounter.incrementAndGet();
                    synchronized (taskResults) {
                        taskResults.put(taskName, taskResult);
                    }
                    activeTaskCounter.decrementAndGet();
                });
    }

    public Integer getNumberActiveTasks() {
        return activeTaskCounter.get();
    }

    public Optional<Integer> getResultByTaskName(String taskName) {
        synchronized (taskResults) {
            return Optional.ofNullable(taskResults.get(taskName));
        }
    }
}
