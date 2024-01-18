package ru.teamscore.java23.syncthreads;

import java.util.Random;

public class Producer implements Runnable {
    private SharedMonitoring monitor;

    public Producer(SharedMonitoring monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.printf("%s started production\n", t.getName());
        for (int i = 0;;i++) {
            Pause.pause(100, 300);
            double value = 10;
            System.out.printf("%s produce %.3f (cycle %d)\n", t.getName(), value, i);
            monitor.add(value);
        }
    }
}
