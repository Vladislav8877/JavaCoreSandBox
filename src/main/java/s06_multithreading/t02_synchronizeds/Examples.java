package s06_multithreading.t02_synchronizeds;

import java.util.concurrent.locks.ReentrantLock;

public class Examples {
    private static ReentrantLock lock = new ReentrantLock();
    private volatile static int value = 0;
    private static void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException{

        Runnable runnable = () -> {
          for (int i = 0; i < 10_000; i++) {
              increment();
          }
        };
        Thread threadOne = new Thread(runnable);
        Thread threadTwo = new Thread(runnable);

        threadOne.start();
        threadTwo.start();

        threadOne.join();
        threadTwo.join();

        System.out.println("Value = "+value);
    }
}

