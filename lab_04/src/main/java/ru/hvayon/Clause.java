package ru.hvayon;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Clause {
    List<Atom> atoms = new ArrayList<>();

    public Clause(List<Atom> atoms) {
        this.atoms.addAll(atoms);
    }

    /*  Этот метод, возвращает новые экземпляр дизъюнктов,
        в котором удаляются повторяющиеся атомы, или пустой дизъюнкт в случае если
        внутри него есть пара типа: ~A, A (истина).
     */
    public Clause absorb() {
        Clause absorbed = new Clause();
        // хранилище для результата
        Map<String, Boolean> processedAtoms = new HashMap<>();

        for (Atom atom: atoms) {
            // Если имя атома совпало с именем проверенного атома,
            if (processedAtoms.containsKey(atom.getName())) {
                Boolean sign = processedAtoms.get(atom.getName());
                if (!atom.isSign() == (sign)) {
                    // но знаки у них разные — значит, он просто 1, поэтому возвращаем пустой
                    return new Clause();
                }
                // Если знаки и имена совпали — не добавляем этот атом в результат.
                continue;
            }
            // Если такого атома не было в хранилище, то добавляем его в туда.
            absorbed.addAtom(atom);
            processedAtoms.put(atom.getName(),atom.isSign());
        }
        return absorbed;
    }

    /* метод для добавления атомов в дизъюнкт */
    public void addAtom(Atom atom) {
        this.getAtoms().add(atom);
    }

    @Override
    public String toString() {
        return atoms.toString();
    }
}
