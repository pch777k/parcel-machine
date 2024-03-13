package com.pch777.input;

import com.pch777.model.Action;

import java.util.ArrayList;
import java.util.List;

import static com.pch777.model.Action.of;

public class UserInputCommand {

    String command;
    Action action;
    List<String> params;

    public UserInputCommand(String line) {
        if (line != null) {
            String[] array = line.split("\\s");
            if (array.length > 0) {
                command = array[0];
            }
            if (array.length > 1) {
                action = of(array[1]);
            }
            params = new ArrayList<>();
            for (int i = 2; i < array.length; i++) {
                params.add(array[i]);
            }
        }
    }
    public UserInputCommand() {
    }

    public String getCommand() {
        return command;
    }

    public Action getAction() {
        return action;
    }

    public List<String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "UserInputCommand{" +
                "command='" + command + '\'' +
                ", action='" + action + '\'' +
                ", params=" + params +
                '}';
    }


}
