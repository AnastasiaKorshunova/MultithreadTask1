package lt.scde.multithreading1.task4;



import java.util.Random;

/**
 * Thread parameters demo.
 *
 * Creates several threads (4 <= N <= 6), all executing the same method,
 * but each printing its own sequence of text lines passed as a parameter.
 * <p>
 * The number of threads and the length of each sequence are generated randomly
 * within the specified bounds.
 * <p>
 * <b>Observation:</b><br>
 * The output demonstrates that the execution order of threads and the order in
 * which they print their messages do not correspond to the order in which the
 * threads were created. The scheduling of threads is non-deterministic, and
 * context switching may occur at any time between the printed lines of different
 * threads. This behavior illustrates how the JVM and the underlying operating
 * system thread scheduler interleave thread execution in an unpredictable way.
 * <p>
 * However, because the demo uses the {@link Thread#join()} method, the main
 * thread waits for all worker threads to finish. As a result, despite the mixed
 * and interleaved output, each thread's entire sequence is eventually printed
 * before the program completes.
 */

public class ThreadParameters {

    /** Minimum number of threads according to the task (inclusive). */
    private static final int MIN_THREADS = 4;

    /** Maximum number of threads according to the task (inclusive). */
    private static final int MAX_THREADS = 6;

    /** Minimum number of elements in a sequence. */
    private static final int MIN_SEQUENCE_LENGTH = 2;

    /** Maximum number of elements in a sequence. */
    private static final int MAX_SEQUENCE_LENGTH = 6;

    /**
     * Runs the demo:
     * <ol>
     *     <li>Randomly chooses the number of threads N (4 <= N <= 6).</li>
     *     <li>Generates N different sequences of strings.</li>
     *     <li>Prints all generated sequences.</li>
     *     <li>Creates one thread per sequence.</li>
     *     <li>Starts all threads.</li>
     *     <li>Waits for all threads to finish using {@code join()}.</li>
     * </ol>
     *
     * @throws InterruptedException if the current thread is interrupted
     *                              while waiting for threads to finish
     */
    public static void runDemo() throws InterruptedException {
        Random random = new Random();

        // Random N such that MIN_THREADS <= N <= MAX_THREADS
        int threadCount = MIN_THREADS + random.nextInt(MAX_THREADS - MIN_THREADS + 1);

        // Generate random sequences
        String[][] sequences = generateSequences(threadCount, random);

        // Print generated sequences for clarity
        printGeneratedSequences(sequences);

        Thread[] threads = new Thread[threadCount];

        // Create and start one thread per sequence
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            threads[i] = new Thread(() -> printSequence("Thread-" + index, sequences[index]));
            threads[i].setName("SequenceThread-" + index);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }
    }

    /**
     * Generates an array of string sequences.
     * Each sequence has a random length between MIN_SEQUENCE_LENGTH and MAX_SEQUENCE_LENGTH.
     * Elements are generated in the form "T&lt;threadIndex&gt;-&lt;elementIndex&gt;".
     *
     * @param threadCount number of sequences (and threads)
     * @param random      random number generator
     * @return a 2D array of string sequences
     */
    private static String[][] generateSequences(int threadCount, Random random) {

        String[][] sequences = new String[threadCount][];

        for (int i = 0; i < threadCount; i++) {
            int length = MIN_SEQUENCE_LENGTH + random.nextInt(MAX_SEQUENCE_LENGTH - MIN_SEQUENCE_LENGTH + 1);
            String[] seq = new String[length];

            for (int j = 0; j < length; j++) {
                seq[j] = "T" + i + "-" + (j + 1);
            }

            sequences[i] = seq;
        }

        return sequences;
    }

    /**
     * Prints all generated sequences before starting the threads.
     *
     * @param sequences array of sequences to print
     */
    private static void printGeneratedSequences(String[][] sequences) {
        System.out.println("Generated sequences (" + sequences.length + " threads):");

        for (int i = 0; i < sequences.length; i++) {
            System.out.print("Sequence " + i + ": ");
            for (String s : sequences[i]) {
                System.out.print(s + " ");
            }
            System.out.println();
        }

        System.out.println("---- Starting threads ----");
    }

    /**
     * Prints all strings from the given sequence with a thread label.
     *
     * @param threadName name identifying the thread
     * @param sequence   array of strings to print
     */
    private static void printSequence(String threadName, String[] sequence) {
        for (String s : sequence) {
            System.out.println("[" + threadName + "] " + s);
        }
    }
}


