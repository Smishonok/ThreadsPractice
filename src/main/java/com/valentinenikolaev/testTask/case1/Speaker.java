package com.valentinenikolaev.testTask.case1;

public class Speaker extends Thread {

    private Runnable runnable;

    public Speaker(Runnable target) {
        super(target);
        this.runnable = target;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            super.run();
        }
    }
}
