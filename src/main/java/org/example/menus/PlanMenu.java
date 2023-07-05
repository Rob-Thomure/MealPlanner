package org.example.menus;

import org.example.Category;
import org.example.Meal;
import org.example.Plan;
import org.example.database.DataAccess;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;

public class PlanMenu {

    public void makePlan() {
        List<Meal> meals = new DataAccess().getMeals();
        if (meals.isEmpty()) {
            System.out.println("There are no meals saved, save meals first");
        } else {
            new DataAccess().deletePlanDb();
            Arrays.asList(DayOfWeek.values()).forEach(day -> {
                System.out.println(day.getDisplayName(TextStyle.FULL, Locale.US));
                Arrays.asList(Category.values()).forEach(category -> {
                    List<Meal> categoryMeals = printMealNamesInCategory(category);
                    Meal meal = getMealChoiceFromKeyboard(day, category, categoryMeals);
                    Plan plan = new Plan(day, category, meal.getId());
                    new DataAccess().savePlanDb(plan);
                });
                printMealsAreSavedForDay(day);
            });
            List<String> plannedMealsList = getPlannedMealsList();
            printWeeklyPlan(plannedMealsList);
        }
    }

    private void printDay(int day) {
        System.out.println(DayOfWeek.of(day).getDisplayName(TextStyle.FULL, Locale.US));
    }

    private List<Meal> printMealNamesInCategory(Category category) {
        List<Meal> meals = new DataAccess().getMealsByCategory(category);
        meals.stream()
                .map(Meal::getMeal)
                .sorted()
                .forEach(System.out::println);
        return meals;
    }

    private Meal getMealChoiceFromKeyboard(DayOfWeek dayOfWeek, Category category, List<Meal> categoryMeals) {
        System.out.printf("Choose the %s for %s from the list above:\n",
                category.toString().toLowerCase(),
                dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US));
        Scanner keyboard = new Scanner(System.in);
        String mealName = keyboard.nextLine().trim();
        Optional<Meal> meal = getMealFromList(categoryMeals, mealName);
        while (meal.isEmpty()) {
            System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            mealName = keyboard.nextLine().trim();
            meal = getMealFromList(categoryMeals, mealName);
        }
        return meal.get();
    }

    private Optional<Meal> getMealFromList(List<Meal> meals, String mealName) {
        return meals.stream()
                .filter(meal -> meal.getMeal().toLowerCase().equals(mealName))
                .findAny();
    }

    private void printMealsAreSavedForDay(DayOfWeek dayOfWeek) {
        System.out.printf("Yeah! We planned the meals for %s.\n\n", dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US));
    }

    private void printWeeklyPlan(List<String> plannedMealsList) {
        ListIterator<String> iterator = plannedMealsList.listIterator();
        Arrays.asList(DayOfWeek.values()).forEach(day -> {
            System.out.printf("%s\n" +
                            "Breakfast: %s\n" +
                            "Lunch: %s\n" +
                            "Dinner: %s\n\n",
                    day.getDisplayName(TextStyle.FULL, Locale.US),
                    iterator.next(),
                    iterator.next(),
                    iterator.next());
        });
    }

    private List<String> getPlannedMealsList() {
        List<String> plans = new ArrayList<>();
        Arrays.asList(DayOfWeek.values()).forEach(option -> {
            Arrays.asList(Category.values()).forEach(category -> {
                Plan plan = new DataAccess().getPlanByOptionAndCategory(option, category);
                Meal meal = new DataAccess().getMealById(plan.getMealId());
                plans.add(meal.getMeal());

            });
        });
        return plans;
    }


}
