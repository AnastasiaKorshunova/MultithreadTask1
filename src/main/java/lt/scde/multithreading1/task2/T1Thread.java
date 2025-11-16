package lt.scde.multithreading1.task2;

/**
 * Custom thread class based on Thread.
 * Used in Task 2 (Thread vs Runnable).
 */
public class T1Thread extends Thread {

    public T1Thread(Runnable target) {
        super(target);
    }

    /**
    // If uncomment this method, the code below will be executed instead of the Runnable's run() method.
     */
//    @Override
//    public void run() {
//        System.out.println("T1Thread.run() executed.");
//    }
}
