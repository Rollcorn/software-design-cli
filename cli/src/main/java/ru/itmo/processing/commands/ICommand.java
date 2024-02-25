package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;

public interface ICommand {
    // мб использовать стандартный интерфейс Runnable?
    void execute(Stream stream1);
}
