package com.pch777.handlers;

import com.pch777.input.UserInputCommand;

public class HelpCommandHandler extends BaseCommandHandler  {
    public static final String COMMAND_NAME = "help";

    @Override
    public void handle(UserInputCommand userInputCommand) {
        System.out.println("Help...");
        System.out.println("Allowed commands: help, quit, pack");
        System.out.println("Command pattern: <command> <action> <param1> <param2>");
        System.out.println("Example: pack send PackName PackSize");
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }
}
