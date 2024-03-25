package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;

public interface ICommand {
    void execute(Stream stream);
}
