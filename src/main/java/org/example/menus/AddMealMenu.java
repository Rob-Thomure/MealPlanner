package org.example.menus;

import org.example.Category;
import org.example.database.DataAccess;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class AddMealMenu {

    public void addMeal() {
        Category category = getMealCategoryFromKeyboard();
        String mealName = getMealNameFromKeyboard();
        String[] ingredients = getIngredientsFromKeyboard();
        new DataAccess().saveMealDb(category, mealName, ingredients);
        System.out.println("The meal has been added!");
    }

    private Category getMealCategoryFromKeyboard() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        String mealCategory = keyboard.nextLine().trim().toLowerCase();
        while (isNotValidMealCategory(mealCategory)) {
            System.out.printf("Wrong meal category! Choose from: %s.\n", Category.getCategoriesString());
            mealCategory = keyboard.nextLine().trim().toLowerCase();
        }
        return Category.valueOf(mealCategory.toUpperCase());
    }

    private boolean isNotValidMealCategory(String mealCategory) {
        return !Arrays.stream(Category.values())
                .map(Objects::toString)
                .map(String::toLowerCase)
                .anyMatch(str -> str.equals(mealCategory));
    }

    private String getMealNameFromKeyboard() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Input the meal's name:");
        String mealName = keyboard.nextLine().trim();
        while (isWrongFormat(mealName)) {
            System.out.println("Wrong format. Use letters only!");
            mealName = keyboard.nextLine().trim();
        }
        return mealName;
    }

    // verifies there are no numbers and non-alphabet characters and not blank
    private boolean isWrongFormat(String mealName) {
        if (mealName.isBlank()) {
            return true;
        }
        for (Character ch : mealName.toCharArray()) {
            if (!Character.isLetter(ch) && !Character.isSpaceChar(ch)) {
                return true;
            }
        }
        return false;
    }

    private String[] getIngredientsFromKeyboard() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Input the ingredients:");
        String input = keyboard.nextLine().trim();
        while (input.endsWith(",") || isIngredientsArrayWrongFormat(input.split(","))) {
            System.out.println("Wrong format. Use letters only!");
            input = keyboard.nextLine().trim();
        }
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }

    private boolean isIngredientsArrayWrongFormat(String[] ingredients) {
        if (ingredients.length == 0) {
            return true;
        }
        for (String ingredient : ingredients) {
            if (isWrongFormat(ingredient)) {
                return true;
            }
        }
        return false;
    }
}
