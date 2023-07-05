package org.example;

import org.example.database.DataTransferObject;

import java.time.DayOfWeek;
import java.util.Objects;

public class Plan implements DataTransferObject {
    private final int planId;
    private final DayOfWeek option;
    private final Category category;
    private final int mealId;

    private static int nextPlanId = 1;

    public Plan(DayOfWeek option, Category category, int mealId) {
        this(nextPlanId, option, category, mealId);
    }

    public Plan(int planId, DayOfWeek option, Category category, int mealId) {
        this.planId = planId;
        this.option = option;
        this.category = category;
        this.mealId = mealId;
        incrementNextPlanId(planId);
    }

    private static void incrementNextPlanId(int planId) {
        if (planId >= nextPlanId) {
            nextPlanId = planId + 1;
        }
    }

    @Override
    public int getId() {
        return planId;
    }

    public DayOfWeek getOption() {
        return option;
    }

    public Category getCategory() {
        return category;
    }

    public int getMealId() {
        return mealId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return planId == plan.planId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId);
    }

    @Override
    public String toString() {
        return "Plan{" +
                "option='" + option + '\'' +
                ", category='" + category + '\'' +
                ", meal_Id=" + mealId +
                '}';
    }
}
