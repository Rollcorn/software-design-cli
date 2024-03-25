package ru.itmo.processing.commands;

import java.util.Map;

public interface IChangeContext {
    void modifyContext(Map<String, String> agrs);
}
