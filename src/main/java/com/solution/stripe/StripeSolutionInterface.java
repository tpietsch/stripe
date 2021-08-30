package com.solution.stripe;

import java.util.List;

public interface StripeSolutionInterface {
    public List<String> parsedLanguageHeaders(String headerLanguages, List<String> serverAccepted);
}
