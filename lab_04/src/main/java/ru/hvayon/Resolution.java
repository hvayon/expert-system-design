package ru.hvayon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Resolution {

    public enum ResolutionResult {
        PROVEN_BY_FOUNDING_EMPTY_DISJUNKT,
        MAX_ITERATIONS_EXCEEDED,
        K_NO_PROOF_FOUND,
        NO_NEW_DISJUNKT_ADDED,
    }

    static ResolutionResult FullResolution(List<Clause> axioms, Clause goal, int maxIter) {

        Resolvent resolvent = new Resolvent();
        System.out.print("[Полный перебор] ");
        PrintInput(axioms, goal);
        // формируем начальный список дизъюнктов и отрицание цели
        List<Clause> clauseList = new ArrayList<>(axioms.size() + 1);
        clauseList.addAll(axioms);
        clauseList.add(goal);

        for (int iter = 1, i = 0; i < clauseList.size() - 1; i++) {
            System.out.print("[Итерация " + iter + "] Множество дезъюнктов: {");
            clauseList.forEach(x -> System.out.print(" " + x.toString()));
            System.out.println(" }");

            for (int j = 0; j < clauseList.size(); j++) {
                if (i == j) { // если дизъюнкты совпадают
                    continue;
                }
                iter++;

                if (iter >= maxIter) {
                    return ResolutionResult.MAX_ITERATIONS_EXCEEDED; // достигли максимум
                }

                Clause clause1 = clauseList.get(i);
                Clause clause2 = clauseList.get(j);

                // пытаемся найти резольвенту
                CustomPair<Clause, Resolvent.ResolventResult> pair =
                        resolvent.createResolvent(clause1, clause2);

                if (pair.getResolventResult() == Resolvent.ResolventResult.EMPTY_CLAUSE) {
                    // Если нашли пустой дизъюнкт,
                    System.out.println("Найдена резольвента дизъюнктов " + clause1 + " " + clause2 + ": {}\n");
                    return ResolutionResult.PROVEN_BY_FOUNDING_EMPTY_DISJUNKT; // то доказали утверждение.
                }

                if (pair.getResolventResult() == Resolvent.ResolventResult.OK) {
                    System.out.print("Найдена резольвента дизъюнктов " + clause1 + " и " + clause2 + ": " +
                            pair.getResolvent());
                    boolean flag = false;
                    for (Clause cl : clauseList) {
                        if (cl.equals(pair.getResolvent())) { // если такой дизъюнкт уже есть, то не добавляем
                            System.out.print(" [не добавляем]");
                            flag = true;
                            break;
                        }
                    }
                    System.out.println();
                    if (!flag) {
                        clauseList.add(pair.getResolvent());  // то добавляем её в список
                    }
                }
            }
        }
        return ResolutionResult.K_NO_PROOF_FOUND;
    }

    static ResolutionResult BasicResolution(List<Clause> axioms,
                                            Clause inverted_target, int maxIter) {

        Resolvent resolvent = new Resolvent();
        System.out.print("[Опорное множество] ");
        PrintInput(axioms, inverted_target);

        // дизьюнкты отрицания цели
        List<Clause> invertedTargetDisjuncts = new ArrayList<>(List.of(inverted_target));

        for (int iter = 0;;) {
            List<Clause> disjunctsToAppend = new ArrayList<>();

            System.out.print("[Итерация " + iter + "] Множество дезъюнктов: {");
            invertedTargetDisjuncts.forEach(x -> System.out.print(" " + x.toString()));
            System.out.println(" }");

            for (Clause clause2 : invertedTargetDisjuncts) {
                for (Clause clause1 : axioms) {
                    // Та же логика, что и в полном переборе, только добавляем не в общий
                    // стек, а в invertedTargetDisjuncts.

                    iter++;
                    if (iter >= maxIter) {
                        return ResolutionResult.MAX_ITERATIONS_EXCEEDED; // достигли максимум
                    }

                    // пытаемся найти резольвенту
                    CustomPair<Clause, Resolvent.ResolventResult> pair =
                            resolvent.createResolvent(clause1, clause2);

                    if (pair.getResolventResult() == Resolvent.ResolventResult.EMPTY_CLAUSE) {
                        // Если нашли пустой дизъюнкт,
                        System.out.println("Найдена резольвента дизъюнктов " + clause1 + " " + clause2 + ": {}\n");
                        return ResolutionResult.PROVEN_BY_FOUNDING_EMPTY_DISJUNKT; // то доказали
                        // утверждение.
                    }
                    if (pair.getResolventResult() == Resolvent.ResolventResult.OK) { // Если получилось,
                        System.out.print("Найдена резольвента дизъюнктов " + clause1 + " и " + clause2 + ": " +
                                pair.getResolvent());
                        boolean flag = false;
                        List<Clause> combinedList = Stream.concat(disjunctsToAppend.stream(), invertedTargetDisjuncts.stream()).toList();
                        for (Clause clause : combinedList) {
                            if (clause.equals(pair.getResolvent())) { // если такой дизъюнкт уже есть, то не добавляем
                                System.out.print(" [не добавляем]");
                                flag = true;
                                break;
                            }
                        }
                        System.out.println();
                        if (!flag) {
                            disjunctsToAppend.add(pair.getResolvent());  // то добавляем её в стек. (поменять на резольвенту)
                        }
                    }
                }
            }
            if (disjunctsToAppend.isEmpty()) {
                return ResolutionResult.NO_NEW_DISJUNKT_ADDED;
            }
            invertedTargetDisjuncts.addAll(disjunctsToAppend);
        }
    }

    static void PrintInput(List<Clause> axioms, Clause clause) {
        System.out.println("\nВходные данные:");
        System.out.println("\tАксиомы:");

        for (int i = 1; i <= axioms.size(); i++) {
            System.out.println("\t\t" + i + ". " + axioms.get(i - 1));
        }
        System.out.println("\tЦель: " + clause);
    }

    public static String printResult(ResolutionResult result) {
        switch (result) {
            case PROVEN_BY_FOUNDING_EMPTY_DISJUNKT:
                return "Истина : Доказано, найдена контрарная пара";
            case MAX_ITERATIONS_EXCEEDED:
                return "Ложь : Выход по числу итераций";
            case K_NO_PROOF_FOUND:
                return "Ложь : Не найдено доказательство";
            case NO_NEW_DISJUNKT_ADDED:
                return "Ложь : Новых дизъюнктов не добавилось";
        }
        return "Неопределённый результат";
    }
}
