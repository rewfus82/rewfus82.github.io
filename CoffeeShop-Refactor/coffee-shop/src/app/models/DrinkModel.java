package app.models;

public class DrinkModel extends MenuItemModel{
    final String type = "drink";
    protected boolean caffeine = false;

    public DrinkModel() {
        super();
        this.setID();
    }

    public DrinkModel(String name, String description, double cost) {
        super(name, description, cost);
        this.setID();
    }

    public void setID() {
        this.ID = "D-" + idg.generate();
    }
    



}