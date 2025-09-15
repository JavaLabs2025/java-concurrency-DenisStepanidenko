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
    private final Food food;

    /**
     * Предоставляет из себя лок текущего официанта
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Сколько скушал порций каждый программист
     * Используется для того, чтобы примерно одинаково распределять еду программистам
     */
    private final int[] countOfServings;


    public Waiter(Food food, int[] countOfServings) {
        this.food = food;
        this.countOfServings = countOfServings;
    }

    /**
     * Метод в котором официант пытается выдать порцию еды программисту
     * @return true, если порции ещё остались, false иначе.
     */
    public boolean givePortion() {
        return food.decreaseCountOfFood();
    }

    public boolean tryLock() {
        return lock.tryLock();
    }

    public void unlock() {
        lock.unlock();
    }

    /**
     * Сравнивает кол-во съеденных порций у данного программиста с самым голодным.
     *
     * @return true, если программист съел намного больше чем самый голодный, false иначе.
     * (данный параметр при желании можно менять и задавать)
     */
    public boolean hasEatenTooMuch(int idProgrammer) {

        int minMeals = Integer.MAX_VALUE;
        for (int i = 1; i < countOfServings.length; i++) {
            if (countOfServings[i] < minMeals) {
                minMeals = countOfServings[i];
            }
        }

        return countOfServings[idProgrammer] > minMeals + 1;
    }

    /**
     * Проверяет наличие свободных порций
     */
    public boolean isFoodAvailable() {

        return food.isFoodAvailable();
    }
}
