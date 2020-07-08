package com.valentinenikolaev.theBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BusStop {

    private int             busStopNumber = 0;
    private int             maxBusStopNumber;
    private Phaser          phaser;
    private List<Passenger> passengersList;

    public BusStop(int busStopNumber, int maxBusStopsNumber) {
        this.busStopNumber    = busStopNumber;
        this.maxBusStopNumber = maxBusStopsNumber;
        this.passengersList   = new ArrayList<>();
    }

    public void registerBus(Bus bus) {
        if (busStopNumber != maxBusStopNumber) {
            passengersList.add(new Passenger(busStopNumber, busStopNumber + 1, bus));
            passengersList.add(new Passenger(busStopNumber, maxBusStopNumber, bus));
        }
    }

    public int getBusStopNumber() {
        return busStopNumber;
    }
}
