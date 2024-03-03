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

        String input1 = "$a | $c";
        String result1 = substitute.substitute(input1, context);
        Assertions.assertEquals(result1, "pwd | cat");

        String input2 = "Empty str >$b";
        String result2 = substitute.substitute(input2, context);
        Assertions.assertEquals(result2, "Empty str >");
    }
}