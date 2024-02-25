package ru.itmo.processing.parser;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubstituteSimple implements ISubstitute{
    @Override
    public String substitute (String input, Map<String, String> replacements) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("\\$(\\w*)(?!([^']*'[^']*')*[^']*'[^']*$)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = replacements.get(key);
            matcher.appendReplacement(result, Matcher.quoteReplacement(Objects.requireNonNullElse(replacement, "")));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
