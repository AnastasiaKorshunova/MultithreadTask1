package org.example;

import java.util.concurrent.TimeUnit;

// Task 7 — yield
public class Task7Yield {
    public static void runDemo() throws InterruptedException {
        Runnable withYield = () -> {
            long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(1500);
            long ops = 0;
            while (System.nanoTime() < end) {
                ops++;
                Thread.yield();
            }
            System.out.println("withYield ops=" + ops);
        };
        Runnable noYield = () -> {
            long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(1500);
            long ops = 0;
            while (System.nanoTime() < end) ops++;
            System.out.println("noYield  ops=" + ops);
        };
        Thread a = new Thread(withYield);
        Thread b = new Thread(noYield);
        a.start();
        b.start();
        a.join();
        b.join();
    }
}
