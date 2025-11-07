package org.example;

// Task 5
public class Task5InterruptedException {
    public static void runDemo() throws InterruptedException {
        Thread worker = new Thread(() -> {
            for (int i = 0; i < 100_000_000; i++) {
            }
        });
        Thread joiner = new Thread(() -> {
            try {
                worker.join();
                System.out.println("joiner: finished normally");
            } catch (InterruptedException e) {
                System.out.println("joiner: interrupted while joining");
            }
        });

        worker.start();
        joiner.start();

        joiner.interrupt();

        worker.join();
        joiner.join();
    }
}
