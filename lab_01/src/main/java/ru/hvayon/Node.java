package ru.hvayon;

import java.util.Objects;

public class Node {

    int number;
    // ссылка на предыдущий узел
    private Node prev = null;

    public Node(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}
