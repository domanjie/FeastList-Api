package FeastList.meals;

import FeastList.menuItem.MenuItem;

public record MealItem(MenuItem menuItem, int noOfPortion) {
    Double getPrice(){
        return menuItem.getPricePerPortion()*noOfPortion;
    };
}
