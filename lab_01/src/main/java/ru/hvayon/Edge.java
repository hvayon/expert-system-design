package ru.hvayon;

import jdk.jfr.DataAmount;

import java.util.Objects;


public class Edge {
    Node startNode;
    Node endNode;
    Integer label;
    int mark;

    public Edge(Node startNode, Node endNode, Integer label) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.label = label;
        this.mark = 0;
    }
}
