package ru.hvayon;

import lombok.Data;

import java.util.List;

@Data
public class Edge {

    private int value;

    private Node finalNode;

    private List<Node> inputNodes;

    private int label;

    public Edge(int value, Node finalNode, List<Node> inputNodes) {
        this.value = value;
        this.finalNode = finalNode;
        this.inputNodes = inputNodes;
        this.label = 0;
    }
}
