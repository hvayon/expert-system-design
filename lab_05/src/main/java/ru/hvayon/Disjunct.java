package ru.hvayon;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Disjunct {

    private List<Atom> args;

    public Disjunct(List<Atom> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        if (args.isEmpty()) {
            return "Пусто";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            result.append(args.get(i).toString());
            if (i < args.size() - 1) {
                result.append(" | ");
            }
        }
        return result.toString();
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

            atomList.add(new Atom(atomName, terms, isPositive));
        }

        return new Disjunct(atomList);
    }
}
