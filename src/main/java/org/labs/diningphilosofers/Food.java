package org.labs.diningphilosofers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Food {

    @Getter
    private int totalCountOfFood;

    private final ReentrantLock lock = new ReentrantLock();

    public Food(int totalCountOfFood) {
        this.totalCountOfFood = totalCountOfFood;
    }

    public boolean decreaseCountOfFood() {

        lock.lock();

        try {

            if (totalCountOfFood <= 0) {
                return false;
            }

            totalCountOfFood--;

            log.debug("Количество оставшихся порций {}", totalCountOfFood);
            return true;

        } finally {
            lock.unlock();
        }

    }

    public boolean isFoodAvailable() {

        return totalCountOfFood > 0;

    }
}
