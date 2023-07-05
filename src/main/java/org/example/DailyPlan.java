package org.example;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DailyPlan {
    private final DayOfWeek dayOfWeek;
    private final String breakfast;
    private final String lunch;
    private final String dinner;

    public DailyPlan(DayOfWeek dayOfWeek, String breakfast, String lunch, String dinner) {
        this.dayOfWeek = dayOfWeek;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public String getDinner() {
        return dinner;
    }

    @Override
    public String toString() {
        return String.format("%s\n" +
                        "Breakfast: %s\n" +
                        "Lunch: %s\n" +
                        "Dinner: %s\n",
                dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US),
                breakfast,
                lunch,
                dinner);
    }
}
