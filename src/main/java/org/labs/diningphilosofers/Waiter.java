package org.labs.diningphilosofers;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс официант
 */
public class Waiter {


    /**
     * Параметры кол-во доступных порций
     * Предоставляет потокобезопасный метод проверки и уменьшения порций
     */
    private Food food;

    /**
     * Предоставляет из себя лок текущего официанта
     */
    private ReentrantLock lock = new ReentrantLock();

    private int[] countOfServings;


    public Waiter(Food food, int[] countOfServings) {
        this.food = food;
        this.countOfServings = countOfServings;
    }


    public boolean givePortion() {

        return food.decreaseCountOfFood();
    }

    public boolean tryLock() {
        return lock.tryLock();
    }

    public void unlock() {
        lock.unlock();
    }


    public boolean canEating(int idProgrammer) {

        int minMeals = Integer.MAX_VALUE;
        for (int i = 1; i < countOfServings.length; i++) {
            if (countOfServings[i] < minMeals) {
                minMeals = countOfServings[i];
            }
        }

        if (countOfServings[idProgrammer] > minMeals + 1) {
            return false;
        }

        return true;
    }

    public boolean hasFood() {

        return food.hasFood();
    }
}
