package org.labs.diningphilosofers;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс "Судья"
 * Решает давать ли право программисту взять обе ложки
 */
public class Judge {

    /**
     * Сколько порций скушал каждый программист
     */
    private int[] countOfServings;

    /**
     * Ложки
     */
    private ReentrantLock[] spoons;

    private Food food;

    public Judge(int[] countOfServings, Food food) {
        this.countOfServings = countOfServings;
        spoons = new ReentrantLock[countOfServings.length];
        Arrays.fill(spoons, new ReentrantLock());
        this.food = food;
    }


    public boolean tryEat(int idProgrammer) {

        System.out.println("Кол-во оставшихся порций " + food.getTotalCountOfFood());

        int leftSpoon = idProgrammer;
        int rightSpoon = (idProgrammer + 1) % (countOfServings.length - 1);

        if (spoons[leftSpoon].tryLock()) {

            if (spoons[rightSpoon].tryLock()) {

                System.out.println("Программист с id " + idProgrammer + " захватил ложку " + leftSpoon + " и " + rightSpoon);

                return true;
            } else {
                spoons[leftSpoon].unlock();
                return false;
            }
        }
        return false;

    }

    public void unlockSpoons(int idProgrammer) {

        int leftSpoon = idProgrammer;
        int rightSpoon = (idProgrammer + 1) % (countOfServings.length - 1);

        countOfServings[idProgrammer]++;
        spoons[leftSpoon].unlock();
        spoons[rightSpoon].unlock();

        System.out.println("Программист с id " + idProgrammer + " освободил ложку " + leftSpoon + " и " + rightSpoon);

    }
}
