package org.labs.diningphilosofers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DiningContext {

    /**
     * Общее кол-во порций
     */
    @Getter
    private AtomicInteger totalCountOfFood;

    /**
     * Сколько скушал каждый программист
     */
    private final int[] mealsEaten;

    public DiningContext(AtomicInteger totalCountOfFood, int[] mealsEaten) {
        this.totalCountOfFood = totalCountOfFood;
        this.mealsEaten = mealsEaten;
    }

    /**
     * Атомарно уменьшить кол-во порций.
     */
    public boolean decreaseCountOfFood() {


        while (true) {

            int currentCount = totalCountOfFood.get();
            if (currentCount <= 0) {
                return false;
            }

            if (totalCountOfFood.compareAndSet(currentCount, currentCount - 1)) {
                return true;
            }
        }

    }

    /**
     * Закончились ли порции еды?
     */
    public boolean isFoodFinished() {

        return totalCountOfFood.get() <= 0;

    }

    /**
     * Сравнивает кол-во съеденных порций у данного программиста с самым голодным.
     *
     * @return true, если программист съел намного больше чем самый голодный, false иначе.
     * (данный параметр при желании можно менять и задавать)
     */
    public boolean isAllowedToEatMore(int idProgrammer) {

        int minMeals = Integer.MAX_VALUE;
        for (int countOfServing : mealsEaten) {
            if (countOfServing < minMeals) {
                minMeals = countOfServing;
            }
        }

        return mealsEaten[idProgrammer] <= minMeals + 1;

    }

    /**
     * Увеличивает кол-во съеденных порций.
     */
    public void increaseCountOfPortion(int idProgrammer) {

        mealsEaten[idProgrammer]++;
    }
}
