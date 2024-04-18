package ru.paermakov;

public class Main {
    public static void main(String[] args) {
        CustomThreadPool customThreadPool = new CustomThreadPool(3);
        customThreadPool.execute(new Task(1));
        customThreadPool.execute(new Task(2));
        customThreadPool.execute(new Task(3));
        customThreadPool.execute(new Task(4));
        customThreadPool.execute(new Task(5));
        customThreadPool.shutdown();
    }
}