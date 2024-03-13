package com.pch777.model;

public class Pack {

    public static int counter = 1;
    private String number;
    private String name;
    private PackSize size;
    private String code;

    public Pack(String number, String name, PackSize size) {
        this.number = number;
        this.name = name;
        this.size = size;
    }

    public Pack(String name, PackSize size) {
        this.number = "" + counter++;
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PackSize getSize() {
        return size;
    }

    public void setSize(PackSize size) {
        this.size = size;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Pack {" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size.toString() + '\'' +
                '}';
    }
}
