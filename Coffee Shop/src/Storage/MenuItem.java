package Storage;

import java.util.Comparator;

public class MenuItem {
	private String itemID;
	private String name;
	private String description;
	private double price;
	

	public MenuItem(String name, String itemID, double price, String description) {
		this.itemID = itemID;
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public String getID() {
		return this.itemID;
	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.description;
	}

	public double getPrice() {
		return this.price;
	}

	public void setFree() {
		this.price = 0;
	}

}
