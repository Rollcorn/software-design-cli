package ru.itmo.processing.parser;

import ru.itmo.processing.commands.ICommand;

import java.util.List;
import java.util.Map;

public interface IParser {

    List<ICommand> parse();

    void readParam(String inputString);

    void substitute(String inputString, Map<String, String> globalArgs);
}
