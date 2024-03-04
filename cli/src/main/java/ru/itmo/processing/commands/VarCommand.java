package ru.itmo.processing.commands;

import ru.itmo.streams.Stream;

import java.util.Map;

public class VarCommand implements ICommand, IChangeContext {
    final private String name;
    final private String value;

    public VarCommand(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(Stream stream1) {
        // TODO: Do nothing :)
    }

    @Override
    public void modifyContext(Map<String, String> agrs) {
        agrs.put(name, value);
    }
}
