package com.pch777.model;

import java.util.Objects;

public enum PackSize {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");

    private final String value;

    PackSize(String value) {
        this.value = value;
    }

    public static PackSize of(String value) {
        for(PackSize packSize : values()) {
            if(Objects.equals(packSize.value, value)) {
                return packSize;
            }
        }
        throw new IllegalArgumentException("Unknown pack size: " + value);
    }
}
