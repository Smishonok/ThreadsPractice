package com.valentinenikolaev.testTask.case1;

import java.util.concurrent.locks.ReentrantLock;

public class Foo {

    private volatile int  counter = 1;
    private ReentrantLock lock = new ReentrantLock();

    public void first() {
        boolean done = false;
        while (! done) {
            lock.lock();
            if (counter == 1) {
                System.out.print("first");
                counter = 2;
                done = true;
            }
            lock.unlock();
        }
    }

    public void second()  {
        boolean done = false;
        while (! done) {
            lock.lock();
            if (counter == 2) {
                System.out.print("second");
                counter = 3;
                done = true;
            }
            lock.unlock();
        }
    }

    public void third()  {
        boolean done = false;
        while (! done) {
            lock.lock();
            if (counter == 3) {
                System.out.print("third ");
                counter = 1;
                done = true;
            }
            lock.unlock();
        }
    }
}
