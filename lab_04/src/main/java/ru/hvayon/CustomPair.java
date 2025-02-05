package ru.hvayon;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomPair<Clause, ResolventResult> {
    private Clause resolvent;
    private ResolventResult resolventResult;
}
