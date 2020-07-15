package com.valentinenikolaev.testTask.case1;

public class Speaker extends Thread {

    public Speaker(Runnable target) {
        super(target);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            super.run();
        }
    }
}
