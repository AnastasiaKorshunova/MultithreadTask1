package lt.scde.multithreading1.task8;

/**
 * Consumer thread for Task 8 (Producer / Consumer).
 *
 * <p>The consumer (C) waits for the producer's flag to become {@code true}.
 * When the flag is {@code true}, it:</p>
 * <ul>
 *     <li>prints the current remaining time K,</li>
 *     <li>decreases the remaining time by {@code step = M/10},</li>
 *     <li>sleeps for {@code step} milliseconds between countdown steps.</li>
 * </ul>
 *
 * <p>If the flag is {@code false}, the consumer waits on the shared monitor
 * until the producer toggles it to {@code true}. The consumer stops when
 * the countdown reaches zero or below.</p>
 *
 * <p>This behavior corresponds to the task requirement that the consumer
 * pauses its work whenever the producer switches the state back to false
 * and resumes when the state becomes true again.</p>
 */
public class Consumer extends Thread {
    private final SharedState state;
    private final long step;
    /**
     * Creates a new consumer.
     *
     * @param state shared state object containing the flag and countdown
     * @param step  countdown step (M/10) in milliseconds
     */
    public Consumer(SharedState state, long step) {
        super("Consumer");
        this.state = state;
        this.step = step;
    }
    @Override
    public void run() {
        try {
            while (true) {
                synchronized (state.lock) {
                    while (!state.getFlag() && state.getRemaining() > 0) {
                        state.lock.wait();
                    }
                    if (state.getRemaining() <= 0) {
                        System.out.println("[C] countdown finished");
                        return;
                    }
                    System.out.println("[C] remaining = " + state.getRemaining() + " ms");
                    state.decreaseRemaining(step);
                    if (state.getRemaining() <= 0) {
                        System.out.println("[C] done (remaining <= 0)");
                        state.lock.notifyAll();
                        return;
                    }
                }
                Thread.sleep(step);
            }
        } catch (InterruptedException ignored) {
        }
    }
}
