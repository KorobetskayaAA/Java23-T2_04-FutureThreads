package ru.teamscore.java23.threads;

import java.math.BigInteger;

/*class Calculation implements Runnable {
    @Override
    public void run() {
        //...
    }
}*/

public class ThreadsMain {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            Thread curThread = Thread.currentThread();
            String name = curThread.getName();
            System.out.printf("\nTHREAD %s started with Id = %d and Priority = %d\n",
                    name, curThread.getId(), curThread.getPriority());
            BigInteger sum = BigInteger.ZERO;
            for (int i = 0; i < 1000; i++) {
                sum = sum.add(BigInteger.valueOf(i * i));
                System.out.printf("%s%d ", name, i);
            }
            System.out.printf("\n%s: finished\n", name);
        };

        Thread threadA = new Thread(task);
        threadA.setName("A");
        threadA.setPriority(Thread.MIN_PRIORITY);

        Thread threadJ = new Thread(task);
        threadJ.setName("J");

        Thread threadD = new Thread(task);
        threadD.setName("D");
        threadD.setDaemon(true);
        threadD.setPriority(Thread.MAX_PRIORITY);

        threadA.start();
        threadJ.start();
        threadJ.join();
        threadD.start();

        //Thread.sleep(10);
        //task.run();
        System.out.printf("\n%s: finished\n", Thread.currentThread().getName());
    }
}
