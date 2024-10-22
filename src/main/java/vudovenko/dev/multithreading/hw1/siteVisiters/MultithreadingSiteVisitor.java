package vudovenko.dev.multithreading.hw1.siteVisiters;

import lombok.RequiredArgsConstructor;
import vudovenko.dev.multithreading.hw1.counters.SiteVisitCounter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MultithreadingSiteVisitor {

    private final SiteVisitCounter siteVisitCounter;

    private final List<Thread> threads = new ArrayList<>();

    private BigDecimal totalTimeHandling = BigDecimal.ZERO;

    public void visitMultithread(int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(siteVisitCounter::incrementVisitCount);
            threads.add(thread);
        }
        long start = System.currentTimeMillis();
        threads.forEach(Thread::start);
        waitUntilAllVisited();
        long end = System.currentTimeMillis();
        totalTimeHandling = totalTimeHandling
                .add(BigDecimal.valueOf(end - start));
    }

    public void waitUntilAllVisited() {
        threads
                .forEach(thread -> {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public Double getTotalTimeOfHandling() {
        return totalTimeHandling
                .divide(new BigDecimal(1000), 1, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
