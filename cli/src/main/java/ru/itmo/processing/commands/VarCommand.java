package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;

public class VarCommand implements ICommand {
    final private String name;
    final private String value;

    public VarCommand(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(Stream stream1) {

    }
}