package app.models;

public class FoodModel extends MenuItemModel {

    final String type = "food";

    public FoodModel() {
        super();
        this.setID();
    }

    public FoodModel(String name, String description, double cost) {
        super(name, description, cost);
        this.setID();
    }

    public void setID() {
        this.ID = "F-" + idg.generate();
    }





}

