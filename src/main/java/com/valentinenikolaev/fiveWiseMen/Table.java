package com.valentinenikolaev.fiveWiseMen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Table {

    private          List<Fork> forkList;
    private volatile int        eatingCounter;
    private volatile int        eatingRound;
    private          Semaphore  semaphore;
    private final    int        MAX_SEMAPHORE_PERMITS = 2;


    public Table() {
        this.forkList      = new ArrayList<>();
        this.eatingCounter = 0;
        this.eatingRound   = 0;
        this.semaphore     = new Semaphore(MAX_SEMAPHORE_PERMITS);
        initiateForkList();
    }

    private void initiateForkList() {
        for (int i = 0; i < 5; i++) {
            forkList.add(new Fork());
        }
    }

    public synchronized void incrementEatingCounter() {
        if (this.eatingCounter < 4) {
            this.eatingCounter++;
        } else {
            this.eatingCounter = 0;
            this.eatingRound++;
        }
    }

    public Fork getFork(int number) {
        return forkList.get(number - 1);
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public int getEatingRound() {
        return eatingRound;
    }
}
