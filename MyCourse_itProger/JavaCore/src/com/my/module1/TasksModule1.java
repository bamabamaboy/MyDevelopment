package com.my.module1;

public class TasksModule1 {
    public static void main(String[] args) {
        System.out.println("Решение заданий для Модуля №1:");
        //Задание №1
        task1(0, 1000);
        //Задание №2
        int[][] x = { {20, 34, 2}, {9, 12, 18}, {3, 4, 5} };
        task2(x);
    }

    public static void task1(int from, int to) {
        int sum = 0;

        for(int i = from; i <= to; i++) {
            if (i % 3 == 0 || i % 5 == 0) sum += i;
        }
        System.out.println("\tЗадание №1 - Общая сумма: " + sum);
    }

    public static void task2(int[][] m) {
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (min > m[i][j]) min = m[i][j];
            }
        }
        System.out.println("\tЗадание №2 - Минимальное значение: " +  min);
    }
}