package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Task 1 ===");
        Task1CreateThread.runDemo();

        System.out.println("\n=== Task 2 ===");
        Task2ThreadRunnable.runDemo();

        System.out.println("\n=== Task 3 ===");
        Task3Join.runDemo();

        System.out.println("\n=== Task 4 ===");
        Task4ThreadParameters.runDemo();

        System.out.println("\n=== Task 5 ===");
        Task5InterruptedException.runDemo();

        System.out.println("\n=== Task 6 ===");
        Task6Priority.runDemo();

        System.out.println("\n=== Task 7 ===");
        Task7Yield.runDemo();

//        System.out.println("\n=== Task 8 ===");
//        Task8ProducerConsumer.runDemo();
    }

}