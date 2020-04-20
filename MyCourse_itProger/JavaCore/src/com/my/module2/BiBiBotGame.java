package com.my.module2;

import java.util.Scanner;

public class BiBiBotGame {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String playerName = "";
        int botCount = 0;
        int playerCount = 0;

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> НАЧАЛО ИГРЫ <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        printBotWords();
        print1();

        printBotWords();
        System.out.print("\tДавайте теперь проверим Вашу память, а именно, " +
                "не мог ли бы Вы назвать своё имя?\n\tВаше имя: ");
        playerName = in.next();
        print2(playerName);

        String var;
        String answer;

        do {
            do {
                System.out.print("\tВыбирите один из вариантов: ");
                var = in.next();
            } while (!("камень".equalsIgnoreCase(var)
                    || "ножницы".equalsIgnoreCase(var)
                    || "бумага".equalsIgnoreCase(var)));

            Player bot = new Player();
            Player player = new Player(playerName, var);

            printBotWords();
            System.out.println("\tВы выбрали: " + player.getVarText() + ", a " + bot.getName()
                    + " выбрал: " + bot.getVarText());

            System.out.println("\t" + bot.whoWins(bot, player));

            if (bot.getIsWin()) {
                botCount++;
            } else if (player.getIsWin()) {
                playerCount++;
            }

            printBotWords();
            System.out.print("\tСыграем ещё раз? Да? ");
            answer = in.next();

        } while ("да".equalsIgnoreCase(answer));

        print3(playerName, playerCount, botCount);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> КОНЕЦ ИГРЫ <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    private static void printBotWords() {
        System.out.println("Слова БиБиБота:");
    }

    private static void print1() {
        System.out.println("\tОоо... Вы наконец-то проснулись! Я очень рад!");
        System.out.println("\tМы попали в кораблекрушение, и Вы были три дня без сознания, " +
                "а сейчас Вы пришли в себя, и это не может не радовать!");
        System.out.println("\tАх да... Мы с Вами не знакомы... Извините меня, за мою непоследовательность!");
        System.out.println("\tМеня зовут - БиБиБот, но друзья зовут меня просто - Бот. Я обычный робот.");
    }

    private static void print2(String playerName) {
        printBotWords();
        System.out.println("\tВас зовут - " + playerName + "? Оууу, у Вас отличное имя! Очень приятно познакомиться!");

        printBotWords();
        System.out.println("\tВижу к Вам вернулась память, а давайте теперь проверим Вашу логику " +
                "и поиграем,\n\tну например, в игру - \"Камень, Ножницы, Бумага\"");
    }

    private static void print3(String playerName, int playerCount, int botCount) {
        printBotWords();
        System.out.println("\tЯ понял, тогда пора заканчивать игру. Кажется, что Вы утомились. " +
                "\n\tНо теперь к Вам вернулась память и логика, а значит Вы идёте на поправку, " +
                "но сейчас Вам лучше ещё отдыхать.");

        printBotWords();
        System.out.println("\tОбщий счёт нашей игры: " + playerName + " - " + playerCount + ", a Бот - " + botCount + ".");

        if (playerCount > botCount) {
            System.out.println("\tПобедил - " + playerName + "!");
        } else if (playerCount < botCount) {
            System.out.println("\tПобедил - Бот!");
        } else {
            System.out.println("\tПобедила - Дружба!");
        }

        printBotWords();
        System.out.println("\tДорогой, " + playerName + "! Если захотите, то мы сыграем с Вами ещё, но только позже. " +
                "\n\tА сейчас отдыхайте, до скорой встречи!\n");
    }
}

class Player {
    private String name;
    private VARIANTS var;
    private boolean isWin;

    Player() {
        name = "Бот";
        int num = 1 + (int) (Math.random() * 3);
        switch (num) {
            case 1: var = VARIANTS.ROCK;
                break;
            case 2: var = VARIANTS.PAPER;
                break;
            case 3: var = VARIANTS.SCISSORS;
                break;
        }
    }

    Player(String name, VARIANTS var) {
        this.name = name;
        this.var = var;
    }

    Player(String name, String var) {
        this.name = name;
        this.var = varEnum(var);
    }

    public static void main(String[] args) {
        Player bot = new Player();
        Player alex = new Player("Алекс", VARIANTS.ROCK);

        System.out.println("ЦУ... ЕЕЕ... ФА... " + alex.getName() + " выбрал: "
                + alex.getVarText()  + ", а " + bot.getName() + " выбрал: " + bot.getVarText());
        System.out.println(bot.whoWins(bot, alex));
    }

    public String getName() {
        return name;
    }

    public VARIANTS getVar() {
        return var;
    }

    public boolean getIsWin() {
        return isWin;
    }

    private void setIsWin() {
        isWin = true;
    }


    public String whoWins(Player p1, Player p2) {
        String result = "";
        boolean p1Win = ( p1.getVar() == VARIANTS.SCISSORS && p2.getVar() == VARIANTS.PAPER
                || p1.getVar() == VARIANTS.ROCK && p2.getVar() == VARIANTS.SCISSORS
                || p1.getVar() == VARIANTS.PAPER && p2.getVar() == VARIANTS.ROCK );

        if (p1.getVar() == p2.getVar()) {
            result = "Получается - Ничья!";
        } else if (p1Win) {
            result = "Получается победил - " + p1.getName() + "!";
            p1.setIsWin();
        } else {
            result = "Получается победил - " + p2.getName() + "!";
            p2.setIsWin();
        }
        return result;
    }

    public String getVarText() {
        String s = "";
        switch (this.getVar()) {
            case ROCK: s = "Камень";
                break;
            case PAPER: s = "Бумага";
                break;
            case SCISSORS: s = "Ножницы";
                break;
        }
        return s;
    }

    public VARIANTS varEnum(String var) {
        VARIANTS varE = null;
        switch (var.toLowerCase()) {
            case "камень": varE = VARIANTS.ROCK;
                break;
            case "бумага": varE = VARIANTS.PAPER;
                break;
            case "ножницы": varE = VARIANTS.SCISSORS;
                break;
        }
        return varE;
    }
}

enum VARIANTS {
    ROCK, PAPER, SCISSORS
}