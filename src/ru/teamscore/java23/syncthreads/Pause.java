package ru.teamscore.java23.syncthreads;

import java.util.Random;

public class Pause {
    private static Random rnd = new Random();
    private Pause() {}
    public static void pause(int minMs, int maxMs) {
        try {
            Thread.sleep(minMs < maxMs ? rnd.nextInt(minMs, maxMs + 1) : minMs);
        } catch (InterruptedException e) {
            System.err.printf("%s interrupted\n", Thread.currentThread().getName());
        }
    }
}
