package com.pch777.handlers;

import com.pch777.exceptions.QuitIdeasApplicationException;
import com.pch777.input.UserInputCommand;

public class QuitCommandHandler extends BaseCommandHandler {
    public static final String COMMAND_NAME = "quit";

    @Override
    public void handle(UserInputCommand userInputCommand) {
        throw new QuitIdeasApplicationException();
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }
}
