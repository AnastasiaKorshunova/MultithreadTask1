package lt.scde.multithreading1.task8;
/**
 * Task 8 – Producer / Consumer demo.
 *
 * <p>This demo implements the following scenario:</p>
 *
 * <ul>
 *     <li>Thread P (producer) toggles a shared boolean flag with a delay of
 *     {@code M} milliseconds.</li>
 *     <li>Thread C (consumer) waits for the flag to become {@code true}.
 *     While the flag is {@code true}, it performs a countdown from {@code K}
 *     milliseconds, printing the remaining time with a delay of {@code M/10}
 *     milliseconds between steps.</li>
 *     <li>Whenever the flag is {@code false}, the consumer pauses and waits
 *     until the producer toggles the flag back to {@code true}.</li>
 *     <li>The condition for termination is that the countdown reaches zero.</li>
 * </ul>
 *
 * <p>This implementation uses a shared {@link SharedState} object and the
 * intrinsic lock {@link SharedState#lock} together with {@code wait()} and
 * {@code notifyAll()} to synchronize the two threads.</p>
 */
public class ProducerConsumerDemo {
    /**
     * Runs the Producer / Consumer demo for Task 8.
     *
     * @throws InterruptedException if the main thread is interrupted while waiting
     */
    public static void runDemo() throws InterruptedException {
        final long m = 1000; // delay in milliseconds for producer (toggle)
        final long k = 5000; // initial countdown time in milliseconds
        final long step = m / 10; // m/10 delay and countdown step

        SharedState state = new SharedState(k);

        Producer producer = new Producer(state, m);
        Consumer consumer = new Consumer(state, step);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Task 8 complete.");
    }
}

