package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        // Перше асинхронне завдання: симуляція обчислення
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Running task 1...");
            sleep(1000); // симуляція затримки
            return 10;
        });

        // Друге асинхронне завдання: симуляція обчислення
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Running task 2...");
            sleep(1500); // симуляція затримки
            return 20;
        });

        // Об'єднання результатів двох завдань за допомогою thenCombine
        CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
            System.out.println("Combining results...");
            return result1 + result2;
        });

        // Використання allOf для очікування завершення всіх завдань
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(future1, future2);

        // Кінцевий результат
        try {
            // Очікуємо завершення всіх завдань
            allTasks.join();
            // Отримуємо об'єднаний результат
            Integer finalResult = combinedFuture.get();
            System.out.println("Final result: " + finalResult);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Допоміжний метод для симуляції затримки
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
