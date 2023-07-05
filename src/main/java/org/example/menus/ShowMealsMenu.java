package org.example.menus;

import org.example.Category;
import org.example.Ingredient;
import org.example.Meal;
import org.example.database.DataAccess;

import java.util.*;

public class ShowMealsMenu {

    public void showMealsByCategory() {
        Category category = getMealCategoryFromKeyboard();
        Map<Meal, List<Ingredient>> mealsAndIngredients = new DataAccess().getMealsAndIngredientsByCategory(category);
        printMeals(mealsAndIngredients);
    }

    private Category getMealCategoryFromKeyboard() {
        Scanner keyboard = new Scanner(System.in);
        System.out.printf("Which category do you want to print (%s)?\n", Category.getCategoriesString());
        String category = keyboard.nextLine().trim().toUpperCase();
        while (isNotValidMealCategory(category)) {
            System.out.printf("Wrong meal category! Choose from: %s.", Category.getCategoriesString());
            category = keyboard.nextLine().trim().toUpperCase();
        }
        return Category.valueOf(category.toUpperCase());
    }

    private boolean isNotValidMealCategory(String category) {
        return Arrays.stream(Category.values())
                .map(Objects::toString)
                .noneMatch(str -> str.equals(category));
    }

    private void printMeals(Map<Meal, List<Ingredient>> mealsAndIngredients) {
        if (mealsAndIngredients.isEmpty()) {
            System.out.println("No meals found.");
        } else {
            Category category = mealsAndIngredients.keySet().stream().toList().get(0).getCategory();
            System.out.printf("Category: %s\n\n", category);
            mealsAndIngredients.forEach((meal, ingredients) -> {
                System.out.printf("Name: %s\n", meal.getMeal());
                System.out.println("Ingredients:");
                ingredients.forEach(System.out::println);
                System.out.println();
            });
        }
    }
}
