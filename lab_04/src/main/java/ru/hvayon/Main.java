package ru.hvayon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static ru.hvayon.Resolution.*;

/* Процедура резолюции */
public class Main {

    public static void main(String[] args) {

        List<Clause> axioms = new ArrayList<>();

        Atom a = new Atom("A", true);
        Atom b = new Atom("B", true);
        Atom c = new Atom("C", true);
        Atom d = new Atom("D", true);

        Atom not_a = new Atom("A", false);
        Atom not_b = new Atom("B", false);
        Atom not_c = new Atom("C", false);
        Atom not_d = new Atom("D", false);

        Clause ax1 = new Clause(Arrays.asList(a, b));
        Clause ax2 = new Clause(Arrays.asList(not_a, c));
        Clause ax3 = new Clause(Arrays.asList(not_b, d));
        Clause ax4 = new Clause(List.of(not_c));
        Clause ax5 = new Clause(List.of(not_d));
        Clause ax6 = new Clause(List.of(not_a));

        axioms.add(ax1);
        axioms.add(ax2);
        axioms.add(ax3);
        axioms.add(ax4);
        axioms.add(ax5);

        // тип обхода — full, basic (полный перебор, опорное мн-во)
        Scanner in = new Scanner(System.in);
        System.out.println("Выберете номер стратегии1:\n 1 - полный перебор\n 2 - опорное множество ");
        int n = in.nextInt();
        Resolution.ResolutionResult res;
        if (n == 1) {
            res = FullResolution(axioms, ax6, 1000);
            System.out.println(printResult(res));
        } else if (n == 2) {
            res = BasicResolution(axioms, ax6, 1000);
            System.out.println(printResult(res));
        } else {
            System.out.println("Неверный параметр");
        }
    }
}
