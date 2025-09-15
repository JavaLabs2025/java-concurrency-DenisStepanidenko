package org.labs.diningphilosofers;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс "Судья"
 * Решает давать ли право программисту взять обе ложки
 */
@Slf4j
public class Judge {

    /**
     * Сколько порций скушал каждый программист
     */
    private int[] countOfServings;

    /**
     * Ложки
     */
    private ReentrantLock[] spoons;


    public Judge(int[] countOfServings) {
        this.countOfServings = countOfServings;
        spoons = new ReentrantLock[countOfServings.length];

        for (int i = 0; i < spoons.length; i++) {
            spoons[i] = new ReentrantLock();
        }
    }

    /**
     * Данный метод предоставляет программисту возможность попытаться взять две ложки для еды
     */
    public boolean tryEat(int idProgrammer) {

        int leftSpoon = idProgrammer;
        int rightSpoon = (idProgrammer + 1) % (countOfServings.length - 1);

        if (spoons[leftSpoon].tryLock()) {

            if (spoons[rightSpoon].tryLock()) {

                log.debug("Программист с id {} захватил ложки {} и {}", idProgrammer, leftSpoon, rightSpoon);

                return true;
            } else {
                spoons[leftSpoon].unlock();
                return false;
            }
        }
        return false;

    }

    /**
     * Данный метод представляет программисту возможность закончить приём пищи и отпустить две ложки + увеличить кол-во съеденных порций
     */
    public void unlockSpoonsWithIncreaseEatenPortions(int idProgrammer) {

        int leftSpoon = idProgrammer;
        int rightSpoon = (idProgrammer + 1) % (countOfServings.length - 1);

        countOfServings[idProgrammer]++;
        spoons[leftSpoon].unlock();
        spoons[rightSpoon].unlock();

        log.debug("Программист с id {} освободил ложки {} и {}", idProgrammer, leftSpoon, rightSpoon);

    }

    /**
     * Данный метод представляет программисту возможность закончить приём пищи и отпустить две ложки
     */
    public void unlockSpoonsWithoutIncreaseEatenPortions(int idProgrammer) {

        int leftSpoon = idProgrammer;
        int rightSpoon = (idProgrammer + 1) % (countOfServings.length - 1);

        spoons[leftSpoon].unlock();
        spoons[rightSpoon].unlock();

        log.debug("Программист с id {} освободил ложки {} и {}", idProgrammer, leftSpoon, rightSpoon);

    }


}
