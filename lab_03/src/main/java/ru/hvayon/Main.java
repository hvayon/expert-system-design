package ru.hvayon;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);
        Node node10 = new Node(10);
        Node node11 = new Node(11);
        Node node12 = new Node(12);
        Node node13 = new Node(13);
        Node node14 = new Node(14);
        Node node15 = new Node(15);
        Node node18 = new Node(18);
        Node node31 = new Node(31);
        Node node32 = new Node(32);
        Node node37 = new Node(37);
        Node node21 = new Node(21);

        Edge edge1 = new Edge(104, node3, Arrays.asList(node8, node31));
        Edge edge2 = new Edge(101, node3, Arrays.asList(node1, node2));
        Edge edge3 = new Edge(102, node7, Arrays.asList(node3, node2, node4));
        Edge edge4 = new Edge(103, node4, Arrays.asList(node5, node6));
        Edge edge5 = new Edge(107, node11, Arrays.asList(node12, node13));
        Edge edge6 = new Edge(106, node9, Arrays.asList(node4, node10, node11));
        Edge edge7 = new Edge(105, node14, Arrays.asList(node7, node9));
        Edge edge8 = new Edge(111, node9, Arrays.asList(node18, node32));
        Edge edge9 = new Edge(110, node14, Arrays.asList(node9, node21));
        Edge edge10 = new Edge(108, node37, Arrays.asList(node21, node15));

        List<Edge> edges = Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6, edge7, edge8, edge9, edge10);

        List<Node> defNodes = Arrays.asList(node8, node31, node2, node5, node6, node10, node11);


        GraphSearch graphSearch = new GraphSearch(edges, node14, defNodes);

        System.out.println("Исходные данные");
        for (Edge edge : edges) {
            System.out.println("\nНомер правила: " + edge.getValue() + " Выходная вершина: " + edge.getFinalNode().toString());
            System.out.println("Входные вершины: ");
            edge.getInputNodes().forEach(x-> System.out.print(x.toString() + " "));
        }
        graphSearch.search();
    }
}
