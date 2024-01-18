package ru.teamscore.java23.syncthreads;

import java.util.Random;

public class Consumer implements Runnable {
    private SharedMonitoring monitor;

    public Consumer(SharedMonitoring monitor) {
        this.monitor = monitor;
    }


    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.printf("%s started consumption\n", t.getName());
        for (int i = 0;;i++) {
            double value = 3;
            Pause.pause(200, 300);
            int attempts = 1;
            while (!monitor.remove(value)) {
                attempts++;
            };
            System.out.printf("%s consumed %.3f success after %d attempts (cycle %d)\n", t.getName(), value, attempts, i);
        }
    }
}
