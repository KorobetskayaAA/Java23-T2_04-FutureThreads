package ru.teamscore.java23.syncthreads;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SharedMonitoring implements Runnable {
    Object lock = new Object();
    int countIn = 0;
    int countOut = 0;
    BigDecimal sum = BigDecimal.ZERO;

    public synchronized void add(double value) {
        countIn++;
        sum = sum.add(BigDecimal.valueOf(value));
    }

    public boolean remove(double value) {
        BigDecimal val = BigDecimal.valueOf(value);
        synchronized (lock) {
            if (sum.compareTo(val) < 0) {
                return false;
            }
            countOut++;
            sum = sum.subtract(val);
        }
        return true;
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        while (true) {
            System.out.printf("\u001B[33m%s +%d, -%d, sum=%s\u001B[0m\n", t.getName(), countIn, countOut, sum);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.printf("%s interrupted\n", t.getName());
            }
        }
    }
}
