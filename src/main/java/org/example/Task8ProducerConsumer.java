package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Task8ProducerConsumer {
    public static void runDemo() throws InterruptedException {
        final long M = 200;    // задержка переключения (мс)
        final long K = 2000;   // старт обратного отсчёта
        final long step = M / 10;

        SharedState shared = new SharedState(false, K);
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread producer = new Thread(() -> {
            try {
                while (true) {
                    lock.lock();
                    try {
                        if (shared.remaining <= 0) break;
                        shared.flag = !shared.flag;
                        System.out.println("[P] state -> " + shared.flag);
                        condition.signalAll();
                    } finally {
                        lock.unlock();
                    }
                    // вместо sleep(M) — ожидаем M миллисекунд
                    lock.lock();
                    try {
                        condition.await(M, TimeUnit.MILLISECONDS);
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }, "P");

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    lock.lock();
                    try {
                        while (!shared.flag) condition.await(); // ждёт true
                        if (shared.remaining <= 0) break;
                        System.out.println("[C] left=" + shared.remaining + " ms");
                        shared.remaining -= step;
                        if (shared.remaining <= 0) {
                            System.out.println("[C] done (remaining=0)");
                            condition.signalAll();
                            return;
                        }
                    } finally {
                        lock.unlock();
                    }
                    // имитация M/10 через await с таймаутом
                    lock.lock();
                    try {
                        condition.await(step, TimeUnit.MILLISECONDS);
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }, "C");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

    static class SharedState {
        boolean flag;
        long remaining;
        SharedState(boolean flag, long remaining) {
            this.flag = flag;
            this.remaining = remaining;
        }
    }
}
