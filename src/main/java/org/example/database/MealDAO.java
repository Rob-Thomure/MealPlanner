package org.example.database;

import org.example.Category;
import org.example.Meal;
import org.example.database.DaoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealDAO implements DaoInterface<Meal> {
    private final Connection connection;

    private static final String CREATE_MEALS_TABLE  = "CREATE TABLE IF NOT EXISTS meals (meal_Id integer PRIMARY KEY, " +
            "category varchar(20),  meal varchar(20))";
    private static final String INSERT = "INSERT INTO meals (meal_Id, category, meal) VALUES(?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM meals ORDER BY meal_Id";
    private static final String SELECT_BY_CATEGORY = "SELECT * FROM meals WHERE category = ? ORDER BY meal_Id";
    private static final String SELECT_BY_ID = "SELECT * FROM meals WHERE meal_Id = ?";
    private static final String UPDATE = "UPDATE meals SET category = ?, meal = ? WHERE meal_Id = ?";
    private static final String DELETE = "DELETE FROM meals WHERE meal_Id = ?";

    public MealDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_MEALS_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Meal findById(int id) {
        Meal meal = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int meal_Id = resultSet.getInt("meal_Id");
                Category category = Category.valueOf(resultSet.getString("category").toUpperCase()) ;
                String mealName = resultSet.getString("meal");
                meal = new Meal(meal_Id, category, mealName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meal;
    }

    @Override
    public List<Meal> findAll() {
        List<Meal> meals = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int meal_Id = resultSet.getInt("meal_Id");
                Category category = Category.valueOf(resultSet.getString("category").toUpperCase()) ;
                String mealName = resultSet.getString("meal");
                Meal meal = new Meal(meal_Id, category, mealName);
                meals.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }

    @Override
    public void update(Meal dto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, dto.getCategory().toString().toLowerCase());
            preparedStatement.setString(2, dto.getMeal());
            preparedStatement.setInt(3, dto.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Meal dto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setInt(1, dto.getId());
            preparedStatement.setString(2, dto.getCategory().toString().toLowerCase());
            preparedStatement.setString(3, dto.getMeal());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Meal dto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, dto.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Meal> findAllByCategory(Category category) {
        List<Meal> meals = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_CATEGORY)) {
            statement.setString(1, category.toString().toLowerCase());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int meal_Id = resultSet.getInt("meal_Id");
                String mealName = resultSet.getString("meal");
                Meal meal = new Meal(meal_Id, category, mealName);
                meals.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }
}
