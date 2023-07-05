package org.example;

import org.example.database.DataTransferObject;

import java.util.Objects;

public class Meal implements DataTransferObject {
    private final int mealId;
    private final Category category;
    private final String meal;

    private static int nextMealId = 1;

    public Meal(Category category, String meal) {
        this(nextMealId, category, meal);
    }

    public Meal(int mealId, Category category, String meal) {
        this.mealId = mealId;
        this.category = category;
        this.meal = meal;
        incrementNextMealId(mealId);
    }

    private static void incrementNextMealId(int mealId) {
        if (mealId >= nextMealId) {
            nextMealId = mealId + 1;
        }
    }

    @Override
    public int getId() {
        return mealId;
    }

    public Category getCategory() {
        return category;
    }

    public String getMeal() {
        return meal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return mealId == meal.mealId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId);
    }

    // TODO
    @Override
    public String toString() {
        return meal;
    }

}
