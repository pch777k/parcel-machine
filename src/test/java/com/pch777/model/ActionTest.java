package com.pch777.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    void shouldReturnListAction() {
        //given
        String action = "list";
        //when
        Action result = Action.of(action);
        //given
        assertEquals(Action.LIST, result);
    }

    @Test
    void shouldReturnSendAction() {
        //given
        String action = "send";
        //when
        Action result = Action.of(action);
        //given
        assertEquals(Action.SEND, result);
    }

    @Test
    void shouldReturnFindAction() {
        //given
        String action = "find";
        //when
        Action result = Action.of(action);
        //given
        assertEquals(Action.FIND, result);
    }

    @Test
    void shouldReturnReceiveAction() {
        //given
        String action = "receive";
        //when
        Action result = Action.of(action);
        //given
        assertEquals(Action.RECEIVE, result);
    }

    @Test
    void shouldThrownExceptionWhenActionIsUnknown() {
        //given
        String action = "create";
        //when
        //given
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Action.of(action));
        assertEquals("Unknown action: " + action, exception.getMessage());
    }

}