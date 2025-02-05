package ru.hvayon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Atom {
    /* имя */
    private String name;

    /* знак (положительный или отрицательный) */
    private boolean sign;

    @Override
    public String toString() {
        if (!sign) {
            return "¬" + name;
        }
        return name;
    }
}
