package vudovenko.dev.multithreading.hw1.counters;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCounter implements SiteVisitCounter {

    private AtomicInteger atomicInteger;

    public AtomicIntegerCounter() {
        this.atomicInteger = new AtomicInteger();
    }

    @Override
    public void incrementVisitCount() {
        try {
            Thread.sleep(100);
            atomicInteger.incrementAndGet();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getVisitCount() {
        try {
            Thread.sleep(100);
            return atomicInteger.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
