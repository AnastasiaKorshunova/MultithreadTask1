package lt.scde.multithreading1.task5;
/**
 * InterruptedException demo.
 *
 * This example creates a situation where a thread is interrupted while
 * it is blocked inside the {@link Thread#join()} method.
 */
public class InterruptedExceptionDemo {

    /** How long the worker simulates its work (in milliseconds). */
//    private static final int WORK_TIME_MS = 0;
    private static final int WORK_TIME_MS = 2000;
    /**
     * How long the main thread waits before interrupting the joiner
     * (in milliseconds).
     *
     * If this value is too large, the worker may finish before the
     * joiner actually starts waiting in join(), and no InterruptedException
     * will be thrown.
     */
    private static final int INTERRUPT_DELAY_MS = 100;

    /*
     * Alternative way to simulate long work:
     * uncomment and use the loop instead of Thread.sleep().
     *
     * private static final long WORK_ITERATIONS = 100_000_000L;
     */

    /**
     * Runs the InterruptedException demonstration.
     *
     * @throws InterruptedException if the main thread is interrupted
     */
    public static void runDemo() throws InterruptedException {

        Thread worker = new Thread(() -> {
            System.out.println("worker: started work...");
            try {
                // Option 1: simulate long work with sleep
                Thread.sleep(WORK_TIME_MS);
                // Option 2: simulate long work with a busy loop
                // for (long i = 0; i < WORK_ITERATIONS; i++) {
                //     // do nothing, just burn CPU
                // }
            } catch (InterruptedException ignored) {
                System.out.println("worker: was interrupted during sleep");
            }
            System.out.println("worker: finished work");
        });

        Thread joiner = new Thread(() -> {
            try {
                System.out.println("joiner: waiting for worker (join)...");
                worker.join(); // blocking here
                System.out.println("joiner: finished normally (no interrupt)");
            } catch (InterruptedException e) {
                System.out.println("joiner: interrupted during join()");
            }
        });

        worker.start();
        joiner.start();
        Thread.sleep(INTERRUPT_DELAY_MS);
        System.out.println("main: interrupting joiner...");
        joiner.interrupt();
        worker.join();
        joiner.join();
        System.out.println("main: InterruptedException demo complete");
    }
}
