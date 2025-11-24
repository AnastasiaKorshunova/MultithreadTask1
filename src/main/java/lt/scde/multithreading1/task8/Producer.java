package lt.scde.multithreading1.task8;
/**
 * Producer thread for Task 8 (Producer / Consumer).
 *
 * <p>The producer (P) toggles a shared boolean flag with a fixed delay {@code M}
 * between updates. Each time the flag is toggled, the producer notifies all
 * threads waiting on the shared monitor, so that the consumer can react to
 * the change.</p>
 *
 * <p>According to the task description:</p>
 * <ul>
 *     <li>Thread P performs switching from {@code true} to {@code false}
 *     and back with a delay of {@code M} milliseconds.</li>
 * </ul>
 */
public class Producer extends Thread {

    private final SharedState state;
    private final long m;
    private int switchCount = 0;

    public Producer(SharedState state, long m) {
        super("Producer");
        this.state = state;
        this.m = m;
    }

    @Override
    public void run() {
        try {
            while (true) {

                Thread.sleep(m);  // delay before switching

                synchronized (state.lock) {
                    if (state.getRemaining() <= 0) {
                        state.lock.notifyAll();
                        break;
                    }
                    switchCount++;
                    state.toggleFlag();
                    System.out.println("[P] switch #" + switchCount +
                            " -> " + state.getFlag());

                    state.lock.notifyAll();
                }
            }
        } catch (InterruptedException ignored) {}
    }
}
