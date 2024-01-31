package FeastList.orders;

import FeastList.meals.Meal;

public record OrderItem(Meal meal, int mealAmount) {
    double getOrderItemPrice(){
        return meal.getPrice()*2;
    }
}
