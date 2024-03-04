package ru.itmo.processing.parser;

import ru.itmo.processing.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserSimple implements IParser {
    private List<String> tokenizeString(String command) {
        List<String> tokens = new ArrayList<>();
        StringBuilder tokenBuilder = new StringBuilder();
        boolean inQuotes = false;
        boolean inDoubleQuotes = false;

        for (int i = 0; i < command.length(); i++) {
            char currentChar = command.charAt(i);

            if (currentChar == '\'' && !inDoubleQuotes) {
                inQuotes = !inQuotes;
            } else if (currentChar == '"' && !inQuotes) {
                inDoubleQuotes = !inDoubleQuotes;
            }

            if (inQuotes || inDoubleQuotes) {
                tokenBuilder.append(currentChar);
            }
            else if (currentChar == ' ' && !tokenBuilder.isEmpty()) {
                tokens.add(tokenBuilder.toString());
                tokenBuilder.setLength(0);
            }

            else if (currentChar != ' ') {
                tokenBuilder.append(currentChar);
            }
        }

        if (!tokenBuilder.isEmpty()) {
            tokens.add(tokenBuilder.toString());
        }

        return tokens;
    }

    @Override
    public List<ICommand> parse(String command) {
        List<List<String>> tokenGroups = new ArrayList<>();

        List<String> tokens = tokenizeString(command);
        List<String> currentGroup = new ArrayList<>();
        for (String token : tokens) {
            if ("|".equals(token)) {
                if (!currentGroup.isEmpty()) {
                    tokenGroups.add(currentGroup);
                    currentGroup = new ArrayList<>();
                }
            } else {
                currentGroup.add(token);
            }
        }

        if (!currentGroup.isEmpty()) {
            tokenGroups.add(currentGroup);
        }

        List<ICommand> commands = new ArrayList<>();

        for (List<String> group : tokenGroups) {
            switch (group.get(0)) {
                case "exit":
                    commands.add(new ExitCommand());
                    break;
                case "pwd":
                    commands.add(new PwdCommand());
                    break;
                case "echo":
                    commands.add(new EchoCommand(group.subList(1, group.size())));
                    break;
                case "wc":
                    // TODO: Add constructor without flags
                    commands.add(new WcCommand(group.subList(1, group.size())));
                    break;
                case "cat":
                    // TODO: Add constructor without flags
                    commands.add(new CatCommand(group.subList(1, group.size())));
                    break;
                default:
                    String regex = "(\\w+)=(\\w+)";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(command);
                    if (matcher.find()) {
                        String name = matcher.group(1);
                        String value = matcher.group(2);
                        commands.add(new VarCommand(name, value));
                    } else {
                        commands.add(new ExternalCommand(group.subList(1, group.size())));
                    }
                    break;
            }
        }


        return commands;
    }
}

