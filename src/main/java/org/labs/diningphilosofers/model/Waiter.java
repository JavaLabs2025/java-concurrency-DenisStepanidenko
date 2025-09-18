package org.labs.diningphilosofers.model;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс официант
 */
public class Waiter {


    /**
     * Параметры кол-во доступных порций
     * Предоставляет потокобезопасный метод проверки и уменьшения порций
     */
    private final DiningContext diningContext;

    /**
     * Предоставляет из себя лок текущего официанта
     */
    private final ReentrantLock lock = new ReentrantLock();


    public Waiter(DiningContext diningContext) {
        this.diningContext = diningContext;
    }

    /**
     * Метод в котором официант пытается выдать порцию еды программисту
     *
     * @return true, если порции ещё остались, false иначе.
     */
    public boolean givePortion() {
        return diningContext.decreaseCountOfFood();
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
    public boolean isAllowedToEatMore(int idProgrammer) {

        return diningContext.isAllowedToEatMore(idProgrammer);
    }

    /**
     * Проверяет наличие свободных порций
     */
    public boolean isFoodFinished() {
        return diningContext.isFoodFinished();
    }
}
