package com.pch777.handlers;

import com.pch777.exceptions.QuitIdeasApplicationException;
import com.pch777.input.UserInputCommand;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuitCommandHandlerTest {
    private final QuitCommandHandler quitCommandHandler = new QuitCommandHandler();

    @Test
    public void shouldThrownExceptionWhenUserInputCommandIsQuit()  {
        //given
        String input = "quit";
        UserInputCommand command = new UserInputCommand(input);

        //when
        //then
        assertThrows(QuitIdeasApplicationException.class, () -> quitCommandHandler.handle(command));

    }
}

