package vudovenko.dev.hw1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import vudovenko.dev.hw1.operations.listeners.OperationsConsoleListener;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext("vudovenko.dev");

        OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);

        Thread threadListener = new Thread(consoleListener);
        threadListener.start();
        threadListener.join();

        context.close();
    }
}