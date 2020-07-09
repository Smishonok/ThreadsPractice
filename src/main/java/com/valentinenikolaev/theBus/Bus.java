package com.valentinenikolaev.theBus;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bus implements Runnable {

    private          Thread        thread;
    private          Phaser        phaser;
    private          Semaphore     semaphore;
    private volatile boolean       isStopped;
    private          List<BusStop> busStops;

    public Bus() {
        this.phaser    = new Phaser();
        this.semaphore = new Semaphore(0);
        phaser.register();
        this.isStopped = false;
        this.thread    = new Thread(this, "TheBus");
    }

    public Bus registerBusToTheRout(List<BusStop> busStops) {
        System.out.println("Register bus to the rout.");
        this.busStops = busStops;
        for (BusStop busStop : busStops) {
            busStop.registerBus(this);
        }
        return this;
    }

    public void startTheRout() {
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("The bus has started the rout.");
        phaser.arriveAndAwaitAdvance();

        Iterator<BusStop> busStopIterator = busStops.iterator();
        while (busStopIterator.hasNext()) {
            BusStop busStop = busStopIterator.next();
            System.out.println(
                    "The bus has arrived at the bus stop №" + busStop.getBusStopNumber());

            semaphore.release(phaser.getRegisteredParties()-1);
            phaser.arriveAndAwaitAdvance();
            System.out.println("The bus lived the bus stop");
        }
        System.out.println("The bus ended the rout.");
        phaser.arriveAndDeregister();
    }


    public boolean isStopped() {
        return isStopped;
    }

    public Thread getThread() {
        return thread;
    }

    public Phaser getPhaser() {
        return phaser;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

}
