package vudovenko.dev.operations.utils;

import java.util.Scanner;

public class OperationReader {

    public static Long readLong(String prompt, Scanner scanner) {
        System.out.println(prompt);
        while (!scanner.hasNextLong()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        Long value = scanner.nextLong();
        scanner.nextLine();

        return value;
    }

    public static Double readDouble(String prompt, Scanner scanner) {
        System.out.println(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        Double value = scanner.nextDouble();
        scanner.nextLine();

        return value;
    }

    public static String readString(String prompt, Scanner scanner) {
        String input = "";
        System.out.println(prompt);
        while (input.isEmpty()) {
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Строка не может быть пустой. Попробуйте еще раз.");
            }
        }

        return input;
    }
}
