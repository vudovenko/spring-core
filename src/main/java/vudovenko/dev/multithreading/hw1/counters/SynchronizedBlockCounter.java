package vudovenko.dev.multithreading.hw1.counters;

public class SynchronizedBlockCounter implements SiteVisitCounter {

    private Integer visitCount = 0;

    @Override
    public void incrementVisitCount() {
        synchronized (this) {
            try {
                Thread.sleep(100);
                visitCount++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Integer getVisitCount() {
        synchronized (this) {
            try {
                Thread.sleep(100);
                return visitCount;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
