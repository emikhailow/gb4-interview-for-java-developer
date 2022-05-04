package task1;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class Task1App {

    public static int COUNT = 10;
    public static String currentWord = "";

    public static final String lockingStrategy = "VIA_LOCK";

    public static final Object lockerObject = new Object();

    public static final ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    public static void main(String[] args) {

        Consumer<String> printPingPong = new Consumer<String>() {
            @Override
            public void accept(String s) {
                for (int i = 0; i < COUNT; i++) {
                    switch (lockingStrategy) {
                        case ("VIA_SYNCHRONIZED"):
                            printWordViaSynchronized(s);
                            break;

                        case ("VIA_LOCK"):
                            printWordViaLock(s);
                            break;
                    }
                }
            }

        };

        Thread threadPing = new Thread(() -> printPingPong.accept("ping"));
        Thread threadPong = new Thread(() -> printPingPong.accept("pong"));
        threadPing.start();
        threadPong.start();

    }

    public static void printWordViaSynchronized(String word) {
        synchronized (lockerObject) {
            while (Objects.equals(word, currentWord)) {
                try {
                    lockerObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(word);
            currentWord = word;
            lockerObject.notifyAll();
        }
    }

    public static void printWordViaLock(String word) {

        lock.lock();
        try {
            condition.await(1, TimeUnit.SECONDS);
            System.out.println(word);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


}

