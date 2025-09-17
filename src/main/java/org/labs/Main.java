package org.labs;


import lombok.extern.slf4j.Slf4j;
import org.labs.diningphilosofers.DiningContext;
import org.labs.diningphilosofers.Judge;
import org.labs.diningphilosofers.Programmer;
import org.labs.diningphilosofers.Waiter;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // кол-во программистов
        int countOfProgrammer = 14;

        // кол-во циклов, чтобы скушать одну порцию
        int totalCountInServing = 1;

        // кол-во официентов
        int countOfWaiters = 2;

        // кол-во порций еды
        AtomicInteger countOfFood = new AtomicInteger(1_000_000);


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


        long start = System.currentTimeMillis();
        for (int i = 0; i < countOfProgrammer; i++) {
            programmers[i].start();
        }

        for (int i = 0; i < countOfProgrammer; i++) {
            programmers[i].join();
        }

        long finish = System.currentTimeMillis();

        int sum = 0;
        for (int i = 0; i < countOfProgrammer; i++) {

            log.info("Программист с id {} скушал {}", i, countOfServings[i]);
            sum += countOfServings[i];

        }

        log.info("Общее кол-во съеденных порций у программистов {}", sum);
        log.info("Общее кол-во времени {} сек.", (finish - start) / 1000.0);
        log.info("Кол-во оставшийся еды {}", diningContext.getTotalCountOfFood().get());


    }
}