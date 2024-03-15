package com.pch777.handlers;

import com.pch777.ParcelMachineApp;
import com.pch777.input.UserInputCommand;

public class HelpCommandHandler extends BaseCommandHandler  {
    private static final String COMMAND_NAME = "help";

    @Override
    public void handle(UserInputCommand userInputCommand) {
        System.out.println("Help...\n" + ParcelMachineApp.howToUseApplication());
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }
}
