package ru.itmo.processing.parser;

import ru.itmo.processing.commands.ICommand;

import java.util.List;

public interface IParser {
    List<String> tokenizeString(String text);
    List<ICommand> parse(List<String> tokens);
}
