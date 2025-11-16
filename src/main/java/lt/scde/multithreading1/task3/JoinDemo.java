package lt.scde.multithreading1.task3;

/**
 * Task 3 – using the join() method.
 *
 * This class demonstrates how the parent thread can wait for a child thread
 * to finish before continuing its own execution.
 * <p>
 * According to the task:
 * <ul>
 *     <li>Modify the program from Task 1 so that the parent thread prints
 *     its output only <b>after</b> the created thread has finished.</li>
 *     <li>Use the {@link Thread#join()} method.</li>
 * </ul>
 */
public class JoinDemo {

    /**
     * Runs the demo for Task 3.
     * <p>
     * The method:
     * <ol>
     *     <li>Creates a child thread that prints numbers from 1 to 5.</li>
     *     <li>Starts the child thread.</li>
     *     <li>Calls {@code join()} on the child thread, so the current
     *     (parent) thread waits until the child finishes.</li>
     *     <li>After that, the parent thread prints its own lines.</li>
     * </ol>
     *
     * As a result, all child output appears first, followed strictly by
     * the parent output.
     *
     * @throws InterruptedException if the current thread is interrupted
     *                              while waiting for the child thread
     */
    public static void runDemo() throws InterruptedException {
        Thread child = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("[child] " + i);
            }
        });

        // Start the child thread
        child.start();

        // Wait until the child thread finishes
        child.join();

        // Parent prints its lines only AFTER the child has completed
        for (int i = 1; i <= 5; i++) {
            System.out.println("[parent after child] " + i);
        }
    }
}

