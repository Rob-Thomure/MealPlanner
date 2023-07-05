package org.example.menus;

import org.example.MainMenuChoices;
import org.example.database.DataAccess;
import org.example.database.DbTablesCreator;

import java.util.*;
import java.util.stream.Collectors;

public class MainMenu {
    private boolean exit;

    public MainMenu() {
        exit = false;
    }

    public void runMainMenu() {
        new DbTablesCreator().createDbTablesIfNotExit();
        new DataAccess().setTableStaticIds();
        while (!exit) {
            String menuChoice = getMenuChoiceFromUser();
            executeMenuChoice(menuChoice);
        }
    }

    private void executeMenuChoice(String menuChoice) {
        switch (menuChoice) {
            case "add" -> new AddMealMenu().addMeal();
            case "show" -> new ShowMealsMenu().showMealsByCategory();
            case "plan" -> new PlanMenu().makePlan();
            case "exit" -> {
                exit = true;
                System.out.println("Bye!");
            }
            case "save" -> new SaveMenu().save();
            default -> System.out.println("Invalid choice");
        }
    }

    private String getMenuChoiceFromUser() {
        String menuChoice = "";
        while (isNotValidMenuChoice(menuChoice)) {
            System.out.printf("What would you like to do (%s)?\n", getMnuChoiceString());
            menuChoice = new Scanner(System.in).nextLine().trim().toLowerCase();
        }
        return menuChoice;
    }

    private boolean isNotValidMenuChoice(String menuChoice) {
        return Arrays.stream(MainMenuChoices.values())
                .map(Objects::toString)
                .map(String::toLowerCase)
                .noneMatch(str -> str.equals(menuChoice));
    }

    private String getMnuChoiceString() {
        return Arrays.stream(MainMenuChoices.values())
                .map(Objects::toString)
                .map(String::toLowerCase)
                .collect(Collectors.joining(", "));
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.getMenuChoiceFromUser();
    }

}
