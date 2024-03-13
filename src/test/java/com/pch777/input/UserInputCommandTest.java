package com.pch777.input;

import com.pch777.exceptions.QuitIdeasApplicationException;
import com.pch777.handlers.CommandHandler;
import com.pch777.handlers.QuitCommandHandler;
import com.pch777.model.Action;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pch777.model.Action.LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserInputCommandTest {

    @Test
    void shouldBuildCorrectUserInputCommand() {
        //given
        String input = "command find param";

        //when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        //then
        assertEquals("command", userInputCommand.getCommand());
        assertEquals(Action.FIND, userInputCommand.getAction());
        assertLinesMatch(List.of("param"), userInputCommand.getParams());
    }

    @Test
    void shouldBuildCorrectUserInputCommandWithMultipleParams() {
        //given
        String input = "command send param1 param2";

        //when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        //then
        assertEquals("command", userInputCommand.getCommand());
        assertEquals(Action.SEND, userInputCommand.getAction());
        assertLinesMatch(List.of("param1", "param2"), userInputCommand.getParams());
    }

    @Test
    void shouldBuildCorrectUserInputCommandWithoutParams() {
        //given
        String input = "command list";

        //when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        //then
        assertThat(userInputCommand.getCommand()).isEqualTo("command");
        assertThat(userInputCommand.getParams().size()).isEqualTo(0);
        assertThat(userInputCommand.getAction()).isEqualTo(LIST);

    }

    @Test
    void shouldBuildCorrectUserInputCommandWithoutActionAndParams() {
        //given
        String input = "command";

        //when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        //then
        assertEquals("command", userInputCommand.getCommand());
        assertNull(userInputCommand.getAction());
        assertLinesMatch(List.of(), userInputCommand.getParams());
    }

    @Test
    void shouldBuildCorrectUserInputCommandWhenInputIsEmpty() {
        //given
        String input = "";

        //when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        //then
        assertThat(userInputCommand.getCommand()).isBlank();
        assertThat(userInputCommand.getCommand()).isEmpty();
        assertThat(userInputCommand.getAction()).isNull();
        assertLinesMatch(List.of(), userInputCommand.getParams());

    }

    @Test
    void shouldBuildUserInputCommandWhenInputIsNull() {
        //given
        UserInputCommand userInputCommand = new UserInputCommand();
        //when
        //then
        assertNull(userInputCommand.getCommand());
        assertNull(userInputCommand.getAction());
        assertNull(userInputCommand.getParams());
    }

    @Test
    void shouldThrownQuitIdeasApplicationException() {
        //given
        String input = "quit";

        //when
        UserInputCommand userInputCommand = new UserInputCommand(input);
        CommandHandler commandHandler = new QuitCommandHandler();

        //then
        assertThrows(QuitIdeasApplicationException.class, () -> commandHandler.handle(userInputCommand));
    }


}