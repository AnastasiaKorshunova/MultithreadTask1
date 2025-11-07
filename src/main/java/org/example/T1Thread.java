package org.example;

// Task 2 — Thread vs Runnable
public class T1Thread extends Thread {
    public T1Thread(Runnable target) {
        super(target);
    }

//    @Override
//    public void run() {
//        System.out.println("T1Thread.run() executed.");
//    }
}

class T1Runnable implements Runnable {
    @Override
    public void run() {
        System.out.println("T1Runnable.run() executed.");
    }
}

class Task2ThreadRunnable {
    public static void runDemo() throws InterruptedException {
        Thread thread = new T1Thread(new T1Runnable());
        thread.start();
        thread.join();
    }
}
