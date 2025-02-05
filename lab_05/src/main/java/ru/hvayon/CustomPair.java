package ru.hvayon;

import lombok.Getter;

@Getter
public class CustomPair<K, V> {
    private K first;
    @Getter
    private V second;

    public CustomPair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getDisjunct() {
        return first;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second +
                ")";
    }
}
