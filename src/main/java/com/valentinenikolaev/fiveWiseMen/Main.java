package com.valentinenikolaev.fiveWiseMen;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Table table = new Table();


        final List<WiseMan> wiseMEN = new ArrayList<WiseMan>();
        for (int i = 0; i < 5; i++) {
            wiseMEN.add(new WiseMan(i + 1, table));
        }

        Thread stoppingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (WiseMan wiseMan : wiseMEN) {
                    wiseMan.goOut();
                }
            }
        });
        stoppingThread.start();

        for (WiseMan wiseMan : wiseMEN) {
            wiseMan.getThread().join();
        }


    }


}
