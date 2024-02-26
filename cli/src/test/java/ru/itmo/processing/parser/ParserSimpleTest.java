package ru.itmo.processing.parser;

import ru.itmo.processing.commands.ICommand;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserSimpleTest {

    @org.junit.jupiter.api.Test
    void parse() {
        System.out.println("--Test tokens--");
        String[] cases = {
                "pwd | cat test.txt | echo \"hello\"",
                "echo '|' | cat \"test.txt\""
        };
        IParser p = new ParserSimple();
        for (String test : cases){
            System.out.println("Input: " + test);
            System.out.println("Result:");
            List<ICommand> res = p.parse(test);


        }
    }
}