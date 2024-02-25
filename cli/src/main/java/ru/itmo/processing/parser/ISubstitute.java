package ru.itmo.processing.parser;

import java.util.Map;

public interface ISubstitute {
    String substitute (String command, Map<String, String> replacements);
}

