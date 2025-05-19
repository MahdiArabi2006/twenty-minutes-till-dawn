package io.github.some_example_name.model;

import java.util.Locale;

public enum Language {
    ENGLISH("English", Locale.ENGLISH),
    FRENCH("France", Locale.FRENCH);

    private final String name;
    private final Locale locale;

    Language(String name, Locale locale) {
        this.name = name;
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public Locale getLocale() {
        return locale;
    }
}
