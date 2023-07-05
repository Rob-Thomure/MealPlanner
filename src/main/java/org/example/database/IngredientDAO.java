package org.example.database;

import org.example.Ingredient;
import org.example.database.DaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO implements DaoInterface<Ingredient> {
    private final Connection connection;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ingredients " +
            "(ingredient_Id integer PRIMARY KEY, ingredient varchar(20), meal_id integer)";
    private static final String INSERT = "INSERT INTO ingredients (ingredient_Id, ingredient, meal_Id) VALUES(?, ?, ?) ";
    private static final String SELECT_ALL = "SELECT * FROM ingredients";
    private static final String SELECT_BY_ID = "SELECT * FROM ingredients WHERE ingredient_Id = ?";
    private static final String SELECT_ALL_BY_MEAL_ID = "SELECT * FROM ingredients WHERE meal_Id = ? ORDER BY ingredient_Id";
    private static final String UPDATE = "UPDATE ingredients SET ingredient = ?, meal_Id = ? WHERE ingredient_Id = ?";
    private static final String DELETE = "DELETE FROM ingredients WHERE ingredient_Id = ?";

    public IngredientDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ingredient findById(int id) {
        Ingredient ingredient = null;
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String ingredientName = resultSet.getString("ingredient");
                int ingredient_Id = resultSet.getInt("ingredient_Id");
                int meal_Id = resultSet.getInt("meal_Id");
                ingredient = new Ingredient(ingredient_Id, ingredientName, meal_Id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredient;
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String ingredientName = resultSet.getString("ingredient");
                int ingredient_Id = resultSet.getInt("ingredient_Id");
                int meal_Id = resultSet.getInt("meal_Id");
                Ingredient ingredient = new Ingredient(ingredient_Id, ingredientName, meal_Id);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    @Override
    public void update(Ingredient dto) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, dto.getIngredient());
            statement.setInt(2, dto.getMealId());
            statement.setInt(3, dto.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Ingredient dto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setInt(1, dto.getId());
            preparedStatement.setString(2, dto.getIngredient());
            preparedStatement.setInt(3, dto.getMealId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Ingredient dto) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, dto.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ingredient> findAllByMealId(int mealId) {
        List<Ingredient> ingredients = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_MEAL_ID)) {
            preparedStatement.setInt(1, mealId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String ingredientName = resultSet.getString("ingredient");
                int ingredient_Id = resultSet.getInt("ingredient_Id");
                Ingredient ingredient = new Ingredient(ingredient_Id, ingredientName, mealId);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {

        }
        return ingredients;
    }
}
