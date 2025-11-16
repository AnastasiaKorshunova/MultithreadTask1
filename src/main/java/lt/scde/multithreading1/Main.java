package lt.scde.multithreading1;

import lt.scde.multithreading1.task1.CreateThread;
import lt.scde.multithreading1.task2.ThreadRunnable;
import lt.scde.multithreading1.task3.JoinDemo;
import lt.scde.multithreading1.task4.ThreadParameters;
import lt.scde.multithreading1.task5.InterruptedExceptionDemo;
import lt.scde.multithreading1.task6.PriorityTask;
import lt.scde.multithreading1.task7.YieldTask;
import lt.scde.multithreading1.task8.ProducerConsumerDemo;

public class Main {

    public static void main(String[] args) throws Exception {
        runTask("Task 1", CreateThread::runDemo);
        runTask("Task 2", ThreadRunnable::runDemo);
        runTask("Task 3", JoinDemo::runDemo);
        runTask("Task 4", ThreadParameters::runDemo);
        runTask("Task 5", InterruptedExceptionDemo::runDemo);
        runTask("Task 6", PriorityTask::runDemo);
        runTask("Task 7", YieldTask::runDemo);
        runTask("Task 8", ProducerConsumerDemo::runDemo);
    }

    private static void runTask(String name, RunnableWithException task) throws Exception {
        System.out.println("\n=== " + name + " ===");
        task.run();
    }

    @FunctionalInterface
    interface RunnableWithException {
        void run() throws Exception;
    }
}
