package org.example.menus;

import org.example.Ingredient;
import org.example.Meal;
import org.example.Plan;
import org.example.database.DataAccess;
import org.example.database.DatabaseConnectionManager;
import org.example.database.IngredientDAO;
import org.example.database.MealDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SaveMenu {

    public void save() {
        List<Plan> plans = new DataAccess().getPlans();
        if (plans.isEmpty()) {
            System.out.println("Unable to save. Plan your meals first.");
        } else {
            Map<String, Integer> shoppingList = createShoppingList(plans);
            saveToFile(shoppingList);
        }
    }

    private Map<String, Integer> createShoppingList(List<Plan> plans) {
        Connection connection = new DatabaseConnectionManager().getConnection();
        Map<String, Integer> shoppingList = new LinkedHashMap<>();
        plans.forEach(plan -> {
            // ***************************************** caused excessive connections
            //Meal meal = new DataAccess().getMealById(plan.getMealId());
            Meal meal = new MealDAO(connection).findById(plan.getMealId());
            // ****************** caused excessive connections
            //List<Ingredient> ingredients = new DataAccess().getIngredientsByMealId(meal.getId());
            List<Ingredient> ingredients = new IngredientDAO(connection).findAllByMealId(meal.getId());
            ingredients.forEach(ingredient -> {
                if (shoppingList.containsKey(ingredient.getIngredient())) {
                    int count = shoppingList.get(ingredient.getIngredient());
                    shoppingList.put(ingredient.getIngredient(), ++count);
                } else {
                    shoppingList.put(ingredient.getIngredient(), 1);
                }
            });
        });
        return shoppingList;
    }

    private void saveToFile(Map<String, Integer> shoppingList) {
        System.out.println("Input a filename:");
        String fileName = new Scanner(System.in).nextLine().trim();
        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            shoppingList.forEach((ingredient, count) -> {
                printWriter.print(ingredient);
                if (count > 1) {
                    printWriter.printf(" x%d", count);
                }
                printWriter.println();
            });
            System.out.println("Saved!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
