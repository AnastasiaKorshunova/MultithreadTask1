package lt.scde.multithreading1.task2;

/**
 * Task 2 – Thread vs Runnable demo.
 *
 * Creates an instance of T1Runnable and passes it to T1Thread.
 * When the thread is started, the Runnable's run() method is executed
 * (unless T1Thread overrides run()).
 */
public class ThreadRunnable {

    /**
     * Runs the demo for Task 2.
     *
     * @throws InterruptedException if the current thread is interrupted
     *                              while waiting for the thread to finish
     */
    public static void runDemo() throws InterruptedException {
        Thread thread = new T1Thread(new T1Runnable());
        thread.start();
        thread.join();
    }
}
