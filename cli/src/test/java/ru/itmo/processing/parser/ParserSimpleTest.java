package ru.itmo.processing.parser;

import org.junit.jupiter.api.Assertions;
import ru.itmo.processing.commands.ExitCommand;
import ru.itmo.processing.commands.ExternalCommand;
import ru.itmo.processing.commands.ICommand;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserSimpleTest {

    @org.junit.jupiter.api.Test
    void parse() {
        System.out.println("--Test tokens--");
        IParser p = new ParserSimple();
        String test_case = "ls | exit";
        List<ICommand> result = p.parse(test_case);
        assertInstanceOf(ExternalCommand.class, result.get(0));
        assertInstanceOf(ExitCommand.class, result.get(1));

    }
}