package ru.hvayon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Unification {

    public Map<String, Term> unificateAtoms(Atom leftAtom, Atom rightAtom) {

        if (!leftAtom.getName().equals(rightAtom.getName())) {
            return null;
        }

        // Проверка количества аргументов
        if (leftAtom.getArgs().size() != rightAtom.getArgs().size()) {
            return null;
        }

        Map<String, Term> substitutions = new HashMap<>();

        // перебираем термы первого атома
        for (int i = 0; i < leftAtom.getArgs().size(); i++) {
            Term leftTerm = leftAtom.getArgs().get(i);
            Term rightTerm = rightAtom.getArgs().get(i);

            if (leftTerm.getType().equals("const") && rightTerm.getType().equals("const")) {
                if (!(leftTerm.getValue().equals(rightTerm.getValue()))) {
                    return null;
                }
            } else if (leftTerm.getType().equals("var") && rightTerm.getType().equals("const")) {
                substitutions.put(leftTerm.getName(), rightTerm);
            } else if (leftTerm.getType().equals("const") && rightTerm.getType().equals("var")) {
                substitutions.put(rightTerm.getName(), leftTerm);
            } else if (leftTerm.getType().equals("var") && rightTerm.getType().equals("var")) {
                if (!leftTerm.getName().equals(rightTerm.getName())) {
                    substitutions.put(leftTerm.getName(), rightTerm);
                }
            } else {
                System.out.println("error");
            }
        }
        return substitutions;
    }

    public CustomPair<Disjunct, Map<String, Term>> unificateDisjunct(Disjunct left, Disjunct right) {
        List<Atom> result = new ArrayList<>(left.getArgs());
        result.addAll(right.getArgs());

        Map<String, Term> globalSubstitutions = new HashMap<>();

        int unificationCount = 0;
        int localUnificationCount = 1;
        while (localUnificationCount > 0) {
            localUnificationCount = 0;

            for (int i = 0; i < result.size(); i++) {
                Atom leftAtom = result.get(i);
                for (int j = i + 1; j < result.size(); j++) {
                    Atom rightAtom = result.get(j);
                    System.out.println("Атом = " + leftAtom);
                    System.out.println("Атом = " + rightAtom);
                    /* если одинаковые имена, то унифицируем атомы */
                    if (leftAtom.getName().equals(rightAtom.getName())) {
                        if (leftAtom.isSign() == rightAtom.isSign()) {
                            Map<String, Term> substitutions = unificateAtoms(leftAtom, rightAtom);
                            if (substitutions == null) {
                                System.out.println("Подстановка не выполнена");
                            } else {
                                System.out.println("Подстановка: " + substitutions);
                            }

                            if (substitutions != null && substitutions.isEmpty()) {
                                result.remove(rightAtom); // удаляем
                            }
                            continue;
                        }

                        /* если знаки разные, то */
                        Map<String, Term> substitutions = unificateAtoms(leftAtom, rightAtom);
                        if (substitutions == null) {
                            System.out.println("Подстановка не выполнена");
                            continue;
                        } else {
                            System.out.println("Подстановка: " + substitutions);
                        }

                        result.remove(leftAtom);
                        result.remove(rightAtom);

                        for (String key : substitutions.keySet()) {
                            globalSubstitutions.put(key, substitutions.get(key));
                        }
                        System.out.println("globalSubstitutions " + globalSubstitutions);

                        // Применение подстановки ко всем оставшимся атомам
                        for (Atom atom : result) {
                            for (Term term : atom.getArgs()) {
                                for (String key : substitutions.keySet()) {
                                    if (key.equals(term.getName())) {
                                        // подставляем в термы
                                        term.setName(substitutions.get(key).getName());
                                        term.setType(substitutions.get(key).getType());
                                        if ("const".equals(substitutions.get(key).getType())) {
                                            term.setValue(substitutions.get(key).getValue());
                                        }
                                    }
                                }
                            }
                        }

                        localUnificationCount++;
                        break;
                    } else {
                        System.out.println("Атомы не унифицируются");
                    }
                }
            }
            unificationCount += localUnificationCount;
        }

        if (unificationCount == 0) {
            return null;
        }

        return new CustomPair<>(new Disjunct(result), globalSubstitutions);
    }
}
