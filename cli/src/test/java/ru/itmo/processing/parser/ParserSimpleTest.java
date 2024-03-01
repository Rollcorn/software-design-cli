package ru.itmo.processing.parser;

import org.junit.jupiter.api.Test;
import ru.itmo.processing.commands.EchoCommand;
import ru.itmo.processing.commands.ExitCommand;
import ru.itmo.processing.commands.ExternalCommand;
import ru.itmo.processing.commands.ICommand;
import ru.itmo.processing.commands.PwdCommand;
import ru.itmo.processing.commands.VarCommand;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


class ParserSimpleTest {

    @Test
    void parse() {
        IParser p = new ParserSimple();

        String test_case = "ls | exit";
        List<ICommand> result = p.parse(test_case);
        assertInstanceOf(ExternalCommand.class, result.get(0));
        assertInstanceOf(ExitCommand.class, result.get(1));

        test_case = "a=112";
        result = p.parse(test_case);
        assertInstanceOf(VarCommand.class, result.get(0));

        test_case = "echo \"ls\" | pwd";
        result = p.parse(test_case);
        assertInstanceOf(EchoCommand.class, result.get(0));
        assertInstanceOf(PwdCommand.class, result.get(1));
    }
}