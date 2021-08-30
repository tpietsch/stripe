package com.solution.stripe;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StripeSolution implements StripeSolutionInterface {
    private static final String WILDCARD_PATTER = "*";

    @Override
    //TODO ignore casing
    public List<String> parsedLanguageHeaders(String headerLanguages, List<String> serverAccepted) {
        List<String> parsedOutHeaders = parseHeader(headerLanguages);

        if (parsedOutHeaders.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> supportedLangs = new ArrayList<>();

        for (String langHeader : parsedOutHeaders) {
            if (langHeader.equals(WILDCARD_PATTER)) {
                supportedLangs.addAll(serverAccepted);
            } else if (isMissingRegion(langHeader)) {
                supportedLangs.addAll(serverAccepted.stream()
                        .filter(language -> language.startsWith(langHeader))
                        .collect(Collectors.toList()));
            } else {
                if (serverAccepted.contains(langHeader)) {
                    supportedLangs.add(langHeader);
                }
            }
        }

        return supportedLangs.stream().distinct().collect(Collectors.toList());
    }

    List<String> parseHeader(String headerLanguages) {
        String[] languageCodes = headerLanguages.split(",");
        return Arrays.asList(languageCodes).stream()
                .filter(header -> !header.isEmpty())
                .map(String::trim).collect(Collectors.toList());
    }

    boolean isMissingRegion(String headerLanguage) {
        return !headerLanguage.contains("-");
    }

}
