package org.labs.diningphilosofers;

import java.util.concurrent.locks.ReentrantLock;

public class Food {

    private int totalCountOfFood;

    private ReentrantLock lock = new ReentrantLock();

    public Food(int totalCountOfFood) {
        this.totalCountOfFood = totalCountOfFood;
    }

    public int getTotalCountOfFood() {
        return totalCountOfFood;
    }

    public boolean decreaseCountOfFood() {

        lock.lock();

        try {

            if (totalCountOfFood <= 0) {
                return false;
            }

            totalCountOfFood--;
            return true;

        } finally {
            lock.unlock();
        }

    }

    public boolean hasFood() {

        lock.lock();

        try {

            return totalCountOfFood != 0;

        } finally {
            lock.unlock();
        }

    }
}
