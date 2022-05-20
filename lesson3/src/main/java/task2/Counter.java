package task2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter{

    private int count = 0;
    private Lock lock = new ReentrantLock();

    public void inc(){
        lock.lock();
        count++;
        lock.unlock();
    }

    public void dec(){
        lock.lock();
        count--;
        lock.unlock();
    }

    public int getCount() {
        return count;
    }
}
