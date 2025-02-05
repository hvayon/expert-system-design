package ru.hvayon;

import java.util.*;

public class GraphBFS {
    /* список ребер */
    List<Edge> edgeList;
    ArrayDeque<Node> openedPeak;
    List<Node> closedPeak;
    Node start;
    Node goal;
    int flagYes;
    int flagNo;
    int childCounter;

    public void setStart(Node start) {
        this.start = start;
    }

    public void setGoal(Node goal) {
        this.goal = goal;
    }

    public GraphBFS(List<Edge> edgeList) {
        this.openedPeak = new ArrayDeque<>();
        this.closedPeak = new ArrayList<>();
        this.edgeList = edgeList;
        this.flagYes = 1;
        this.flagNo = 1;
    }

    void BFS(Node start, Node goal) {
        System.out.println("Метод поиска в широту");
        System.out.println("Путь между " + start.toString() + " и " + goal.toString() + ":");
        openedPeak.push(start);
        setStart(start);
        setGoal(goal);
        while (flagYes == 1 && flagNo == 1) {
            searchDescendants();
            if (flagYes == 0) {
                closedPeak.add(openedPeak.removeFirst());
                List<Node> nodeList = getWay();
                for (Node node1 : nodeList) {
                    System.out.print(node1.toString() + " ");
                }
                break;
            }
            Node removeNode = openedPeak.removeFirst();
            if (childCounter > 0) {
                closedPeak.add(removeNode);
            } else if (childCounter == 0 && openedPeak.isEmpty()) {
                flagNo = 0;
                System.out.println("Нет решения");
            }
        }
    }

    /* метод определения всех потомков */
    void searchDescendants() {
        childCounter = 0;
        for (Edge edge: edgeList) {
            /* если образец равен цели, мы нашли решение */
            if (openedPeak.size() > 0 && openedPeak.peek().equals(goal)) {
                flagYes = 0;
                break;
                /* находим инсцендентные ребра */
            } else if (openedPeak.size() > 0 && edge.startNode.equals(openedPeak.peek()) && edge.mark == 0) {
                /* если конечное ребро равно цели, мы нашли решение */
                if (edge.endNode.equals(goal)) {
                    flagYes = 0;
                    break;
                } else {
                    Node node = edge.endNode;
                    node.setPrev(openedPeak.peekFirst());
                    openedPeak.addLast(edge.endNode);
                    edge.mark = 1;
                    childCounter++;
                }
            }
        }
    }
    public List<Node> getWay() {
        List<Node> nodes = new ArrayList<>();
        Node temp = closedPeak.get(closedPeak.size()-1);
        nodes.add(temp);
        while (temp.getPrev() != null) {
            temp = temp.getPrev();
            nodes.add(temp);
        }
        Collections.reverse(nodes);
        return nodes;
    }
}
