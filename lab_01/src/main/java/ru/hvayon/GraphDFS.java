package ru.hvayon;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* метод поиска в ширину пространст состояний */
public class GraphDFS {

    /* целевая нода */
    Node goal;
    List<Edge> edgeList;
    Stack<Node> openedPeak;
    List<Node> closedPeak;
    int j;

    int flagYes;
    int flagNo;


    public GraphDFS(List<Edge> edgeList) {
        this.edgeList = edgeList;
        this.openedPeak = new Stack<>();
        closedPeak = new ArrayList<>();
        this.flagYes = 1;
        this.flagNo = 1;
    }

    public void setGoal(Node goal) {
        this.goal = goal;
    }

    void DFS(Node start, Node goal) {
        System.out.println("Метод поиска в глубину");
        System.out.println("Путь между " + start.toString() + " и " + goal.toString() + ":");
        /* начальную вершину помещаем в стек */
        openedPeak.push(start);
        setGoal(goal);
        while (flagYes == 1 && flagNo == 1) {
            sampleSearch();
            if (flagYes == 0) {
                for (Node node : openedPeak) {
                    System.out.print(node.toString() + " ");
                }
                System.out.println("\n");
                break;
            } else if (j == 0 && !openedPeak.isEmpty()) {
                closedPeak.add(openedPeak.pop());
            } else if (j == 0) {
                System.out.println("Решения нет");
                break;
            }
        }
    }

    /* метод поиска по образцу */
    void sampleSearch() {
        j = 0;
        for (Edge edge: edgeList) {
            if (openedPeak.size() > 0 && edge.startNode.equals(openedPeak.peek()) && edge.endNode.equals(goal)) {
                j = 1;
                flagYes = 0;
                break;
            } else if (openedPeak.size() > 0 && openedPeak.peek().equals(edge.startNode) && edge.mark == 0 && !closedPeak.contains(edge.endNode)) {
                edge.mark = 1;
                openedPeak.push(edge.endNode);
                j = 1;
                break;
            }
        }
    }
}

