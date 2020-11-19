package Storage;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerList {

	private String custID;
	private String first;
	private String last;
	private String email;
	private String phone;
	private int points;

	private ArrayList<Customer> list = new ArrayList<>();

	public CustomerList() {
	}

	public void add(Customer customer) {
		this.list.add(customer);
	}

	public void fileAdd(Scanner in) {

		while (in.hasNext()) {
			this.custID = in.nextLine();
			this.last = in.nextLine();
			this.first = in.nextLine();
			this.phone = in.nextLine();
			this.email = in.nextLine();
			this.points = Integer.parseInt(in.nextLine());
			Customer customer = new Customer<Customer>(last, first, custID, email, phone);
			customer.addPoints(points);
			list.add(customer);
		}

	}

	public void remove(int index) {
		this.list.remove(index);
	}

	public Customer getCustomerByID(String custID) {

		for (int i = 0; i < this.list.size(); i++) {
			if (custID.contentEquals(this.list.get(i).getID())) {
				return this.list.get(i);
			}
		}
		return null;
	}

	public Customer getCustomerByIndex(int index) {
		return this.list.get(index);
	}

	public int getIndex(String custID) {
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).getID().equals(custID)) {
				return i;
			}
		}
		return -1;
	}

	public int getIndexByName(String first, String last) {
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).getName().equalsIgnoreCase(last + ", " + first)) {
				return i;
			}
		}
		return -1;
	}	

	public int getIndexByName(String name) {
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).getFirst().equalsIgnoreCase(name) || this.list.get(i).getLast().equalsIgnoreCase(name)) {
				return i;
			}
		}
		return -1;
	}	
	
	
	public int getListSize() {
		return list.size();
	}
}
