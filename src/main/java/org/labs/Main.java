package org.labs;


import org.labs.diningphilosofers.Food;
import org.labs.diningphilosofers.Judge;
import org.labs.diningphilosofers.Programmer;
import org.labs.diningphilosofers.Waiter;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int n = 100;
        int totalCountInServing = 1;


        Food food = new Food(1000);
        int[] countOfServings = new int[n + 1];
        Programmer[] programmers = new Programmer[n + 1];
        Waiter[] waiters = new Waiter[n + 1];
        Judge judge = new Judge(countOfServings, food);

        for (int i = 1; i <= n; i++) {
            waiters[i] = new Waiter(food, countOfServings);
        }

        for (int i = 1; i <= n; i++) {

            programmers[i] = new Programmer(waiters, judge, totalCountInServing, i);
        }

        for (int i = 1; i <= n; i++) {
            programmers[i].start();
        }

        for (int i = 1; i <= n; i++) {
            programmers[i].join();
        }


        System.out.println("Общее кол-во съеденных порций у каждого программиста");

        for (int i = 1; i <= n; i++) {
            System.out.println("Программист с id " + i + " скушал " + countOfServings[i]);
        }

        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += countOfServings[i];
        }
        System.out.println("Общее кол-во съеденных порций у программистов " + sum);


    }
}