package Storage;

import java.util.ArrayList;
import java.util.Comparator;

public class Customer<Customer> {

	private String custID;
	private String first;
	private String last;
	private String email;
	private String phone;
	private int points;

	public Customer() {
	}

	public Customer(String last, String first, String custID, String email, String phone) {
		this.custID = custID;
		this.last = last;
		this.first = first;
		this.email = email;
		this.phone = phone;
	}

	public String getID() {
		return this.custID;
	}

	public String getName() {
		return this.last + ", " + this.first;
	}

	public String getLast() {
		return last;
	}

	public String getFirst() {
		return first;
	}

	public void changeName(String first, String last) {
		this.first = first;
		this.last = last;
	}

	public String getEmail() {
		return this.email;
	}

	public void changeEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public boolean changePhone(String phone) {
		if (phone.length() < 12) {
			this.phone = phone;
			return true;
		}
		return false;
	}

	public int getPoints() {
		return this.points;
	}

	public void addPoints(int points) {
		this.points += points;
	}

	public boolean removePoints(int points) {
		if (this.points - points > 0) {
			this.points -= points;
			return true;
		}
		return false;
	}

}
