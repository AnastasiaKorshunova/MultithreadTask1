package lt.scde.multithreading1.task8;
/**
 * Shared state object for the Producer–Consumer demo (Task 8).
 *
 * <p>This class holds the state that is shared between the {@link Producer}
 * and {@link Consumer} threads:</p>
 * <ul>
 *     <li>a boolean {@code flag} that is toggled by the producer,</li>
 *     <li>a {@code remaining} time value in milliseconds used by the consumer
 *     for the countdown.</li>
 * </ul>
 *
 * <p>The {@link #lock} object is used as a monitor for {@code synchronized},
 * {@code wait()} and {@code notifyAll()} calls. Access to {@code flag} and
 * {@code remaining} must always be performed under this lock to ensure
 * thread safety.</p>
 */
public class SharedState {

    /** Monitor object used for synchronized / wait / notifyAll. */
    public final Object lock = new Object();

    private boolean flag = false;
    private long remaining;

    /**
     * Creates a new shared state with the given initial countdown value.
     *
     * @param initialRemaining initial value for the countdown (K), in milliseconds
     */
    public SharedState(long initialRemaining) {
        this.remaining = initialRemaining;
    }

    /**
     * Returns the current flag value.
     *
     * @return {@code true} if the flag is set, {@code false} otherwise
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Toggles the flag value (true → false, false → true).
     */
    public void toggleFlag() {
        flag = !flag;
    }

    /**
     * Returns the remaining countdown time.
     *
     * @return remaining time in milliseconds
     */
    public long getRemaining() {
        return remaining;
    }

    /**
     * Decreases the remaining countdown time by the given step.
     *
     * @param step value to subtract from remaining time (in milliseconds)
     */
    public void decreaseRemaining(long step) {
        remaining -= step;
    }
}
