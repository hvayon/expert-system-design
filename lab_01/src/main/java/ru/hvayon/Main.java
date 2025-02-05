package ru.hvayon;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        List<Edge> edgeLst = new ArrayList<>();
        edgeLst.add(new Edge(node0, node1, 101));
        edgeLst.add(new Edge(node1, node2, 102));
        edgeLst.add(new Edge(node0, node3, 103));
        edgeLst.add(new Edge(node3, node4, 104));
        edgeLst.add(new Edge(node3, node5, 105));
        edgeLst.add(new Edge(node6, node5, 106));
        edgeLst.add(new Edge(node5, node4, 107));
        edgeLst.add(new Edge(node2, node6, 108));
        edgeLst.add(new Edge(node2, node4, 109));

        /* метод поиска в глубину */
        GraphDFS graphDFS = new GraphDFS(edgeLst);
        graphDFS.DFS(node0, node4);

        /* метод поиска в ширину */
        GraphBFS graphBFS = new GraphBFS(edgeLst);
        graphBFS.BFS(node0, node4);
    }
}
