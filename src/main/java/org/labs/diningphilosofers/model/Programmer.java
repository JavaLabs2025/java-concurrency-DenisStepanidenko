package org.labs.diningphilosofers.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Programmer extends Thread {

    /**
     * Доступные официанты
     */
    private final Waiter[] waiters;

    /**
     * Судья, который выдаёт разрешение на взятие обеих ложек
     */
    private final Judge judge;

    /**
     * Сколько нужно циклов, чтобы скушать порцию
     */
    private final int totalCountOfUnitsOfServing;

    /**
     * Сколько на данный момент произошло циклов для поедания одной порции
     */
    private int currentUnitsOfServing;

    /**
     * Id программиста
     */
    private final int id;

    /**
     * Нужно ли ещё выполнять блок кода в run()?
     */
    private boolean isActive = true;

    private final DiningContext diningContext;

    public Programmer(Waiter[] waiters, Judge judge, int totalCountOfUnitsOfServing, int id, DiningContext diningContext) {
        this.waiters = waiters;
        this.judge = judge;
        this.totalCountOfUnitsOfServing = totalCountOfUnitsOfServing;
        this.currentUnitsOfServing = 0;
        this.id = id;
        this.diningContext = diningContext;
    }

    @Override
    public void run() {

        try {

            while (isActive) {

                discussion();

                if (needNewPortion()) {

                    boolean isSuccess = tryGetNewPortion();

                    if (!isSuccess) {
                        continue;
                    }

                }

                tryToEat();

            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Попытка захватить у судьи право на взятие двух ложек.
     */
    private void tryToEat() throws InterruptedException {

        if (judge.tryEat(id)) {

            eating();

            if (needNewPortion()) {
                diningContext.increaseCountOfPortion(id);
            }

            judge.unlockSpoons(id);

        } else {
            log.debug("Программист с id {} не смог захватить ложки. Идёт обсуждать преподавателей.", id);

        }


    }

    /**
     * Попытка захватить внимание официанта и выпросить порцию.
     */
    private boolean tryGetNewPortion() {

        for (Waiter waiter : waiters) {

            if (waiter.tryLock()) {

                try {

                    // Программист может запросить новую порцию если выполненые следующие условия
                    // 1) Кол-во порцию > 0
                    // 2) Он не съел сильно больше других на текущий момент времени

                    if (waiter.isAllowedToEatMore(id)) {

                        if (waiter.givePortion()) {

                            log.debug("Программист с id {} получил новую порцию еды.", id);
                            currentUnitsOfServing = totalCountOfUnitsOfServing;
                            return true;
                        }

                    }

                    if (waiter.isFoodFinished()) {

                        log.debug("Программист с id {} получил сообщение, что еда законичилась. Завершает поток.", id);
                        isActive = false;
                        return false;

                    }


                } finally {
                    waiter.unlock();
                }


            }
        }

        log.debug("Программист с id {} не смог захватить внимание официантов. Идёт обсуждать преподвателей.", id);

        return false;


    }

    private boolean needNewPortion() {
        return currentUnitsOfServing <= 0;
    }

    /**
     * Процесс приёма еды
     */
    private void eating() throws InterruptedException {

        currentUnitsOfServing--;
        log.debug("Программист с id {} начинает кушать", id);

        // Для бенчмарков убирать имитацию работы
        //Thread.sleep(10);

    }

    /**
     * Процесс обсуждения преподавателей
     */
    private void discussion() throws InterruptedException {

        log.debug("Программист с id {} начинает обсуждения преподавателей", id);

        // Для бенчмарков убирать имитацию работы
        //Thread.sleep(10);

    }
}
