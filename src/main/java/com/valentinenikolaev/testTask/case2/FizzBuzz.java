package com.valentinenikolaev.testTask.case2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FizzBuzz {

    private volatile int  currentN;
    private          int  n;
    private          Lock lock;


    public FizzBuzz(int n) {
        this.currentN = 1;
        this.n        = n;
        this.lock     = new ReentrantLock();
    }

    public void fizz() {
        while (currentN < n + 1) {
            lock.lock();
            if (currentN < n + 1) {
                if (currentN % 3 == 0 && currentN % 5 != 0) {
                    System.out.print("fizz, ");
                    currentN++;
                }
            }
            lock.unlock();
        }
    }

    public void buzz() {
        while (currentN < n + 1) {
            lock.lock();
            if (currentN < n + 1) {
                if (currentN % 5 == 0 && currentN % 3 != 0) {
                    System.out.print("buzz, ");
                    currentN++;
                }
            }
            lock.unlock();
        }
    }

    public void fizzbuzz() {
        while (currentN < n + 1) {
            lock.lock();
            if (currentN < n + 1) {
                if (currentN % 5 == 0 && currentN % 3 == 0) {
                    System.out.print("fizzbuzz, ");
                    currentN++;
                }
            }
            lock.unlock();
        }
    }

    public void number() {
        while (currentN < n + 1) {
            lock.lock();
            if (currentN < n + 1) {
                if (currentN % 5 != 0 && currentN % 3 != 0) {
                    System.out.print(currentN + ", ");
                    currentN++;
                }
            }
            lock.unlock();
        }
    }
}
