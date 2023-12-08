package FeastList.orders;

public record OrderCost(double mealsCost,double deliveryCost) {
    public double totalCost(){
        return mealsCost+deliveryCost;
    }
}
