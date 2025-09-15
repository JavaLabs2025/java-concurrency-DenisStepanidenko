package org.labs.diningphilosofers;

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

    public Programmer(Waiter[] waiters, Judge judge, int totalCountOfUnitsOfServing, int id) {
        this.waiters = waiters;
        this.judge = judge;
        this.totalCountOfUnitsOfServing = totalCountOfUnitsOfServing;
        this.currentUnitsOfServing = 0;
        this.id = id;
    }

    @Override
    public void run() {

        try {

            while (true) {

                discussion();

                // если скушали всю порцию, то пробуем попросить у какого-нибудь официанта ещё
                if (currentUnitsOfServing == 0) {

                    for (Waiter waiter : waiters) {

                        if (waiter.tryLock()) {


                            try {

                                log.debug("Мы нашли официанта");
                                // Программист может запросить новую порцию если выполненые следующие условия
                                // 1) Кол-во порцию > 0
                                // 2) Он не съел сильно больше других на текущий момент времени

                                if (!waiter.hasEatenTooMuch(id)) {

                                    if (waiter.givePortion()) {

                                        log.info("Программист с id {} получил новую порцию еды.", id);
                                        currentUnitsOfServing = totalCountOfUnitsOfServing;
                                        break;
                                    }

                                }

                                if (!waiter.isFoodAvailable()) {

                                    log.debug("Программист с id {} получил сообщение, что еда законичилась. Завершает поток.", id);
                                    this.interrupt();
                                    break;

                                }


                            } finally {
                                waiter.unlock();
                            }


                        }
                    }

                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }

                    // мы не смогли ни у одного из официантов получить еды, все заняты, попробуем позже
                    if (currentUnitsOfServing == 0) {

                        log.debug("Программист с id {} не смог найти официанта доступного для порции/ слишком много скушал, начинает обсуждает преподавателей.", id);
                        continue;
                    }

                }


                if (judge.tryEat(id)) {

                    eating();

                    if (currentUnitsOfServing == 0) {
                        judge.unlockSpoonsWithIncreaseEatenPortions(id);
                    } else {
                        judge.unlockSpoonsWithoutIncreaseEatenPortions(id);
                    }


                } else {
                    log.debug("Программист с id {} не смог захватить ложки. Идёт думать", id);

                }
            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void eating() throws InterruptedException {

        currentUnitsOfServing--;
        log.debug("Программист с id {} начинает кушать", id);

        // Для бенчмарков убирать имитацию работы
        //Thread.sleep(10);

    }

    private void discussion() throws InterruptedException {

        log.debug("Программист с id {} начинает обсуждения преподавателей", id);

        // Для бенчмарков убирать имитацию работы
        //Thread.sleep(10);

    }
}
