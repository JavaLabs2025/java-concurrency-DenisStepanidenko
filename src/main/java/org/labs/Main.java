package org.labs;


import lombok.extern.slf4j.Slf4j;
import org.labs.diningphilosofers.Food;
import org.labs.diningphilosofers.Judge;
import org.labs.diningphilosofers.Programmer;
import org.labs.diningphilosofers.Waiter;


@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // кол-во программистов
        int countOfProgrammer = 14;

        // кол-во циклов, чтобы скушать одну порцию
        int totalCountInServing = 2;

        // кол-во официентов
        int countOfWaiters = 2;

        // кол-во порций еды
        int countOfFood = 1_000;


        Food food = new Food(countOfFood);
        int[] countOfServings = new int[countOfProgrammer + 1];
        Programmer[] programmers = new Programmer[countOfProgrammer + 1];
        Waiter[] waiters = new Waiter[countOfWaiters];
        Judge judge = new Judge(countOfServings);

        for (int i = 0; i <= 1; i++) {
            waiters[i] = new Waiter(food, countOfServings);
        }

        for (int i = 1; i <= countOfProgrammer; i++) {
            programmers[i] = new Programmer(waiters, judge, totalCountInServing, i);
        }


        long start = System.currentTimeMillis();
        for (int i = 1; i <= countOfProgrammer; i++) {
            programmers[i].start();
        }

        for (int i = 1; i <= countOfProgrammer; i++) {
            programmers[i].join();
        }

        long finish = System.currentTimeMillis();

        int sum = 0;
        for (int i = 1; i <= countOfProgrammer; i++) {

            log.info("Программист с id {} скушал {}", i, countOfServings[i]);
            sum += countOfServings[i];

        }

        log.info("Общее кол-во съеденных порций у программистов {}", sum);
        log.info("Общее кол-во времени {} сек.", (finish - start) / 1000);


    }
}