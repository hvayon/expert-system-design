package ru.hvayon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static List<Disjunct> getKnowledge() {
        List<Disjunct> knowledge = new ArrayList<>();

        knowledge.add(parseDisjunct("L(Петя, Снег)"));
        knowledge.add(parseDisjunct("L(Петя, Дождь)"));

        knowledge.add(parseDisjunct("S(x1) | ~M(x1)"));
        knowledge.add(parseDisjunct("S(x2) | M(x2)"));
        knowledge.add(parseDisjunct("~M(x3) | ~L(x3, Дождь)"));
        knowledge.add(parseDisjunct("~S(x4) | L(x4, Снег)"));
        knowledge.add(parseDisjunct("~L(Лена, y1) | ~L(Петя, y1)"));
        knowledge.add(parseDisjunct("~L(Лена, y2) | L(Петя, y2)"));

        return knowledge;
    }

    public static Disjunct parseDisjunct(String exp) {
        String[] atoms = exp.split("\\|");
        List<Atom> atomList = new ArrayList<>();

        for (String atom : atoms) {
            atom = atom.trim();
            boolean isPositive = atom.charAt(0) != '~';

            if (!isPositive) {
                atom = atom.substring(1).trim();
            }

            String[] parts = atom.split("\\(");
            String atomName = parts[0].trim();
            String atomArgs = parts[1].split("\\)")[0].trim();

            String[] atomTerms = atomArgs.split(",");
            List<Term> terms = new ArrayList<>();

            for (String term : atomTerms) {
                term = term.trim();
                String type = Character.isUpperCase(term.charAt(0)) ? "const" : "var";
                terms.add(new Term(term, type, null));
            }

            atomList.add(new Atom(atomName, terms,  isPositive));
        }

        return new Disjunct(atomList);
    }

    /* дизъюнкт для резолюции */
    public static Disjunct resolve(Disjunct resolving, List<Disjunct> knowledge, int maxCounter) {
        int resolvingCount = 1;
        int counter = maxCounter;

        while (resolvingCount > 0 && counter > 0) {
            resolvingCount = 0;

            for (Disjunct rule : knowledge) {
                System.out.println("1. Дизьюнкт " + resolving);
                System.out.println("2. Дизьюнкт " + rule);
                CustomPair<Disjunct, Map<String, Term>> result = new Unification().unificateDisjunct(resolving, rule);
                System.out.println("Результат унификации дизъюнктов " + result);
                System.out.println("\n\n");
                if (result == null) {
                    continue;
                }

                System.out.println(resolving + " ~~~ " + rule + " ==== (" + result.getSecond() + ")\n ==> " + result.getDisjunct() + "\n");

                resolving = result.getDisjunct();
                resolvingCount++;
                counter--;
                break;
            }
        }

        if (resolvingCount > 0) {
            System.out.println("Tries out");
        }

        return resolving;
    }

    public static void main(String[] args) {
        //Disjunct disjunct = Disjunct.parseDisjunct("S(x4) | ~M(K)");
        //System.out.println(disjunct);

        List<Disjunct> knowledge = getKnowledge();
        for (Disjunct disjunct1 : knowledge) {
            System.out.println(disjunct1);
        }
        System.out.println("\n");


//        Disjunct leftDisjunct = parseDisjunct("S(x1) | ~M(x1)");
//        Disjunct rightDisjunct = parseDisjunct("S(x2) | M(x2)");
//
//        CustomPair<Disjunct, Map<String, Term>> result = new Unification().unificateDisjunct(leftDisjunct, rightDisjunct);
//
//        System.out.println(result);

        /* доказать отрицание цели */
        Disjunct result = resolve(
                parseDisjunct("L(Лена, Снег) | ~L(Лена, Футбол)"),
                knowledge,
                1000
        );
        // System.out.println(result);
    }
}
