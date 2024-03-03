package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;

import java.io.IOException;

public interface ICommand {
    void execute(Stream stream);
}
