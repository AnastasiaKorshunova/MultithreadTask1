package org.example;

// Task 4
class Task4ThreadParameters {
    public static void runDemo() throws InterruptedException {
        String[][] sequences = {
                {"A1", "A2", "A3"},
                {"B1", "B2", "B3", "B4"},
                {"C1", "C2"},
                {"D1", "D2", "D3", "D4", "D5"}
        };
        Thread[] threads = new Thread[sequences.length];
        for (int i = 0; i < sequences.length; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> printSequence("T" + idx, sequences[idx]));
            threads[i].start();
        }
        for (Thread t : threads) t.join();
    }

    private static void printSequence(String name, String[] seq) {
        for (String s : seq) System.out.println("[" + name + "] " + s);
    }
}