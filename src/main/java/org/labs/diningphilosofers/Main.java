package org.labs.diningphilosofers;


import lombok.extern.slf4j.Slf4j;
import org.labs.diningphilosofers.model.SettingsSimulation;


@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {

        int countOfProgrammers = 7;
        int countOfWaiters = 2;
        int totalCountOfFood = 1_000_000;
        int totalCountInServing = 1;

        SettingsSimulation settingsSimulation = new SettingsSimulation(countOfProgrammers, countOfWaiters, totalCountOfFood, totalCountInServing);
        settingsSimulation.startSimulation();

        log.info("Общее кол-во съеденных порций у программистов {}", settingsSimulation.sumOfTotalEatenFood());
        log.info("Общее кол-во времени {} сек.", (settingsSimulation.getTimeExecution()) / 1000.0);
        log.info("Кол-во оставшийся еды {}", settingsSimulation.getDiningContext().getTotalCountOfFood());


    }
}