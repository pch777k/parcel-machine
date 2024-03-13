package com.pch777.handlers;

import com.pch777.dao.PackDao;
import com.pch777.exceptions.PackNotFoundException;
import com.pch777.input.UserInputCommand;
import com.pch777.model.Pack;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.pch777.model.PackSize.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackCommandHandlerTest {

    private final LogCaptor logCaptor = LogCaptor.forClass(PackCommandHandler.class);
    @Mock
    private PackDao packDao;
    private PackCommandHandler packCommandHandler;

    @BeforeEach
    public void setUp() {
        packCommandHandler = new PackCommandHandler(packDao);
    }

    @Test
    public void shouldHandleListActionAndLogMessagesWhenNoPacksExist() throws PackNotFoundException {
        //given
        String input = "pack list";
        UserInputCommand command = new UserInputCommand(input);

        //when
        when(packDao.getPacks()).thenReturn(Collections.emptyList());
        packCommandHandler.handle(command);

        //then
        verify(packDao, atMostOnce()).getPacks();
        verify(packDao, times(1)).getPacks();
        assertThat(logCaptor.getInfoLogs())
                .containsExactly("List of packs...",
                        "There are no packs, create one");
    }

    @Test
    public void shouldHandleListActionAndLogMessagesWhenPacksExist() throws PackNotFoundException {
        //given
        String input = "pack list";
        List<Pack> packs = Arrays.asList(
                new Pack("ball", MEDIUM),
                new Pack("printer", LARGE));
        UserInputCommand command = new UserInputCommand(input);

        //when
        when(packDao.getPacks()).thenReturn(packs);
        packCommandHandler.handle(command);

        //then
        verify(packDao, times(2)).getPacks();
        assertThat(logCaptor.getInfoLogs())
                .containsExactly("List of packs...");
    }

    @Test
    public void shouldHandleSendActionAndLogMessages() throws PackNotFoundException {
        //given
        String input = "pack send printer large";
        UserInputCommand command = new UserInputCommand(input);

        //when
        packCommandHandler.handle(command);

        //then
        verify(packDao).sendPack(any(Pack.class));
        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Send pack");
    }

    @Test
    public void shouldThrownExceptionIfValidationNumberOfParamsReturnFalse() throws PackNotFoundException {
        //given
        String input = "pack send";
        UserInputCommand command = new UserInputCommand(input);

        //when
        //then
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> packCommandHandler.handle(command));
        assertThat(illegalArgumentException.getMessage()).isEqualTo("Wrong command format. Check help for more information");
        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Send pack");
    }

    @Test
    public void shouldHandleFindActionAndLogMessagesWhenSearchingPackExists() throws PackNotFoundException {
        //given
        Pack pack = new Pack("1","ball", MEDIUM);
        String input = "pack find 1";

        UserInputCommand command = new UserInputCommand(input);

        //when
        when(packDao.findOne(command.getParams().getFirst())).thenReturn(Optional.of(pack));
        packCommandHandler.handle(command);

        //then
        verify(packDao, atLeastOnce()).findOne(anyString());
        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Find a pack");
    }

    @Test
    public void shouldThrownExceptionWhenPackIsNotFound() throws PackNotFoundException {
        //given
        String packNumber = "1";
        String input = "pack find " + packNumber;

        UserInputCommand command = new UserInputCommand(input);

        //when
        when(packDao.findOne(anyString())).thenReturn(Optional.empty());

        //then
        var packNotFoundException = assertThrows(PackNotFoundException.class, () -> packCommandHandler.handle(command));
        assertThat(packNotFoundException.getMessage()).isEqualTo("Pack with number " + packNumber + " not found");
        verify(packDao, atLeastOnce()).findOne(anyString());

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Find a pack");
    }

    @Test
    public void shouldHandleReceiveActionAndLogMessagesWhenPackNumberAndCodeAreCorrect() throws PackNotFoundException {
        //given
        String input = "pack receive 1 0000";

        UserInputCommand command = new UserInputCommand(input);

        //when;
        packCommandHandler.handle(command);

        //then
        verify(packDao, atLeastOnce()).receivePack(anyString(), anyString());
        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Receive a pack");
    }

}