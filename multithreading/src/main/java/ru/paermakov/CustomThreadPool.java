package ru.paermakov;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author PAErmakov
 * CustomThreadPool работает следующим образом: пул с момента создания работает в отдельном потоке и не блокирует метод main.
 * Добавив в него задачи с помощью метода execute, пул выполнит их продолжит работу по аналогии с дефолтным newFixedThreadPool,
 * если же вызвать метод shutdown, пул отработает задачи из линкедлиста и завершит работу
 */

public class CustomThreadPool implements Runnable {
    private final int threadNumber;
    private final LinkedList<Runnable> taskList;
    private final AtomicBoolean run;
    private final Supplier<Thread> threadSupplier;
    private final Lock lock;
    private Thread poolThread;

    public CustomThreadPool(int threadNumber) {
        if (threadNumber < 1)
            throw new IllegalArgumentException();

        this.threadNumber = threadNumber;
        this.taskList = new LinkedList<>();
        this.run = new AtomicBoolean(true);
        this.lock = new ReentrantLock();

        this.threadSupplier = () -> new Thread(new Runnable() {
            @Override
            public void run() {
                while (run.get() || taskList.size() > 0) {
                    if (lock.tryLock()) {
                        if (!taskList.isEmpty()) {
                            taskList.poll().run();
                        }
                        lock.unlock();
                    }
                }
            }
        });

        this.poolThread = new Thread(this);
        poolThread.start();
    }

    public void execute(Runnable runnable) {
        if (run.get()) {
            taskList.add(runnable);
        } else {
            throw new IllegalStateException("New tasks can not be submitted after shutdown is called");
        }
    }

    public void shutdown() {
        run.set(false);
    }

    @Override
    public void run() {
        Stream.generate(threadSupplier).limit(threadNumber).forEach(Thread::start);
    }
}
