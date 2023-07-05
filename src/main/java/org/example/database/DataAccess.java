package org.example.database;

import org.example.Category;
import org.example.Ingredient;
import org.example.Meal;
import org.example.Plan;

import java.sql.Connection;
import java.time.DayOfWeek;
import java.util.*;

public class DataAccess {

    public void setTableStaticIds() {
        Connection connection = new DatabaseConnectionManager().getConnection();
        new MealDAO(connection).findAll();
        new IngredientDAO(connection).findAll();
        new PlanDAO(connection).findAll();
    }

    public void saveMealDb(Category category, String mealName, String[] ingredients) {
        Connection connection = new DatabaseConnectionManager().getConnection();
        Meal meal = new Meal(category, mealName);
        new MealDAO(connection).create(meal);
        Arrays.asList(ingredients).forEach(str -> {
            Ingredient ingredient = new Ingredient(str, meal.getId());
            new IngredientDAO(connection).create(ingredient);
        });
    }

    public Map<Meal, List<Ingredient>> getMealsAndIngredientsByCategory(Category category) {
        Connection connection = new DatabaseConnectionManager().getConnection();
        Map<Meal, List<Ingredient>> mealsAndIngredients = new LinkedHashMap<>();
        List<Meal> meals = new MealDAO(connection).findAllByCategory(category);
        meals.forEach(meal -> {
            List<Ingredient> ingredients = new IngredientDAO(connection).findAllByMealId(meal.getId());
            mealsAndIngredients.put(meal, ingredients);
        });
        return mealsAndIngredients;
    }

    public List<Meal> getMealsByCategory(Category category) {
        Connection connection = new DatabaseConnectionManager().getConnection();
        return new MealDAO(connection).findAllByCategory(category);
    }

    public Meal getMealById(int mealId) {
        Connection connection = new DatabaseConnectionManager().getConnection();
        return new MealDAO(connection).findById(mealId);
    }

    public void deletePlanDb() {
        Connection connection = new DatabaseConnectionManager().getConnection();
        new PlanDAO(connection).deleteAll();
    }

    public void savePlanDb(Plan plan) {
        Connection connection = new DatabaseConnectionManager().getConnection();
        new PlanDAO(connection).create(plan);
    }

    public Plan getPlanByOptionAndCategory(DayOfWeek option, Category category) {
        Connection connection = new DatabaseConnectionManager().getConnection();
        return new PlanDAO(connection).findByCategoryAndOption(option, category);
    }

    public List<Plan> getPlans() {
        Connection connection = new DatabaseConnectionManager().getConnection();
        return new PlanDAO(connection).findAll();
    }

    public List<Ingredient> getIngredientsByMealId(int mealId) {
        Connection connection = new DatabaseConnectionManager().getConnection();
        return new IngredientDAO(connection).findAllByMealId(mealId);
    }

    public List<Meal> getMeals() {
        Connection connection = new DatabaseConnectionManager().getConnection();
        return new MealDAO(connection).findAll();
    }
}
