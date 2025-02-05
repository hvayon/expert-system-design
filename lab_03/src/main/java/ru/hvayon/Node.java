package ru.hvayon;

import lombok.Data;

@Data
public class Node {

    private int value;

    /*
    0 - вершина не раскрыта / не доказана
    1 - вершина закрыта / доказана
     */
    private int flag;

    public Node(int value) {
        this.value = value;
        this.flag = 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}