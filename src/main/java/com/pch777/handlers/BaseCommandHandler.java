package com.pch777.handlers;


abstract public class BaseCommandHandler implements CommandHandler {

    @Override
    public boolean supports(String name) {
        return getCommandName().equals(name);
    }

    protected abstract String getCommandName();
}

