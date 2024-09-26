package vudovenko.dev.operations.consoleInput;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ConsoleInputService {

    private final Scanner scanner;

    public Long readLong(String prompt) {
        System.out.println(prompt);
        while (!scanner.hasNextLong()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        Long value = scanner.nextLong();
        scanner.nextLine();

        return value;
    }

    public Double readDouble(String prompt) {
        System.out.println(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        Double value = scanner.nextDouble();
        scanner.nextLine();

        return value;
    }

    public String readString(String prompt) {
        String input = "";
        System.out.println(prompt);
        while (input.isBlank()) {
            input = scanner.nextLine();
            if (input.isBlank()) {
                System.out.println("Строка не может быть пустой. Попробуйте еще раз.");
            }
        }

        return input;
    }
}
