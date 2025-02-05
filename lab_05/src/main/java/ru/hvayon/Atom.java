package ru.hvayon;

import lombok.Data;

import java.util.List;

@Data
class Atom {
    private String name;
    private boolean sign;
    /* массив термов */
    private List<Term> args;

    public Atom(String name, List<Term> args, boolean sign) {
        this.name = name;
        this.args = args;
        this.sign = sign;
    }

    @Override
    public String toString() {
        String prefix = sign ? "" : "~";
        StringBuilder argsString = new StringBuilder();

        for (int i = 0; i < args.size(); i++) {
            argsString.append(args.get(i).toString());
            if (i < args.size() - 1) {
                argsString.append(", ");
            }
        }

        return String.format("%s%s(%s)", prefix, name, argsString);
    }
}