package main3;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main3 {
    private double totalResult;
    private int finished;

    public static void main(String[] args) {
        Main3 main = new Main3();
        main.run();
    }

    private void run() {
        double a = 0;
        double b = Math.PI;
        int n = 1000_000_000;
        int nThreads = 100;
        totalResult = 0;
        finished = 0;
        double delta = (b - a)/nThreads;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            double ai = a + i * delta;
            double bi = ai + delta;
            new Thread(new RunnableCalculator(ai, bi, n/nThreads, Math::sin, this)).start();
        }
        lock = new ReentrantLock();
        condition = lock.newCondition();
        lock.lock();
        try {
            while (finished < nThreads) {
                condition.await();
            }
        } catch (InterruptedException ignore) {
        } finally {
            lock.unlock();
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("Result = " + totalResult);
        System.out.println(finishTime-startTime);
    }

    public void sendResult(double res) {
        try {
            lock.lock();
            totalResult += res;
            finished++;
            condition.signal();
        } finally {
            lock.unlock();
        }

    }

    Lock lock;
    Condition condition;
}
