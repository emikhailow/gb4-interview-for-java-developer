package task2;

public class Task2App{

    public static final int MAX_COUNT = 1000000;

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < MAX_COUNT; i++) {
                counter.inc();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < MAX_COUNT; i++) {
                counter.dec();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter.getCount());

    }







}
