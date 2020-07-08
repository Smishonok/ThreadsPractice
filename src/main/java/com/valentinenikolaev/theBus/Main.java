package com.valentinenikolaev.theBus;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<BusStop> busStops          = new ArrayList<>();
        int           muxBusStopsNumber = 5;

        for (int i = 0; i < muxBusStopsNumber; i++) {
            busStops.add(new BusStop(i + 1, muxBusStopsNumber));
        }

        Bus bus = new Bus();
        bus.registerBusToTheRout(busStops).startTheRout();
        bus.getThread().join();

    }


}
