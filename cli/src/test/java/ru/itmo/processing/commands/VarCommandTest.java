package ru.itmo.processing.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VarCommandTest {

    @Test
    void modifyContext() {
        Map<String, String> context = new HashMap<>();
        context.put("PATH", "a path");
        // Modifies existing
        VarCommand var1 = new VarCommand("PATH", "a new path");
        var1.modifyContext(context);
        Assertions.assertEquals(context.get("PATH"), "a new path");

        // Adds new
        VarCommand var2 = new VarCommand("a", "42");
        var2.modifyContext(context);
        Assertions.assertEquals(context.get("a"), "42");
    }
}