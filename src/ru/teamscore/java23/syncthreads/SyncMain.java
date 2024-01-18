package ru.teamscore.java23.syncthreads;

public class SyncMain {
    public static void main(String[] args) {
        SharedMonitoring sm = new SharedMonitoring();
        Producer p = new Producer(sm);
        Consumer[] consumers = new Consumer[5];

        Thread threadSm = new Thread(sm);
        threadSm.start();
        Thread threadP = new Thread(p);
        threadP.start();
        Thread[] threadConsumers = new Thread[consumers.length];
        for (int i = 0; i < threadConsumers.length; i++) {
            consumers[i] = new Consumer(sm);
            threadConsumers[i] = new Thread(consumers[i]);
            threadConsumers[i].start();
        }
    }
}
