package main2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main2 {
    public static void main(String[] args) {
        Main2 main = new Main2();
        main.run();
    }

    private void run() {
        double a = 0;
        double b = Math.PI;
        int n = 1000_000_000;
        long startTime = System.currentTimeMillis();
        int nThreads = 100;
        double delta = (b-a)/nThreads;

        ExecutorService es = Executors.newWorkStealingPool(50);
        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < nThreads; i++) {
            double ai = a + i * delta;
            double bi = ai + delta;
            CallableCalculator calculator = new CallableCalculator(ai, bi, n / nThreads, Math::sin);
            Future<Double> future = es.submit(calculator);
            futures.add(future);
        }
        es.shutdown();

        double total = 0;
        try {
            for (Future<Double> future : futures) {
                total += future.get();
            }
            long finishTime = System.currentTimeMillis();
            System.out.println("result = " + total);
            System.out.println(finishTime - startTime);
        } catch (Exception ignored) {
        }
    }
}
