package ru.hvayon;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/* ПОИСК В ШИРИНУ ОТ ДАННЫХ В ГРАФАХ "И/ИЛИ" */
@Data
public class GraphSearch {

    /* список вершин */
    private List<Edge> edgeList;
    /* список закрытых вершин */
    private List<Node> closedNodes = new ArrayList<>();
    private Node targetNode;
    private int flagY = 1;
    private int flagN = 1;
    private List<Node> allNodes = new ArrayList<>();
    private List<Edge> openEdges = new ArrayList<>();
    /* список закрытых правил */
    private List<Edge> closedEdges = new ArrayList<>();

    public GraphSearch(List<Edge> edgeList, Node targetNode, List<Node> defaultNodes, List<Node> allNodes) {
        this.edgeList = edgeList;
        this.targetNode = targetNode;
        this.closedNodes.addAll(defaultNodes);
        this.allNodes.addAll(allNodes);
    }

    public void printGraph(boolean initialDraw) {
        Graph<String, String> gr = new DirectedSparseGraph<>();
        String title = "Исходный граф";
        if (initialDraw) {
            for (Node node : this.allNodes) {
                gr.addVertex(String.valueOf(node));
            }
            for (Edge edge : this.edgeList) {
                for (Node node : edge.getInputNodes()) {
                    gr.addEdge(String.valueOf(gr), String.valueOf(node), String.valueOf(edge.getFinalNode()));
                }
            }
        } else {
            for (Node node : this.closedNodes) {
                gr.addVertex(String.valueOf(node));
            }
            for (Edge edge : this.openEdges) {
                for (Node node : edge.getInputNodes()) {
                    gr.addEdge(String.valueOf(gr), String.valueOf(node), String.valueOf(edge.getFinalNode()));
                }
            }
            title = "Полученное дерево";
            if (this.flagN == 0) {
                title += ": решения нет";
            } else {
                title += ": цель " + this.targetNode.toString() + " достигнута";
            }
        }

        VisualizationViewer<String, String> vs =
                new VisualizationViewer<>(new ISOMLayout<>(gr), new Dimension(800, 800));
        JFrame frame = new JFrame(title);
        vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        frame.getContentPane().add(vs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void BFS() {
        System.out.println("Список начальных вершин:");
        closedNodes.forEach(x-> System.out.print(x.toString() + " "));
        System.out.println("\nЦелевая вершина: " + this.targetNode);
        int j;
        while (flagY == 1 && flagN == 1) {
            j = patternSearch();
            if (flagY == 0) {
                System.out.println("Решение найдено");
                break;
            } else if (j == 0) {
                this.flagN = 0;
                System.out.println("Нет решения");
            }
        }
    }

    public int patternSearch() {
        int count = 0;
        for (Edge edge : this.edgeList) {
            if (edge.getLabel() == 0) {
                int temp = 0;
                /* проверяем выполняется покрытие входов вершинами из списка закрытых */
                for (Node inputNode : edge.getInputNodes()) {
                    if (this.closedNodes.contains(inputNode)) {
                        inputNode.setFlag(1);
                        temp++;
                    }
                }
                if (edge.getInputNodes().size() == temp) {
                    count++;
                    edge.setLabel(1);
                    edge.getFinalNode().setFlag(1);
                    this.closedNodes.add(edge.getFinalNode());
                    this.openEdges.add(edge);
                    if (edge.getFinalNode().equals(this.targetNode)) {
                        flagY = 0;
                        break;
                    }
                }
            }
        }
        return count;
    }
}
