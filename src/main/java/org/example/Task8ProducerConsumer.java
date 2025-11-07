package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class Task8ProducerConsumer {
    public static void runDemo() throws InterruptedException {
        final long M = 200;    // toggle period (ms)
        final long K = 2000;   // countdown start (ms)
        final long step = Math.max(1, M / 10);

        AtomicBoolean state = new AtomicBoolean(false);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        Runnable toggler = () -> {
            boolean newState = !state.get();
            state.set(newState);
            System.out.println("[P] state -> " + newState);
        };

        final long[] remaining = { K };
        final ScheduledFuture<?>[] consumerHandle = new ScheduledFuture<?>[1];

        Runnable consumer = () -> {
            if (remaining[0] <= 0) return;
            if (!state.get()) return; // paused while false
            System.out.println("[C] left=" + remaining[0] + " ms");
            remaining[0] -= step;
            if (remaining[0] <= 0) {
                System.out.println("[C] done (remaining=0)");
                consumerHandle[0].cancel(false);
                scheduler.shutdownNow();
            }
        };

        ScheduledFuture<?> toggleHandle =
                scheduler.scheduleAtFixedRate(toggler, M, M, TimeUnit.MILLISECONDS);
        consumerHandle[0] =
                scheduler.scheduleAtFixedRate(consumer, 0, step, TimeUnit.MILLISECONDS);

        scheduler.awaitTermination(10, TimeUnit.SECONDS);
        toggleHandle.cancel(true);
    }
}
