package ru.itmo.processing.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SubstituteSimpleTest {

    @Test
    void substitute() {
        ISubstitute substitute = new SubstituteSimple();
        Map<String, String> context = new HashMap<>();
        context.put("a", "pwd");
        context.put("c", "cat");

        String input = "$a | $c";
        String result = substitute.substitute(input, context);
        Assertions.assertEquals(result, "pwd | cat");
    }
}