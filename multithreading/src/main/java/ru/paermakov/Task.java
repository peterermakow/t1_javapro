package ru.paermakov;

import java.util.concurrent.TimeUnit;

public class Task implements Runnable {
    private int taskNum;

    public Task(int taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Task %d completed on thread: %s\n", taskNum, Thread.currentThread().getName());
    }
}
