package com.valentinenikolaev.theBus;

import java.util.concurrent.Phaser;

public class Passenger implements Runnable {

    private Thread thread;
    private String name;
    private Bus    bus;
    private Phaser phaser;
    private int    departure;
    private int    destination;

    private static int lastPassengerNumber = 1;

    public Passenger(int departureBusStop, int destinationBusStop, Bus bus) {
        this.name        =
                "Passenger №" + (lastPassengerNumber++) + " {" + departureBusStop + "->" +
                        destinationBusStop + "}";
        this.departure   = departureBusStop;
        this.destination = destinationBusStop;
        this.bus         = bus;
        this.phaser      = bus.getPhaser();
        this.phaser.register();

        this.thread = new Thread(this, name);
        this.thread.start();
    }

    @Override
    public void run() {
        System.out.println(name + " comes to the bus stop №" + this.departure + ".");
        phaser.arriveAndAwaitAdvance();

        while (departure > phaser.getPhase()) {
            try {
                bus.getSemaphore().acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " waiting for the bus.");
            phaser.arriveAndAwaitAdvance();
        }

        try {
            bus.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " comes into the bus.");
        phaser.arriveAndAwaitAdvance();

        while (destination > phaser.getPhase()) {
            try {
                bus.getSemaphore().acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " waiting for arriving at the destination bus stop.");
            phaser.arriveAndAwaitAdvance();
        }

        try {
            bus.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.name + " arrived to the destination and went out the bus.");
        phaser.arriveAndDeregister();
    }
}

