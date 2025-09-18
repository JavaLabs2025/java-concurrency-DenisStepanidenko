package org.labs.diningphilosofers.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс для управления симуляцией обедающих философах
 */
@Getter
public class SettingsSimulation {

    private final DiningContext diningContext;
    private final Programmer[] programmers;
    private final Waiter[] waiters;
    private long timeExecution;

    public SettingsSimulation(int countOfProgrammer, int countOfWaiters, int totalCountOfFood, int totalCountInServing) {

        AtomicInteger countOfFood = new AtomicInteger(totalCountOfFood);


        int[] countOfServings = new int[countOfProgrammer];
        Programmer[] programmers = new Programmer[countOfProgrammer];
        DiningContext diningContext = new DiningContext(countOfFood, countOfServings);
        Waiter[] waiters = new Waiter[countOfWaiters];
        Judge judge = new Judge(countOfProgrammer);

        for (int i = 0; i < countOfWaiters; i++) {
            waiters[i] = new Waiter(diningContext);
        }

        for (int i = 0; i < countOfProgrammer; i++) {
            programmers[i] = new Programmer(waiters, judge, totalCountInServing, i, diningContext);
        }

        this.diningContext = diningContext;
        this.programmers = programmers;
        this.waiters = waiters;
    }

    public void startSimulation() throws InterruptedException {

        long startTime = System.currentTimeMillis();
        for (Programmer programmer : programmers) {
            programmer.start();
        }


        for (Programmer programmer : programmers) {
            programmer.join();
        }
        timeExecution = System.currentTimeMillis() - startTime;

    }

    public int sumOfTotalEatenFood() {

        int[] totalEatenFood = diningContext.getMealsEaten();

        return Arrays.stream(totalEatenFood).sum();
    }


}
