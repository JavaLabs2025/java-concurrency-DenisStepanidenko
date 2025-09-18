package org.labs.diningphilosofers.model;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс "Судья"
 * Решает давать ли право программисту взять обе ложки
 */
@Slf4j
public class Judge {

    /**
     * Ложки
     */
    private final ReentrantLock[] spoons;


    public Judge(int countOfProgrammers) {

        spoons = new ReentrantLock[countOfProgrammers];

        for (int i = 0; i < spoons.length; i++) {
            spoons[i] = new ReentrantLock();
        }

    }

    /**
     * Данный метод предоставляет программисту возможность попытаться взять две ложки для еды
     */
    public boolean tryEat(int idProgrammer) {

        int leftSpoon = idProgrammer;
        int rightSpoon = (idProgrammer + 1) % spoons.length;

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
     * Данный метод представляет программисту возможность закончить приём пищи и отпустить две ложки.
     */
    public void unlockSpoons(int idProgrammer) {

        int leftSpoon = idProgrammer;
        int rightSpoon = (idProgrammer + 1) % spoons.length;

        spoons[leftSpoon].unlock();
        spoons[rightSpoon].unlock();

        log.debug("Программист с id {} освободил ложки {} и {}", idProgrammer, leftSpoon, rightSpoon);

    }



}
