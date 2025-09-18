package org.labs.diningphilosofers;

import org.junit.jupiter.api.Test;
import org.labs.diningphilosofers.model.SettingsSimulation;

import static org.junit.jupiter.api.Assertions.*;

class DiningPhilosophersTest {

    @Test
    public void testDiningPhilosophersWithSmallCountOfWaiters() throws InterruptedException {

        int countOfProgrammer = 7;
        int countOfWaiters = 1;
        int totalCountOfFood = 1_000_000;
        int totalCountInServing = 1;

        runAndVerifySimulation(countOfProgrammer, countOfWaiters, totalCountOfFood, totalCountInServing);
    }

    @Test
    public void testDiningPhilosophersWithBigCountOfWaiters() throws InterruptedException {

        int countOfProgrammer = 7;
        int countOfWaiters = 50;
        int totalCountOfFood = 1_000_000;
        int totalCountInServing = 1;

        runAndVerifySimulation(countOfProgrammer, countOfWaiters, totalCountOfFood, totalCountInServing);
    }

    @Test
    public void testDiningPhilosophersWithSmallCountOfProgrammer() throws InterruptedException {

        int countOfProgrammer = 2;
        int countOfWaiters = 2;
        int totalCountOfFood = 1_000_000;
        int totalCountInServing = 1;

        runAndVerifySimulation(countOfProgrammer, countOfWaiters, totalCountOfFood, totalCountInServing);
    }

    @Test
    public void testDiningPhilosophersWithSmallCountOfFood() throws InterruptedException {

        int countOfProgrammer = 7;
        int countOfWaiters = 2;
        int totalCountOfFood = 7;
        int totalCountInServing = 1;

        runAndVerifySimulation(countOfProgrammer, countOfWaiters, totalCountOfFood, totalCountInServing);
    }

    @Test
    public void testDiningPhilosophersWithBigCountOfFoodAndProgrammer() throws InterruptedException {

        int countOfProgrammer = 14;
        int countOfWaiters = 2;
        int totalCountOfFood = 1_000_000;
        int totalCountInServing = 1;

        runAndVerifySimulation(countOfProgrammer, countOfWaiters, totalCountOfFood, totalCountInServing);
    }

    /**
     * Запускает и верифицирует симуляцию обедающих философов.
     */
    private void runAndVerifySimulation(int countOfProgrammer, int countOfWaiters, int totalCountOfFood, int totalCountInServing) throws InterruptedException {


        SettingsSimulation settingsSimulation = new SettingsSimulation(countOfProgrammer, countOfWaiters, totalCountOfFood, totalCountInServing);

        settingsSimulation.startSimulation();

        assertEquals(totalCountOfFood, settingsSimulation.sumOfTotalEatenFood());
        assertEquals(0, settingsSimulation.getDiningContext().getTotalCountOfFood().get());


    }

}