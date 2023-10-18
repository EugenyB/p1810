package main;

public class Main {

    private double totalResult;
    private int finished;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
//        Runtime runtime = Runtime.getRuntime();
//        System.out.println(runtime.availableProcessors());
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
        try {
            synchronized (this) {
                while (finished < nThreads) {
                    wait();
                }
            }
            long finishTime = System.currentTimeMillis();
            System.out.println("Result = " + totalResult);
            System.out.println(finishTime - startTime);
        } catch (InterruptedException e) {
            System.err.println("Error in thread");
        }

    }

    public synchronized void sendResult(double res) {
        totalResult += res;
        finished++;
        notify();
    }

    private void run1() {
        double a = 0;
        double b = Math.PI;
        int n = 1000_000_000;
        long startTime = System.currentTimeMillis();
        IntegralCalculator calculator = new IntegralCalculator(a, b, n, Math::sin);
        double v = calculator.calculate();
        long finishTime = System.currentTimeMillis();
        System.out.println("v = " + v);
        System.out.println(finishTime-startTime);
    }
}
