package com.pch777.model;


import com.pch777.exceptions.PackNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static com.pch777.model.PackSize.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParcelMachineTest {

    private ParcelMachine parcelMachine;

    @BeforeEach
    void setUp() {
        parcelMachine = new ParcelMachine();
    }

    @AfterEach
    void tearDown() {
        parcelMachine.getPacks().clear();
        Pack.counter = 1;
    }

    @Test
    void shouldGetAllPacks() {
        //given
        Pack pack1 = new Pack("Smartphone", SMALL);
        Pack pack2 = new Pack("Printer", LARGE);
        parcelMachine.sendPack(pack1);
        parcelMachine.sendPack(pack2);
        //when
        Collection<Pack> packs = parcelMachine.getPacks();
        //then
        assertEquals(2, packs.size());
        assertFalse(packs.isEmpty());
    }

    @Test
    void shouldReturnEmptyCollection() {
        //given
        //when
        Collection<Pack> packs = parcelMachine.getPacks();
        //then
        assertEquals(0, packs.size());
        assertTrue(packs.isEmpty());
    }

    @Test
    void shouldGetAPackByNumber() {
        //given
        Pack pack1 = new Pack("Smartphone", SMALL);
        Pack pack2 = new Pack("Printer", LARGE);
        parcelMachine.sendPack(pack1);
        parcelMachine.sendPack(pack2);
        //when
        Optional<Pack> pack = parcelMachine.getPack(pack1.getNumber());
        //then
        assertEquals(SMALL, pack.get().getSize());
        assertEquals("Smartphone", pack.get().getName());
        assertNotNull(pack.get().getCode());

    }

    @Test
    void shouldNotFoundAPackByNumber() {
        //given
        Pack pack = new Pack("Smartphone", SMALL);
        parcelMachine.sendPack(pack);
        //when
        Optional<Pack> optionalPack = parcelMachine.getPack("2");
        //then
        assertFalse(optionalPack.isPresent());

    }

    @Test
    void shouldSendAPack() {
        //given
        Pack pack = new Pack("Smartphone", SMALL);
        //when
        parcelMachine.sendPack(pack);
        //then
        assertNotNull(pack.getNumber());
        assertNotNull(pack.getCode());
        assertFalse(parcelMachine.getPacks().isEmpty());

    }

    @Test
    void shouldThrownExceptionIfAllBoxesAreNotAvailable() {
        //given
        Pack pack1 = new Pack("Smartphone", SMALL);
        Pack pack2 = new Pack("Mouse", SMALL);
        Pack pack3 = new Pack("Ball", MEDIUM);
        Pack pack4 = new Pack("Printer", LARGE);
        parcelMachine.sendPack(pack1);
        parcelMachine.sendPack(pack2);
        parcelMachine.sendPack(pack3);
        parcelMachine.sendPack(pack4);
        Pack pack5 = new Pack("Kettle", MEDIUM);
        //when
        //then
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> parcelMachine.sendPack(pack5));
        assertEquals("The packStorage is full", illegalArgumentException.getMessage());
    }


    @Test
    void shouldThrownExceptionIfBoxForGivenSizeIsNotAvailable() {
        //given
        Pack pack1 = new Pack("Ball", MEDIUM);
        Pack pack2 = new Pack("Kettle", MEDIUM);
        parcelMachine.sendPack(pack1);
        //when
        //then
        var illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> parcelMachine.sendPack(pack2));

        assertEquals(0, parcelMachine.getBoxSizeManager().get(MEDIUM));
        assertEquals("No available slots for size: " + pack1.getSize(),
                illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrownExceptionIfPackNumberIsNull() {

        assertThrows(PackNotFoundException.class, () -> parcelMachine.receivePack(null, "1111"));
    }

    @Test
    void shouldThrownExceptionIfCodeIsNull() {
        //given
        Pack pack = new Pack("Ball", MEDIUM);
        parcelMachine.sendPack(pack);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> parcelMachine.receivePack(pack.getNumber(), null));

    }

    @Test
    void shouldThrownExceptionIfCodeIsWrong() {
        //given
        Pack pack = new Pack("Ball", MEDIUM);
        parcelMachine.sendPack(pack);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> parcelMachine.receivePack(pack.getNumber(), "9999"));

    }

    @Test
    void shouldReceivedAPackIfPackNumberAndCodeAreCorrect() throws PackNotFoundException {
        //given
        Pack pack = new Pack("Ball", MEDIUM);
        parcelMachine.sendPack(pack);
        //when
        parcelMachine.receivePack(pack.getNumber(), pack.getCode());
        //then
        assertThat(parcelMachine.getPacks()).isEmpty();
        assertThat(parcelMachine.getBoxSizeManager().get(MEDIUM)).isGreaterThan(0);

    }


}