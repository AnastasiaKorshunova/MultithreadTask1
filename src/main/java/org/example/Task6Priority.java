package org.example;

// Task 6
class Task6Priority {
    public static void runDemo() throws InterruptedException {
        Runnable busy = () -> {
            long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(1500);
            long ops = 0;
            while (System.nanoTime() < end) ops++;
            System.out.println(Thread.currentThread().getName() + " ops=" + ops);
        };
        Thread min = new Thread(busy, "Min");
        Thread max = new Thread(busy, "Max");
        min.setPriority(Thread.MIN_PRIORITY);
        max.setPriority(Thread.MAX_PRIORITY);
        max.start();
        min.start();
        max.join();
        min.join();
    }
}
