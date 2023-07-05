package org.example;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public enum Category {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner");

    private static final Category[] ENUMS = Category.values();

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getValue() {
        return ordinal() + 1;
    }

    public static Category of(int mealCategory) {
        if (mealCategory < 1 || mealCategory > 3) {
            throw new RuntimeException("Invalid value for mealCategory: " + mealCategory);
        }
        return ENUMS[mealCategory - 1];
    }

    public static String getCategoriesString() {
        return Arrays.stream(Category.values())
                .map(Objects::toString)
                .map(String::toLowerCase)
                .collect(Collectors.joining(", "));
    }

}
