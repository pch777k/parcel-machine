package com.pch777.handlers;

import com.pch777.exceptions.PackNotFoundException;
import com.pch777.input.UserInputCommand;

public interface CommandHandler {

    boolean supports(String name);

    void handle(UserInputCommand userInputCommand) throws PackNotFoundException;
}
