package lt.scde.multithreading1.task6;

import java.util.concurrent.TimeUnit;

/**
 * Task 6 – Thread priority demonstration.
 *
 * <p>This demo compares the execution speed of two threads with different
 * priorities: one with {@link Thread#MIN_PRIORITY} and one with
 * {@link Thread#MAX_PRIORITY}. Each thread performs a busy computation
 * for a fixed amount of time (1.5 seconds) and counts how many loop
 * operations it can complete during that period.</p>
 *
 * <p><b>Expected behavior:</b><br>
 * Higher thread priority is a scheduling <i>hint</i> to the JVM and the
 * operating system, not a strict guarantee. In many environments the
 * higher-priority thread may perform more operations, but depending on
 * OS/JVM implementation, the difference may be small or nonexistent.</p>
 *
 * <p><b>Observation:</b><br>
 * The printed operation counts (ops=...) indicate how much CPU time each
 * thread received. If the Max-priority thread shows a higher count, it
 * means the scheduler favored it. If both values are similar, the system
 * ignored priority or treated it minimally.</p>
 */
public class PriorityTask {
    /**
     * Runs the priority demonstration.
     *
     * @throws InterruptedException if the main thread is interrupted while waiting
     */
    public static void runDemo() throws InterruptedException {
        Runnable busy = () -> {
            long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(1500);
            long ops = 0;
            while (System.nanoTime() < end) {
                ops++;
            }
            System.out.println(Thread.currentThread().getName() +
                    " (priority=" + Thread.currentThread().getPriority() + ") ops=" + ops);
        };
        Thread min = new Thread(busy, "MinPriorityThread");
        Thread max = new Thread(busy, "MaxPriorityThread");
        min.setPriority(Thread.MIN_PRIORITY); // 1
        max.setPriority(Thread.MAX_PRIORITY); // 10
        System.out.println("Starting threads with different priorities...");
        max.start();
        min.start();
        max.join();
        min.join();
        System.out.println("PriorityTask complete.\n");
    }
}

