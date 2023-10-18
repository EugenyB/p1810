package main;

import java.util.function.DoubleUnaryOperator;

public class RunnableCalculator implements Runnable {

    private final IntegralCalculator calculator;
    private final Main main;

    public RunnableCalculator(double a, double b, int n, DoubleUnaryOperator f, Main main) {
        calculator = new IntegralCalculator(a, b, n, f);
        this.main = main;
    }

    @Override
    public void run() {
        main.sendResult(calculator.calculate());
    }
}
