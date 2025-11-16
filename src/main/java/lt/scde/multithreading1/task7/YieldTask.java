package lt.scde.multithreading1.task7;
import java.util.concurrent.TimeUnit;

/**
 * Task 7 – Thread.yield() demonstration.
 *
 * <p>This demo compares two threads performing identical busy work for a fixed
 * duration (1.5 seconds). One thread explicitly calls {@link Thread#yield()}
 * inside its loop, while the other performs the same work without yielding.</p>
 *
 * <p><b>Purpose:</b><br>
 * The goal is to observe whether {@code yield()} significantly affects the
 * execution speed of the yielding thread compared to the non-yielding one.</p>
 *
 * <p><b>How it works:</b></p>
 * <ul>
 *     <li>{@code YieldThread} increments its counter and calls {@code yield()}
 *     in each iteration.</li>
 *     <li>{@code NoYieldThread} simply increments its counter as fast as possible.</li>
 *     <li>Both run for exactly the same amount of time (1.5 seconds).</li>
 * </ul>
 *
 * <p><b>Observation:</b><br>
 * The thread using {@code yield()} typically performs significantly fewer
 * operations because {@code yield()} is a scheduling hint indicating that the
 * current thread is willing to pause and allow other runnable threads to run.
 * However, the exact effect depends on the JVM and operating system scheduler:
 * sometimes the difference is large, sometimes minimal.</p>
 *
 * <p>After both threads finish, their operation counts are printed for comparison.</p>
 */
public class YieldTask {
    /**
     * Runs the Thread.yield() demonstration.
     *
     * @throws InterruptedException if the main thread is interrupted
     */
    public static void runDemo() throws InterruptedException {
        Runnable withYield = () -> {
            long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(1500);
            long ops = 0;
            while (System.nanoTime() < end) {
                ops++;
                Thread.yield(); // voluntarily give up CPU
            }
            System.out.println(Thread.currentThread().getName() +
                    " (with yield) ops=" + ops);
        };
        Runnable noYield = () -> {
            long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(1500);
            long ops = 0;
            while (System.nanoTime() < end) {
                ops++; // no yielding
            }
            System.out.println(Thread.currentThread().getName() +
                    " (no yield)  ops=" + ops);
        };
        Thread tYield = new Thread(withYield, "YieldThread");
        Thread tNoYield = new Thread(noYield, "NoYieldThread");
        tYield.start();
        tNoYield.start();
        tYield.join();
        tNoYield.join();
        System.out.println("Task 7 complete.\n");
    }
}
