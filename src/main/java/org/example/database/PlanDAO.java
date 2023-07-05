package org.example.database;

import org.example.Category;
import org.example.Plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO implements DaoInterface<Plan> {
    private final Connection connection;

    private static final String CREATE_PLAN_TABLE = "CREATE TABLE IF NOT EXISTS plan " +
            "(plan_Id integer PRIMARY KEY, " +
            "option varchar(20), " +
            "category varchar(20), " +
            "meal_Id integer)";
    private static final String INSERT = "INSERT INTO plan (plan_Id, option, category, meal_Id) VALUES(?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM plan";
    private static final String SELECT_BY_ID = "SELECT * FROM plan WHERE plan_Id = ?";
    private static final String SELECT_BY_OPTION_AND_CATEGORY = "SELECT * FROM plan WHERE option = ? AND category = ?";
    private static final String UPDATE = "UPDATE plan SET option = ?, category = ?, meal_Id = ? WHERE plan_Id = ?";
    private static final String DELETE = "DELETE FROM plan WHERE plan_Id = ?";
    private static final String DELETE_ALL = "DELETE FROM plan";


    public PlanDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PLAN_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Plan findById(int id) {
        Plan plan = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int planId = resultSet.getInt("plan_Id");
                DayOfWeek option = DayOfWeek.valueOf(resultSet.getString("option").toUpperCase());
                Category category = Category.valueOf(resultSet.getString("category").toUpperCase());
                int mealId = resultSet.getInt("meal_Id");
                plan = new Plan(planId, option, category, mealId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plan;
    }

    public Plan findByCategoryAndOption(DayOfWeek option, Category category) {
        Plan plan = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_OPTION_AND_CATEGORY)) {
            preparedStatement.setString(1, option.toString().toLowerCase());
            preparedStatement.setString(2, category.toString().toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int planId = resultSet.getInt("plan_Id");
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(resultSet.getString("option").toUpperCase());
                Category mealCategory = Category.valueOf(resultSet.getString("category").toUpperCase());
                int mealId = resultSet.getInt("meal_Id");
                plan = new Plan(planId, dayOfWeek, mealCategory, mealId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plan;
    }

    @Override
    public List<Plan> findAll() {
        List<Plan> plans = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int planId = resultSet.getInt("plan_Id");
                DayOfWeek option = DayOfWeek.valueOf(resultSet.getString("option").toUpperCase());
                Category category = Category.valueOf(resultSet.getString("category").toUpperCase());
                int mealId = resultSet.getInt("meal_Id");
                plans.add(new Plan(planId, option, category, mealId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }

    @Override
    public void update(Plan dto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, dto.getOption().toString().toLowerCase());
            preparedStatement.setString(2, dto.getCategory().toString().toLowerCase());
            preparedStatement.setInt(3, dto.getMealId());
            preparedStatement.setInt(4, dto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Plan dto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setInt(1, dto.getId());
            preparedStatement.setString(2, dto.getOption().toString().toLowerCase());
            preparedStatement.setString(3, dto.getCategory().toString().toLowerCase());
            preparedStatement.setInt(4, dto.getMealId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Plan dto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, dto.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
