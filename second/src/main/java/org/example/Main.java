package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        SoftwareOption option1 = new SoftwareOption("Software A");
        SoftwareOption option2 = new SoftwareOption("Software B");
        SoftwareOption option3 = new SoftwareOption("Software C");

        // Паралельне отримання даних для кожного варіанту
        CompletableFuture<Void> comparison = CompletableFuture.allOf(
                option1.fetchData(),
                option2.fetchData(),
                option3.fetchData()
        );

        // Після отримання даних вибираємо найкращий варіант
        comparison.thenRun(() -> {
            SoftwareOption bestOption = SoftwareOption.chooseBest(option1, option2, option3);
            System.out.println("Best option: " + bestOption);
        }).join(); // Очікуємо завершення всіх завдань
    }
}

// Клас, що представляє програмне забезпечення
class SoftwareOption {
    private final String name;
    private int price; // Ціна
    private int functionality; // Рівень функціональності
    private int support; // Рівень підтримки

    public SoftwareOption(String name) {
        this.name = name;
    }

    // Симуляція отримання даних
    public CompletableFuture<Void> fetchData() {
        return CompletableFuture.runAsync(() -> {
            System.out.println("Fetching data for " + name + "...");
            this.price = (int) (Math.random() * 100 + 1); // Генеруємо ціну
            this.functionality = (int) (Math.random() * 10 + 1); // Генеруємо рівень функціональності
            this.support = (int) (Math.random() * 10 + 1); // Генеруємо рівень підтримки
            System.out.println(name + ": Price=" + price + ", Functionality=" + functionality + ", Support=" + support);
        });
    }

    // Оцінка на основі ваг для кожного критерію
    public int calculateScore() {
        // Ваги для критеріїв: ціна, функціональність, підтримка
        int priceWeight = -1; // Менша ціна - кращий варіант
        int functionalityWeight = 2;
        int supportWeight = 1;

        return priceWeight * price + functionalityWeight * functionality + supportWeight * support;
    }

    // Вибір найкращого варіанту
    public static SoftwareOption chooseBest(SoftwareOption... options) {
        SoftwareOption bestOption = null;
        int bestScore = Integer.MIN_VALUE;

        for (SoftwareOption option : options) {
            int score = option.calculateScore();
            if (score > bestScore) {
                bestScore = score;
                bestOption = option;
            }
        }
        return bestOption;
    }

    @Override
    public String toString() {
        return name + " [Price=" + price + ", Functionality=" + functionality + ", Support=" + support + "]";
    }
}
