package ru.hvayon;

public class Constant extends ParamType {

    private String value;

    public Constant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}