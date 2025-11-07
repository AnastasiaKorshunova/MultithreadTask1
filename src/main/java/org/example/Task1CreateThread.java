package org.example;

// Task 1
public class Task1CreateThread {
    public static void runDemo() throws InterruptedException {
        final int N = 5;
        Thread child = new Thread(() -> {
            for (int i = 1; i <= N; i++) {
                System.out.println("[child] line " + i);
            }
        });
        child.start();
        for (int i = 1; i <= N; i++) {
            System.out.println("[parent] line " + i);
        }
        child.join();
    }
}