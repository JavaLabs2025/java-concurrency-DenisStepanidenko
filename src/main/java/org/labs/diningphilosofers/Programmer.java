package org.labs.diningphilosofers;

public class Programmer extends Thread {

    /**
     * Доступные официанты
     */
    private Waiter[] waiters;

    /**
     * Судья, который выдаёт разрешение на взятие обеих ложек
     */
    private Judge judge;

    /**
     * Сколько нужно циклов, чтобы скушать порцию
     */
    private int totalCountOfUnitsOfServing;

    /**
     * Сколько на данный момент произошло циклов
     */
    private int currentUnitsOfServing;

    private int id;

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

            boolean flag = true;

            while (flag) {

                discussion();

                // скушали всю порцию
                if (currentUnitsOfServing == 0) {

                    // пробуем попросить еды у официантов
                    for (int i = 1; i < waiters.length; i++) {

                        Waiter waiter = waiters[i];
                        if (waiter.tryLock()) {

                            if (waiter.canEating(id)) {

                                if (waiter.givePortion()) {
                                    currentUnitsOfServing = totalCountOfUnitsOfServing;
                                    waiter.unlock();
                                    break;
                                } else {

                                    // вот тут мы зашли в случае, если еды уже больше нет
                                    System.out.println("Программист с id " + id + " получил сообщение, что еда закончилась. Выходит из цикла.");
                                    waiter.unlock();
                                    this.interrupt();
                                    break;
                                }

                            } else {

                                if(!waiter.hasFood()){
                                    waiter.unlock();
                                    this.interrupt();
                                    break;
                                }

                                waiter.unlock();
                                System.out.println("Программист с id " + id + " пока что не может кушать, нужно подождать, чтобы другие наелись тоже.");
                                break;
                            }

                        }
                    }

                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }

                    // мы не смогли ни у одного из официантов получить еды, все заняты, попробуем позже
                    if (currentUnitsOfServing == 0) {
                        System.out.println("Программист с id " + id + " не смог найти официанта доступного для порции/ слишком много скушал, начинает обсуждает преподавателей.");
                        continue;
                    }

                }


                if (judge.tryEat(id)) {

                    eating();

                    judge.unlockSpoons(id);

                } else {
                    System.out.println("Программист с id " + id + " не смог захватить ложки. Идёт думать");

                }
            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void eating() throws InterruptedException {

        currentUnitsOfServing--;
        System.out.println("Программист с id " + id + " начинает кушать");
        Thread.sleep(10);

    }

    private void discussion() throws InterruptedException {

        System.out.println("Программист с id " + id + " начинает обсуждения преподавателей");
        Thread.sleep(10);

    }
}
