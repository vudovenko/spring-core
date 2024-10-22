package vudovenko.dev.multithreading.hw1;

import vudovenko.dev.multithreading.hw1.counters.*;
import vudovenko.dev.multithreading.hw1.siteVisiters.MultithreadingSiteVisitor;


public class Main {

    public static void main(String[] args) {
        SiteVisitCounter reentrantLockCounter = new ReentrantLockCounter();
        SiteVisitCounter unsynchronizedCounter = new UnsynchronizedCounter();
        SiteVisitCounter volatileCounter = new VolatileCounter();
        SiteVisitCounter synchronizedBlockCounter = new SynchronizedBlockCounter();
        SiteVisitCounter atomicIntegerCounter = new AtomicIntegerCounter();

        Integer numbThreads = 100;
        startTest(volatileCounter, numbThreads);
        startTest(atomicIntegerCounter, numbThreads);
        startTest(unsynchronizedCounter, numbThreads);
        startTest(reentrantLockCounter, numbThreads);
        startTest(synchronizedBlockCounter, numbThreads);
    }

    private static void startTest(SiteVisitCounter siteVisitCounter,
                                  Integer numbThreads) {
        MultithreadingSiteVisitor multithreadingSiteVisitor
                = new MultithreadingSiteVisitor(siteVisitCounter);

        multithreadingSiteVisitor.visitMultithread(numbThreads);
//        multithreadingSiteVisitor.waitUntilAllVisited();
        System.out.printf("\nСуммарное время работы потоков %s = %.1f, значение счетчика = %d\n\n",
                siteVisitCounter.getClass(),
                multithreadingSiteVisitor.getTotalTimeOfHandling(),
                siteVisitCounter.getVisitCount());
    }
}
