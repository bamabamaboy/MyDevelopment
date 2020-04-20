package com.my.module3;

import java.io.*;
import java.util.Scanner;

public class PeopleMakerMachine {
    public static void main(String[] args) {
        print1();

        NewPeople peopleParams = maker();
        paramsPacking(peopleParams);

        print2();

        NewPeople newPeople = peopleUnpacking();
        newPeople.peoplePrint();

        print3();
    }

    private static NewPeople maker() {
        String gender;
        String name;
        String login;
        int age;
        String hobbies;

        Scanner in = new Scanner(System.in);
        System.out.print("\tВведите пол(\"м\", \"ж\"): ");
        gender = in.next();
        System.out.print("\tВведите имя: ");
        name = in.next();
        System.out.print("\tВведите прозвище: ");
        login = in.next();
        System.out.print("\tВведите возраст: ");
        age = in.nextInt();
        System.out.print("\tВведите список умений (через запятую, без пробелов): ");
        hobbies = in.next();

        return new NewPeople(gender, name, login, age, hobbies);
    }

    private static void paramsPacking(NewPeople peopleParams) {
        try {
            FileOutputStream fos = new FileOutputStream("people.maker");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(peopleParams);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static NewPeople peopleUnpacking() {
        NewPeople newPeople = null;
        try {
            FileInputStream fis = new FileInputStream("people.maker");
            ObjectInputStream ois = new ObjectInputStream(fis);
            newPeople = (NewPeople) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newPeople;
    }

    private static void print1() {
        System.out.println("Добро пожаловать в машину для создания людей!");
        System.out.println("Да да, это не шутка, будущее уже наступило, " +
                "и вы можете создать для себя лучшего друга, " +
                "\nпомощника в делах, любовника, любовницу или ребёнка, " +
                "за которым сможете ухаживать и дарить свою любовь.\n");
        System.out.println("Чтобы мечта прямо сейчас стала реальностью, " +
                "Вам достаточно ввести необходимые параметры желаемого человека,\n" +
                "машина обработает информацию и по ним воссоздаст задуманного Вами человека! Давайте попробуем прямо сейчас?\n");

        System.out.println("Введите параметры желаемого человека: ");
    }

    private static void loadPrint(String s) {
        Thread t = new Thread(() -> {
                for (int i = 0; i < 15; i++) {
                    try {
                        Thread.sleep(450);
                        System.out.print(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" Готово!");
    }

    public static void print2() {
        System.out.println("\nЭтапы работы машины:");
        System.out.println("- Обработка полученной информации:");
        loadPrint(">>>");
        System.out.println("- Поиск подходящего биологического профиля человека:");
        loadPrint(">>>");
        System.out.println("- Загрузка данных в инкубатор:");
        loadPrint(">>>");
        System.out.println("- Процесс воссоздания человеке:");
        loadPrint(">>>");

        System.out.println("\nНовый человек - готов!");
    }

    private static void print3() {
        System.out.println("\nСпасибо за то, что воспользовались нашей машиной! " +
                "\nМы хотим делать мир лучше и считаем, что люди не должны быть одиноки!");
    }
}

class NewPeople implements Serializable {
    private String gender;
    private String name;
    private String login;
    private int age;
    private String[] hobbies;

    NewPeople() {
    }

    NewPeople(String name, String login, int age, String hobbies) {
        this.name = name;
        this.login = login;
        this.age = age;
        this.hobbies = hobbies.split(",");
    }

    NewPeople(String gender, String name, String login, int age, String hobbies) {
        this.gender = gender;
        this.name = name;
        this.login = login;
        this.age = age;
        this.hobbies = hobbies.split(",");
    }

    public static void main(String[] args) {
        NewPeople p = new NewPeople("Алекс", "Alex", 67, "гулять,плавать,читать,бегать");
        System.out.println(p.toString());
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public int getAge() {
        return age;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void peoplePrint() {
        System.out.println("\nСозданный человек обладает данными характеристиками:");
        if ("ж".equalsIgnoreCase(gender)) {
            System.out.println("\tЕё зовут: " + name + ",");
            System.out.println("\tУ неё есть прозвище: " + login + ",");
            System.out.println("\tЕё возраст - " + age + ", и она восхитительна,");
            System.out.print("\tА также " + login + " умеет - ");
        } else {
            System.out.println("\tЕго зовут: " + name + ",");
            System.out.println("\tУ него есть прозвище: " + login + ",");
            System.out.println("\tЕго возраст - " + age + ", и он замечателен,");
            System.out.print("\tА также " + login +  " умеет - ");
        }

        for (int i = 0; i < hobbies.length; i++) {
            if (i < hobbies.length - 2) {
                System.out.print(hobbies[i] + ", ");
            } else if (i < hobbies.length - 1) {
                System.out.print(hobbies[i] + " и ");
            } else {
                System.out.print(hobbies[i] + ".");
            }
        }
        System.out.println("\n");

        if ("ж".equalsIgnoreCase(gender)) {
            System.out.println("Смотрите! Она глядит на Вас с широко открытыми глазами! Любуется Вами!");
        } else {
            System.out.println("Глядите! Он смотрит на Вас, улыбается! Он рад Вас видеть!");
        }
    }

    public String toString() {
        String s = "Имя - " + name + ", Логин - " + login +
                ", Возраст - " + age + ", Хобби - \n";
        for(int i = 0; i < hobbies.length; i++) {
            if (i < hobbies.length - 1) {
                s += hobbies[i] + "\n";
            } else {
                s += hobbies[i];
            }
        }
        return s;
    }
}