package ru.teamscore.java23.syncthreads;

import java.lang.invoke.VolatileCallSite;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SharedMonitoring implements Runnable {
    Object lock = new Object();
    AtomicInteger countIn = new AtomicInteger(0);
    AtomicInteger countOut = new AtomicInteger(0);
    AtomicReference<BigDecimal> sum = new AtomicReference<>(BigDecimal.ZERO);

    public void add(double value) {
        countIn.incrementAndGet();
        sum.set(sum.get().add(BigDecimal.valueOf(value)));
    }

    public boolean remove(double value) {
        BigDecimal val = BigDecimal.valueOf(value);
        if (sum.get().compareTo(val) < 0) {
            return false;
        }
        countOut.incrementAndGet();
        sum.set(sum.get().subtract(val));
        return true;
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        while (true) {
            System.out.printf("\u001B[33m%s +%d, -%d, sum=%s\u001B[0m\n", t.getName(), countIn.get(), countOut.get(), sum.get());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.printf("%s interrupted\n", t.getName());
            }
        }
    }
}
