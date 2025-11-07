package org.example;

// Task 3 — join
public class Task3Join {
    public static void runDemo() throws InterruptedException {
        Thread child = new Thread(() -> {
            for (int i = 1; i <= 5; i++) System.out.println("[child] " + i);
        });
        child.start();
        child.join();
        for (int i = 1; i <= 5; i++) System.out.println("[parent after child] " + i);
    }
}