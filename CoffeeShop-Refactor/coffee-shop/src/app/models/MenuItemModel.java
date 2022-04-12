package app.models;

import app.models.EnumBody.MenuItem;
import app.services.IDGenerator;

public class MenuItemModel {

    protected String name = null;
    protected String description = null;
    protected double price = 0.00;
    protected String ID = null;
    protected String type = null;
    protected IDGenerator idg = new IDGenerator();

    public MenuItemModel() { }
    public MenuItemModel(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    // SET METHODS
    public void set(MenuItem field, String arg) {
        switch (field) {
            case NAME: {
                this.name = arg;
                break;
            }
            case DESC: {
                this.name = arg;
                break;
            }
        }
    }
    public void setPrice(double arg) { this.price = arg; }
    public void setID() { this.ID = "M-" + idg.generate(); }
    public void setAll(String name, String description, double price, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.setID();
    }

    // GET METHODS
    public String get(MenuItem field) {
        switch (field) {
            case NAME: {
                return this.name;
            }
            case DESC: {
                return this.description;
            }
            default:
                return null;
        }
    }
    public double getPrice() { return this.price; }
    public String getId() { return this.ID; }
    public String getAll() {
        String price = Double.toString(this.price);
        String str = "ID#: " + this.ID + " / " + this.name + ": " + this.description + " / Price: " + price;
        return str;
    }
    public MenuItemModel getIt() { return this; }

}
