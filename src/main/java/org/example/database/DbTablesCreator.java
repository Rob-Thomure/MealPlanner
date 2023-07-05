package org.example.database;

import java.sql.Connection;

public class DbTablesCreator {

    public void createDbTablesIfNotExit() {
        Connection connection = new DatabaseConnectionManager().getConnection();
        new MealDAO(connection).createTable();
        new IngredientDAO(connection).createTable();
        new PlanDAO(connection).createTable();
    }
}
