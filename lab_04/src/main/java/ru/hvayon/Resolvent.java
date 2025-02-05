package ru.hvayon;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Resolvent {

    public enum ResolventResult {
        OK,
        OPPOSITE_PAIR_NOT_FOUND,
        EMPTY_CLAUSE
    }

    private static Map<String, Boolean> makeClauseFastLookup(Clause clause) {
        return clause.getAtoms().stream()
                .collect(Collectors.toMap(
                        Atom::getName,
                        Atom::isSign,
                        (existingValue, newValue) -> existingValue, // если происходит столкновение ключей,
                                                                    // оставить старое значение
                        HashMap::new));
    }

    /*  Вспомогательная функция для CreateResolvent.
        Ищет имя атома из первого дизъюнкта, для которого существует атом,
        противоположный по знаку во втором дизъюнкте, иначе возвращает пустую строку */
    public static String findOppositeAtomName(Clause firstClause, Clause secondClause) {
        Map<String, Boolean> secondClauseFastLookup = makeClauseFastLookup(secondClause);

        return firstClause.getAtoms().stream()
                .filter(atom -> secondClauseFastLookup.containsKey(atom.getName())
                && !atom.isSign() == secondClauseFastLookup.get(atom.getName()))
                .findFirst()
                .map(Atom::getName)
                .orElse("");
    }

    private void addNonOppositeAtomsToResolvent(Clause clause, String oppositeAtomName, Clause resolvent) {
        clause.getAtoms().stream()
                .filter(atom -> !atom.getName().equals(oppositeAtomName))
                .forEach(resolvent::addAtom);
    }

    /* Добавляем в резольвенту все атомы, кроме атомов с противоположными знаками,
      которые были найдены на предыдущем шаге */
    private Clause buildResolvent(Clause firstDisjunct, Clause secondDisjunct, String oppositeAtomName) {
        Clause resolvent = new Clause();

        addNonOppositeAtomsToResolvent(firstDisjunct, oppositeAtomName, resolvent);
        addNonOppositeAtomsToResolvent(secondDisjunct, oppositeAtomName, resolvent);

        return resolvent;
    }

    /* Создание дизъюнкта, исключая из него контрарную пару типа: (~A, B) , (C, A) = (B, C)
        или получение пустого дизъюнкта. */
    public CustomPair<Clause, ResolventResult> createResolvent(Clause disjunct1, Clause disjunct2) {
        // приводим первый дизъюнкт к виду, где атомы не повторяются
        Clause firstAbsorbedDisjunct = disjunct1.absorb();
        Clause secondAbsorbedDisjunct = disjunct2.absorb();

        // если вернулся пустой дизъюнкт
        if (firstAbsorbedDisjunct.getAtoms().isEmpty() ||
                secondAbsorbedDisjunct.getAtoms().isEmpty()) {
            return new CustomPair<>(new Clause(), ResolventResult.EMPTY_CLAUSE);
        }

        String oppositeAtomName = findOppositeAtomName(firstAbsorbedDisjunct, secondAbsorbedDisjunct);

        if (oppositeAtomName.isEmpty()) {
            // противоположных пар не найдено
            return new CustomPair<>(new Clause(), ResolventResult.OPPOSITE_PAIR_NOT_FOUND);
        }

        // Формируем резолвенту, исключив противоположные атомы из двух дизъюнктов
        Clause resolvent = buildResolvent(firstAbsorbedDisjunct, secondAbsorbedDisjunct, oppositeAtomName);

        Clause absorbedResolvent = resolvent.absorb();

        ResolventResult result = absorbedResolvent.getAtoms().isEmpty()
                ? ResolventResult.EMPTY_CLAUSE
                : ResolventResult.OK;

        return new CustomPair<>(absorbedResolvent, result);
    }
}
