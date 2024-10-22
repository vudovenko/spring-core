package vudovenko.dev.multithreading.hw1.counters;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter implements SiteVisitCounter {

    private Integer visitCount = 0;
    private final ReentrantLock reentrantLock;

    public ReentrantLockCounter() {
        this.reentrantLock = new ReentrantLock();
    }

    @Override
    public void incrementVisitCount() {
        reentrantLock.lock();
        try {
            Thread.sleep(100);
            visitCount++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public Integer getVisitCount() {
        reentrantLock.lock();
        try {
            Thread.sleep(100);
            return visitCount;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reentrantLock.unlock();
        }
    }
}
