package com.valentinenikolaev.fiveWiseMen;

public class Fork {

    private volatile boolean isInUse;

    public Fork() {
        this.isInUse    = false;
    }

    public boolean isInUse() {
        return isInUse;
    }

    public void isInUse(boolean status) {
        this.isInUse = status;
    }
}
