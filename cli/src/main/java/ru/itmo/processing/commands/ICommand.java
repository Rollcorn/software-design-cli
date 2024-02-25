package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;

import java.io.IOException;

public interface ICommand {
    // мб использовать стандартный интерфейс Runnable?
    void execute(Stream stream) throws IOException;
}
