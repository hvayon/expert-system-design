package ru.hvayon;

import java.util.*;

/* Обратный поиск в глубину в графах И/ИЛИ */
public class GraphSearch {


    private List<Edge> edgeList = new ArrayList<>();

    private Node targetNode;

    private List<Node> defaultNodes = new ArrayList<>();

    private int fy = 1;

    private int fn = 1;

    private Stack<Node> openNodes = new Stack<>(); // открытые вершины

    private List<Node> closedNodes = new ArrayList<>(); // закрытые вершины

    private List<Node> forbiddenNodes = new ArrayList<>(); // запрещенные вершины

    private Stack<Edge> openEdges = new Stack<>(); // открытые правила

    private List<Edge> closedEdges = new ArrayList<>(); // закрытые правила

    private final List<Edge> forbiddenEdges = new ArrayList<>(); // запрещенные правила

    public GraphSearch(List<Edge> edgeList, Node targetNode, List<Node> defaultNodes) {
        this.edgeList.addAll(edgeList);
        this.targetNode = targetNode; // целевая вершина
        this.defaultNodes.addAll(defaultNodes);
        this.openNodes.add(targetNode); // добавляем целевую вершину в список открытых
        this.closedNodes.addAll(defaultNodes); // исходные вершины помещаем в список закрытых
    }

    public void search() {
        int j;
        while (fy == 1 && fn == 1) {
            j = childSearch();
            if (fy == 0) {
                System.out.println("\n\nРешение найдено");
                System.out.println("Список закрытых правил");
                closedEdges.forEach(x -> System.out.print(x.getValue() + " "));
                System.out.println("\nСписок закрытых вершин");
                closedNodes.forEach(x -> System.out.print(x.getValue() + " "));
            } else if (j == 0 && openNodes.size() == 1 && openNodes.contains(targetNode)) {
                System.out.println("\n\nРешения нет");
                System.out.println("\nСписок закрытых вершин");
                closedNodes.forEach(x -> System.out.print(x.getValue() + " "));
                fn = 0;
            } else if (j == 0 && openNodes.size() != 0) {
                backTracking();
            }
        }
    }

    /* возвращает
        1, если нашли правило
        0, если нет */
    public int childSearch() {
        for (Edge edge : edgeList) {
            /* если выходная вершина совпадает с подцелью, т.е. раскрывает эту вершину и
               метка правила == 0 */
            if (edge.getFinalNode().equals(openNodes.peek()) && edge.getLabel() == 0) {
                edge.setLabel(1);
                /* номер правила пишем в голову стека открытых правил */
                openEdges.add(edge);

                int l = 0;
                // определяем какие вершины выбранного правила не входят в закрытые
                List<Node> nodes = edge.getInputNodes();
                Collections.reverse(nodes);
                for (Node node : nodes) {
                    if (closedNodes.contains(node)) { // если вершины в списке доказанных выставляем флаг
                        node.setFlag(1);
                    } else if (node.getFlag() == 0) { // определяем какие вершины выбранного правила не входят в закрытые
                        l++;
                        openNodes.add(node); // эти вершины добавляем в голову стека вершин
                    }
                }
                if (l == 0) {
                    markup();
                }
                return 1;
            }
        }
        return 0;
    }

    /* возврат */
    public void backTracking() {
        int flag = 0;
        for (Node node : openEdges.peek().getInputNodes()) {
            this.openNodes.remove(node);
            if (node.getFlag() == 0 && flag == 0) {
                /* текущая подцель объявляется как запрещенная вершина */
                forbiddenNodes.add(node);
                flag++;
            }
        }
        openEdges.peek().setLabel(-1);
        /* перемещаем правило в список запрещенных */
        forbiddenEdges.add(openEdges.pop());
    }

    /* алгоритм разметки */
    public void markup() {
        while (fy == 1) {
            /* проверить, выполняется ли покрытие входных вершин правила из головы стека закрытыми */
            if (new HashSet<>(closedNodes).containsAll(openEdges.peek().getInputNodes())) {
                openEdges.peek().getFinalNode().setFlag(1); // ставим флаг, что правило доказана
                Edge edge = openEdges.pop(); // удаляем из головы открытых вершин
                if (edge.getFinalNode().equals(targetNode)) {
                    fy = 0;
                }
                closedEdges.add(edge);
                closedNodes.add(openNodes.pop());
            } else {
                break;
            }
        }
    }
}