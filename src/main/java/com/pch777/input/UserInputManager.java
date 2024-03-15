package com.pch777.input;

import java.util.Scanner;

public class UserInputManager {
    private final Scanner scanner;

    public UserInputManager() {
        scanner = new Scanner(System.in);
    }

    public UserInputCommand nextCommand() {
        return new UserInputCommand(scanner.nextLine());
    }

}
