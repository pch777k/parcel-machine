package com.pch777.dao;

import com.pch777.exceptions.PackNotFoundException;
import com.pch777.model.Pack;
import com.pch777.model.ParcelMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pch777.model.PackSize.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackDaoTest {

    @Mock
    private ParcelMachine parcelMachine;
    private PackDao packDao;

    @BeforeEach
    void setUp() {
        packDao = new PackDaoImpl(parcelMachine);
    }

    @Test
    void shouldFindOnePackByNumber() {
        // given
        String packNumber = "1";
        Pack pack = new Pack(packNumber, "mouse", SMALL);
        when(parcelMachine.getPack(packNumber)).thenReturn(Optional.of(pack));

        // when
        Optional<Pack> result = packDao.findOne(packNumber);

        // then
        assertTrue(result.isPresent());
        assertEquals(pack, result.get());
        verify(parcelMachine, times(1)).getPack(packNumber);
    }

    @Test
    void shouldNotFindOnePackByNumber() {
        // given
        String packNumber = "1";
        when(parcelMachine.getPack(packNumber)).thenReturn(Optional.empty());

        // when
        Optional<Pack> result = packDao.findOne(packNumber);

        // then
        assertFalse(result.isPresent());
        verify(parcelMachine, times(1)).getPack(packNumber);
    }

    @Test
    void shouldGetAllPacks() {
        // given
        List<Pack> packs = new ArrayList<>();
        packs.add(new Pack("1", "pencil", SMALL));
        packs.add(new Pack("2", "ball", MEDIUM));
        packs.add(new Pack("3", "printer", LARGE));
        when(parcelMachine.getPacks()).thenReturn(packs);

        // when
        List<Pack> result = packDao.getPacks();

        // then
        assertEquals(packs, result);
        verify(parcelMachine, times(1)).getPacks();
    }

    @Test
    void shouldSendPack() {
        // given
        Pack pack = new Pack("pencil", SMALL);

        // when
        packDao.sendPack(pack);

        // then
        verify(parcelMachine, times(1)).sendPack(pack);
    }

    @Test
    void shouldReceivePackByNumberAndCode() throws PackNotFoundException {
        // given
        String packNumber = "1";
        String packCode = "0000";

        // when
        packDao.receivePack(packNumber, packCode);

        // then
        verify(parcelMachine, times(1)).receivePack(packNumber, packCode);
    }
}