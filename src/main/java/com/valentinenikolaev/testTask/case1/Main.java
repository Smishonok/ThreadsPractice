package com.valentinenikolaev.testTask.case1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Foo testClass = new Foo();

        Thread task1 = new Speaker(testClass::first);
        Thread task2 = new Speaker(testClass::second);
        Thread task3 = new Speaker(testClass::third);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(task1);
        executor.execute(task2);
        executor.execute(task3);

        task1.join();
        task2.join();
        task3.join();

        executor.shutdown();
    }
}
