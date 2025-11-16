package lt.scde.multithreading1.task1;

/**
 * Demonstrates basic thread creation using default thread attributes.
 * <p>
 * The parent thread creates a child thread. Both the parent and the child
 * print {@code N} lines of text to the console. This example corresponds
 * to Task 1 of the multithreading assignment:
 * <ul>
 *     <li>Create a thread using default attributes.</li>
 *     <li>Both the parent thread and the newly created thread must print
 *     {@code N} lines of text.</li>
 * </ul>
 */
public class CreateThread {

    /**
     * Runs the demo for Task 1: creating a thread and printing {@code N} lines
     * from both the parent and the child thread.
     * <p>
     * The method:
     * <ol>
     *     <li>Defines a constant {@code N} – the number of lines to print.</li>
     *     <li>Creates a child thread that prints {@code N} lines.</li>
     *     <li>Starts the child thread.</li>
     *     <li>Prints {@code N} lines from the parent thread.</li>
     *     <li>Waits for the child thread to finish using {@link Thread#join()}.</li>
     * </ol>
     *
     * @throws InterruptedException if the current thread is interrupted
     *                              while waiting for the child thread to finish
     */
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
//        child.join();
    }
}

