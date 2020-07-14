package com.valentinenikolaev.testTask.case2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        FizzBuzz fizzBuzz = new FizzBuzz(46);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Thread task1 = new Thread(fizzBuzz::fizz);
        Thread task2 = new Thread(fizzBuzz::buzz);
        Thread task3 = new Thread(fizzBuzz::fizzbuzz);
        Thread task4 = new Thread(fizzBuzz::number);

        executor.execute(task1);
        executor.execute(task2);
        executor.execute(task3);
        executor.execute(task4);

        task1.join();
        task2.join();
        task3.join();
        task4.join();

        executor.shutdown();


    }




}
