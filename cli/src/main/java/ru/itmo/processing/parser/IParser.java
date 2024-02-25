package ru.itmo.processing.parser;

import ru.itmo.processing.commands.ICommand;

import java.util.List;

public interface IParser {
    List<ICommand> parse(String inputString);
}
