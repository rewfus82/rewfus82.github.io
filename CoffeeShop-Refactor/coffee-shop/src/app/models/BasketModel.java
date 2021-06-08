package app.models;

import java.util.ArrayList;

public class BasketModel {
    private ArrayList<MenuItemModel> basket = new ArrayList<MenuItemModel>();
    private double total;
    private String summary;

    public void add(MenuItemModel arg) {
        this.basket.add(arg);
    }

    public void add(DrinkModel arg) {
        this.basket.add(arg);
    }

    public void add(FoodModel arg) {
        this.basket.add(arg);
    }

    public void remove(int arg) {
        this.basket.remove(arg);
    }

    public double getTotal() {
        basket.forEach(item -> {
            total += item.price;
        });
        return this.total;
    }

    public int getCount() {
        return basket.size();
    }

    public String summary() {
        this.summary = "";
        basket.forEach(item -> {
            summary += item.getAll() + "%n";
        });
        return this.summary;
    }

}
