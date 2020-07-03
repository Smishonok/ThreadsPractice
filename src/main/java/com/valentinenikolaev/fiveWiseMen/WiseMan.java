package com.valentinenikolaev.fiveWiseMen;

import java.util.concurrent.Semaphore;

public class WiseMan implements Runnable {

    private          int       number;
    private          int       eatingCount;
    private          boolean   mayEat;
    private          Fork      leftFork;
    private          Fork      rightFork;
    private          Semaphore semaphore;
    private          Table     table;
    private volatile boolean   isAtTheTable;
    private          Thread    thread;

    private final int MAX_WISE_MAN_NUMBER = 5;

    public WiseMan(int number, Table table) {
        this.number       = number;
        this.table        = table;
        this.eatingCount  = 0;
        this.isAtTheTable = true;
        setForks();
        this.semaphore = table.getSemaphore();
        this.thread    = new Thread(this,"Wise man №" + number);
        this.thread.start();
    }

    private void setForks() {
        if (this.number == 1) {
            this.rightFork = this.table.getFork(MAX_WISE_MAN_NUMBER);
        } else {
            this.rightFork = this.table.getFork(number - 1);
        }
        this.leftFork = this.table.getFork(number);
    }

    @Override
    public void run() {
        System.out.println("Wise man №" + this.number + " came and seat to the table.");
        while (isAtTheTable) {
            if (this.eatingCount == this.table.getEatingRound()&&this.semaphore.availablePermits()>0) {
                this.mayEat = true;
                tryToEat();
            } else {
                thinking();
            }
        }
        System.out.println(
                "Wise man №" + this.number + " went out. He was eat " + this.eatingCount +
                        " times.");

    }

    private void tryToEat() {
        while (mayEat) {
            tryToAcquireSemaphore();
            if (! leftFork.isInUse() && ! rightFork.isInUse()) {
                getForks();
                eat();
                putForks();
                this.mayEat = false;
            }
            this.semaphore.release();
        }
    }

    private void getForks() {
        leftFork.isInUse(true);
        rightFork.isInUse(true);
    }

    private void putForks() {
        leftFork.isInUse(false);
        rightFork.isInUse(false);
    }

    private void eat() {
        System.out.println("Wise man №" + this.number + " is eating.");
        this.table.incrementEatingCounter();
        this.eatingCount++;

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted: " + e.getMessage());
        }
    }

    private void thinking() {
        System.out.println("Wise man №" + this.number + " is thinking.");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted: " + e.getMessage());
        }
    }

    private void tryToAcquireSemaphore() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted: " + e.getMessage());
        }
    }

    public void goOut() {
        this.isAtTheTable = false;
    }

    public Thread getThread() {
        return thread;
    }
}
