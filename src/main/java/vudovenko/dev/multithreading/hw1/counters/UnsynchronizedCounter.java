package vudovenko.dev.multithreading.hw1.counters;

public class UnsynchronizedCounter implements SiteVisitCounter {

    private Integer visitCount = 0;

    @Override
    public void incrementVisitCount() {
        try {
            Thread.sleep(100);
            visitCount++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getVisitCount() {
        try {
            Thread.sleep(100);
            return visitCount;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
