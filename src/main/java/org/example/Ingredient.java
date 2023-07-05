package org.example;

import org.example.database.DataTransferObject;

import java.util.Objects;

public class Ingredient implements DataTransferObject {
    private final int ingredientId;
    private final String ingredient;
    private final int mealId;

    private static int nextIngredientId = 1;

    public Ingredient(String ingredient, int meal_Id) {
        this(nextIngredientId, ingredient, meal_Id);
    }

    public Ingredient(int ingredientId, String ingredient, int mealId) {
        this.ingredientId = ingredientId;
        this.ingredient = ingredient;
        this.mealId = mealId;
        incrementNextIngredientId(ingredientId);
    }

    @Override
    public int getId() {
        return ingredientId;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getMealId() {
        return mealId;
    }

    static private void incrementNextIngredientId(int ingredientId) {
        if (ingredientId >= nextIngredientId) {
            nextIngredientId = ingredientId + 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return ingredientId == that.ingredientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId);
    }

    @Override
    public String toString() {
        return ingredient;
    }
}
