package com.pch777.model;

import org.junit.jupiter.api.Test;

import static com.pch777.model.PackSize.*;
import static org.junit.jupiter.api.Assertions.*;

class PackSizeTest {

    @Test
    void shouldReturnSmallPackSize() {
        //given
        String size = "small";
        //when
        PackSize result = PackSize.of(size);
        //given
        assertEquals(SMALL, result);
    }

    @Test
    void shouldReturnMediumPackSize() {
        //given
        String size = "medium";
        //when
        PackSize result = PackSize.of(size);
        //given
        assertEquals(MEDIUM, result);
    }

    @Test
    void shouldReturnLargePackSize() {
        //given
        String size = "large";
        //when
        PackSize result = PackSize.of(size);
        //given
        assertEquals(LARGE, result);
    }

    @Test
    void shouldThrownExceptionWhenPackSizeIsUnknown() {
        //given
        String size = "XXL";
        //when
        //given
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> PackSize.of(size));
        assertEquals("Unknown pack size: " + size, exception.getMessage());
    }

}