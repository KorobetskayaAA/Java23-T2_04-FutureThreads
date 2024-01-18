package ru.teamscore.java23.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureCalculator squareCalculator = new FutureCalculator();

        Future<Integer> future1 = squareCalculator.calculate(100);
        Future<Integer> future2 = squareCalculator.calculate(1000);

        while (!(future1.isDone() && future2.isDone())) {
            System.out.println(
                    String.format(
                            "future1 is %s and future2 is %s",
                            future1.isDone() ? "done" : "not done",
                            future2.isDone() ? "done" : "not done"
                    )
            );
            Thread.sleep(300);
        }

        Integer result1 = future1.get();
        Integer result2 = future2.get();

        System.out.println(result1 + " and " + result2);
    }
}
