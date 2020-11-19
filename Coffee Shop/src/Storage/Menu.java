package Storage;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

	private String itemID;
	private String name;
	private String description;
	private double price;
	private ArrayList<MenuItem> menuList = new ArrayList<>();

	public Menu() {
	}

	public void add(String itemID, String name, Double price, String description) {
		MenuItem item = new MenuItem(itemID, name, price, description);
		this.menuList.add(item);
	}
	
	public void addItem(MenuItem item) {
		this.menuList.add(item);
	}

	public void manualAdd(Scanner in, boolean run) {
		String input;
		System.out.print("Please enter the item ID:");
		itemID = in.nextLine();
		if(itemID.equals("")) {
			System.out.print("No ID entered. Exiting item creation.\n");
			return;
		}
		while (this.compareID(itemID)) {
			System.out.print("That ID is already in use.\nPlease enter the item ID:");
			itemID = in.nextLine();
		}
		System.out.print("Please enter the item name: ");
		name = in.nextLine();
		if (name.equals("")) {
			System.out.print("No name entered. Exiting item creation.\n");
			return;
		}
		System.out.print("Please enter the item price (0.00): ");
		price = 0;
		price = Double.parseDouble(in.nextLine());
		if (price == 0) {
			System.out.print("No price entered. Exiting item creation.\n");
			return;
		}
		System.out.print("Please enter the item description (max 55 characters): ");
		description = in.nextLine();
		if (description.equals("")) {
			System.out.print("No description entered. Exiting item creation.\n");
			return;
		}
		System.out.println();
		System.out.printf("%-7s", itemID);
		System.out.printf("%-25s", name.toUpperCase());
		System.out.printf("%-60s", description);
		System.out.print("$");
		System.out.printf("%.2f %n", price);
		while (run) {
			System.out.print("Add item? (Y/N):");
			input = in.nextLine().toUpperCase();
			if (input.equals("Y")) {
				this.add(name, itemID, price, description);
				break;
			} else if (input.equals("N")) {
				System.out.println("Item not saved.");
				break;
			}
		}

	}

	public void fileAdd(Scanner in) {

		while (in.hasNext()) {
			itemID = in.nextLine();
			name = in.nextLine();
			description = in.nextLine();
			price = Float.parseFloat(in.nextLine());
			MenuItem item = new MenuItem(name, itemID, price, description);
			menuList.add(item);
		}

	}

	public ArrayList<MenuItem> getList() {
		return menuList;
	}

	public int getCount() {
		return menuList.size();
	}

	public boolean compareID(String itemID) {
		for (int i = 0; i < menuList.size(); i++) {
			if (menuList.get(i).getID().equals(itemID)) {
				return true;
			}
		}
		return false;
	}

	public int getItemIndexByID(String itemID) {

		for (int i = 0; i < menuList.size(); i++) {
			if (menuList.get(i).getID().equals(itemID)) {
				return i;
			}
		}
		return -1;
	}

	public void removeItem(int index) {
		menuList.remove(index);
	}
	
	public boolean compareMenus(Integer i, Menu menu2) {
		if(this.menuList.get(i).getName().equals(menu2.getList().get(i).getName())) {
			if(this.menuList.get(i).getID().equals(menu2.getList().get(i).getID())) {
				if(this.menuList.get(i).getPrice() == (menu2.getList().get(i).getPrice())) {
					if(this.menuList.get(i).getDesc().equals(menu2.getList().get(i).getDesc())) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
