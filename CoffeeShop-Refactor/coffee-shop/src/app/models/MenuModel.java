package app.models;

import java.util.ArrayList;

public class MenuModel{

        private ArrayList<FoodModel> foodList = new ArrayList<FoodModel>();
        private ArrayList<DrinkModel> drinkList = new ArrayList<DrinkModel>();
        private String drinkSummary;
        private String foodSummary;
        private String menuSummary;

        public void add(DrinkModel arg) {
            this.drinkList.add(arg);
        }
    
        public void add(FoodModel arg) {
            this.foodList.add(arg);
        }

        public String drinkSummary() {
            this.drinkSummary = "";
            drinkList.forEach(item -> {
                drinkSummary += item.getAll() + "%n";
            });
            return this.drinkSummary;
        }

        public String foodSummary() {
            this.foodSummary = "";
            foodList.forEach(item -> {
                foodSummary += item.getAll() + "%n";
            });
            return this.foodSummary;
        }

        public String menuSummary() {
            this.menuSummary = foodSummary() + drinkSummary();
            return this.menuSummary;
        }
        
}
