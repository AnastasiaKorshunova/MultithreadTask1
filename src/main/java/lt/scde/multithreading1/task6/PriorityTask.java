package lt.scde.multithreading1.task6;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Enhanced Priority Task — heavy-load demonstration with competing threads.
 *
 * This version creates ~equal groups of LOW, NORMAL and HIGH priority threads.
 * All threads run the same busy loop for a fixed duration and report how many
 * operations they completed, so we can observe the effect of priorities.
 */
public class PriorityTask {

    private static final int LOW_THREADS  = 33;
    private static final int NORM_THREADS = 33;
    private static final int HIGH_THREADS = 33;
    private static final long TEST_DURATION_MS = 2000;

    /**
     * Runs the priority demonstration with competing threads.
     *
     * @throws InterruptedException if the main thread is interrupted while waiting
     */
    public static void runDemo() throws InterruptedException {

        List<Thread> threads = new ArrayList<>();
        Map<String, Long> results = Collections.synchronizedMap(
                new LinkedHashMap<>());
        Map<String, Long> groupTotals = new LinkedHashMap<>();
        Runnable worker = () -> {
            long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(TEST_DURATION_MS);
            long ops = 0;

            while (System.nanoTime() < end) {
                ops++;
            }
            results.put(Thread.currentThread().getName(), ops);
        };

        for (int i = 1; i <= LOW_THREADS; i++) {
            Thread t = new Thread(worker, "LOW-" + i);
            t.setPriority(Thread.MIN_PRIORITY);
            threads.add(t);
        }

        for (int i = 1; i <= NORM_THREADS; i++) {
            Thread t = new Thread(worker, "NORM-" + i);
            t.setPriority(Thread.NORM_PRIORITY);
            threads.add(t);
        }

        for (int i = 1; i <= HIGH_THREADS; i++) {
            Thread t = new Thread(worker, "HIGH-" + i);
            t.setPriority(Thread.MAX_PRIORITY);
            threads.add(t);
        }

        System.out.println("Starting " + threads.size() + " heavy-load threads...");

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\n=== PRIORITY SUMMARY ===");

        printGroup("HIGH priority threads", results, "HIGH", groupTotals);
        printGroup("NORM priority threads", results, "NORM", groupTotals);
        printGroup("LOW priority threads",  results, "LOW",  groupTotals);

        printRanking(groupTotals);

        System.out.println("\nPriorityTask complete.\n");
    }
    /**
     * Prints detailed statistics for a specific priority group and records
     * the group's total number of operations.
     *
     * <p>This method selects all entries from the {@code results} map
     * whose thread names start with the given {@code prefix}
     * (e.g. "HIGH", "NORM", "LOW"). It then:</p>
     *
     * <p>The grouping is based purely on name prefixes assigned when threads
     * are created (e.g. "HIGH-1", "LOW-12").</p>
     *
     * @param title       a human-readable group title printed above results
     * @param results     a map containing thread names and their operation counts
     * @param prefix      group identifier (e.g. "HIGH", "NORM", "LOW")
     * @param groupTotals a map where the computed total operations for this group
     *                    will be stored for later ranking
     */
    private static void printGroup(String title,
                                   Map<String, Long> results,
                                   String prefix,
                                   Map<String, Long> groupTotals) {

        System.out.println("\n" + title + ":");

        List<Map.Entry<String, Long>> group = results.entrySet().stream()
                .filter(e -> e.getKey().startsWith(prefix))
                .sorted(Map.Entry.comparingByKey())
                .toList();

        long sum = 0;
        int count = group.size();
        for (Map.Entry<String, Long> e : group) {
            System.out.println(e.getKey() + " ops=" + e.getValue());
            sum += e.getValue();
        }
        double avg = count > 0 ? (double) sum / count : 0.0;
        System.out.println("---- " + prefix + " total ops = " + sum);
        System.out.println("---- " + prefix + " avg   ops = " + avg);
        groupTotals.put(prefix, sum);
    }

    /**
     * Prints the overall ranking of priority groups based on their total number
     * of operations.
     *
     * <p>The groupTotals map is expected to contain entries like:
     * "HIGH" -> totalOps, "NORM" -> totalOps, "LOW" -> totalOps.
     * The method sorts these entries in descending order of total operations
     * and prints them as a simple ranking (1st, 2nd, 3rd).</p>
     *
     * @param groupTotals a map from group prefix (e.g. "HIGH", "NORM", "LOW")
     *                    to the total number of operations performed by that group
     */
    private static void printRanking(Map<String, Long> groupTotals) {
        System.out.println("\n=== Overall ranking by total ops ===");

        List<Map.Entry<String, Long>> list = new ArrayList<>(groupTotals.entrySet());
        list.sort((a, b) -> Long.compare(b.getValue(), a.getValue()));

        int rank = 1;
        for (Map.Entry<String, Long> e : list) {
            System.out.println(rank + ") " + e.getKey() +
                    " total ops = " + formatOps(e.getValue()));
            rank++;
        }
    }
    /**
     * Formats a long value representing the number of operations in a
     * human-readable form.
     *
     * <p>The value is formatted with thousands separators and commas are
     * replaced by underscores, for example:
     * 19860858 -> "19_860_858".</p>
     *
     * @param value the numeric operations count
     * @return a formatted string with underscores as thousands separators
     */
    private static String formatOps(long value) {
        return String.format("%,d", value).replace(',', '_');
    }
}

/**
 * Thread Priority Demonstration (Task 6)
 *
 * === Overall ranking by total ops ===
 * 1) HIGH total ops = 19 860 858
 * 2) LOW  total ops = 16 838 218
 * 3) NORM total ops = 16 469 769
 *
 * === Overall ranking by total ops ===
 * 1) HIGH total ops = 20 407 636
 * 2) NORM total ops = 18 512 660
 * 3) LOW  total ops = 18 443 909
 *
 * === Overall ranking by total ops ===
 * 1) HIGH total ops = 18 605 972
 * 2) LOW  total ops = 17 909 688
 * 3) NORM total ops = 16 543 056
 *
 * This experiment demonstrates how Java thread priorities behave under
 * different CPU load conditions.
 *
 * When CPU load is LOW (e.g., only 2–3 threads running as it was in the first version), the operating system
 * usually gives enough CPU time to every runnable thread. As a result, threads
 * with MIN, NORM and MAX priorities may complete almost the same amount of work.
 * Many modern OS schedulers largely ignore user-level priority differences
 * unless the system is under heavy contention.
 *
 * When CPU load is HIGH (e.g., 100 competing CPU-bound threads, as in this task),
 * the scheduler must constantly decide which thread runs next. Under such
 * pressure, MAX-priority threads tend to receive more CPU time and therefore
 * perform noticeably more operations. NORM-priority threads fall in the middle,
 * and LOW-priority threads perform the smallest number of operations.
 *
 * Summary:
 *  - Priority in Java is only a scheduling hint, not a strict rule.
 *  - Under low load, priority differences may not be visible at all.
 *  - Under high load, MAX > NORM > LOW becomes clear in total operations.
 *
 * This task uses ~100 CPU-intensive threads to reveal these effects and to make
 * priority-based scheduling differences observable and measurable.
 */
