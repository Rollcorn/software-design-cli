package ru.itmo.processing.parser;

import ru.itmo.processing.commands.EchoCommand;
import ru.itmo.processing.commands.ExitCommand;
import ru.itmo.processing.commands.ICommand;
import ru.itmo.processing.commands.PwdCommand;

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

            // Handle start and end of quotes
            if (currentChar == '\'' && !inDoubleQuotes) {
                inQuotes = !inQuotes;
            } else if (currentChar == '"' && !inQuotes) {
                inDoubleQuotes = !inDoubleQuotes;
            }

            // If we're in quotes, add the character to the current token
            if (inQuotes || inDoubleQuotes) {
                tokenBuilder.append(currentChar);
            }
            // If we're not in quotes and encounter a space, add the token to the list
            else if (currentChar == ' ' && !tokenBuilder.isEmpty()) {
                tokens.add(tokenBuilder.toString());
                tokenBuilder.setLength(0); // Reset the token builder
            }
            // If we're not in quotes and the character is not a space, add it to the token
            else if (currentChar != ' ') {
                tokenBuilder.append(currentChar);
            }
        }

        // Add the last token if it's not empty
        if (!tokenBuilder.isEmpty()) {
            tokens.add(tokenBuilder.toString());
        }

        return tokens;
    }

    @Override
    public List<ICommand> parse(String command) {
        List<String> tokens = tokenizeString(command);

        List<List<String>> token_groups = new ArrayList<>();
        List<String> currentGroup = new ArrayList<>();

        for (String token : tokens) {
            if ("|".equals(token)) {
                if (!currentGroup.isEmpty()) {
                    token_groups.add(currentGroup);
                    currentGroup = new ArrayList<>();
                }
            } else {
                currentGroup.add(token);
            }
        }

        if (!currentGroup.isEmpty()) {
            token_groups.add(currentGroup);
        }

        List<ICommand> commands = new ArrayList<>();

        for (List<String> group : token_groups) {
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
                    break;
                case "cat":
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

