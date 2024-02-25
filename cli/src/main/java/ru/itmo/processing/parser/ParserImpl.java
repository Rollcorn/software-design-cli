package ru.itmo.processing.parser;

import ru.itmo.processing.commands.ICommand;

import java.util.List;
import java.util.Map;

public class ParserImpl implements IParser {
    @Override
    public List<ICommand> parse() {
        return null;
    }

    @Override
    public void readParam(String inputString) {

    }

    @Override
    public void substitute(String inputString, Map<String, String> globalArgs) {

    }
}
