package lt.scde.multithreading1.task2;

/**
 * Simple Runnable implementation used as a task for T1Thread.
 */
public class T1Runnable implements Runnable {

    @Override
    public void run() {
        System.out.println("T1Runnable.run() executed.");
    }
}

