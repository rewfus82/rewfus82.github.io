package CoffeeShop;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


import Storage.Customer;
import Storage.CustomerList;
import Storage.Menu;
import Storage.MenuItem;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * v0.3
 * 
 * CHANGELOG 11/10: Stream-lined code, removed redundant variables (run
 * booleans), fixed customer add by name, still need to debug customer add,
 * throwing errors with some data
 * @author Ryan
 * 
 **/

/**
 * v0.4
 *
 * CHANGELOG 11/14: Added POS system
 * @author Ryan
 * 
 */

/**
 * v0.5
 *
 * CHANGELOG 11/17: Added daily transaction reports
 * @author Ryan
 * 
 */

/**
 * v1.0
 *
 * CHANGELOG 11/18: Hey! It's a persistent program! Added export/import system.
 * @author Ryan
 * 
 */

/**
 * v1.1
 * 
 * CHANGELOG 12/1: Usability additions
 * 
 * @author Ryan
 *
 */

public class CoffeeShop {

	public static void main(String[] args) throws Exception {

		/** initialize */

		Scanner in = new Scanner(System.in);

		// read in menu from text file

		Menu menu = new Menu();
		readMenuFromBlog(menu);

		// read in customer list from text file
		CustomerList customerList = new CustomerList();
		FileReader read = new FileReader("customerlisttext");
		customerList.fileAdd(new Scanner(read));
		read.close();

		ArrayList<MenuItem> dailyTrans = new ArrayList<>();
		// if no daily report text file exists, make a new one
		if (!new File(LocalDate.now() + " Report").exists()) {
			System.out.print("\nDaily report does no exist!\nCreating new daily report.\n\n");
			File file = new File(LocalDate.now() + " Report");
			file.createNewFile();
		}

		// read in daily report from text file
		read = new FileReader(LocalDate.now() + " Report");
		Scanner daily = new Scanner(read);
		while (daily.hasNext()) {
			String itemID = daily.nextLine();
			String name = daily.nextLine();
			String description = daily.nextLine();
			String priceStr = daily.nextLine();
			Double price = Double.parseDouble(priceStr);

			MenuItem temp = new MenuItem(name, itemID, price, description);
			dailyTrans.add(temp);
		}
		daily.close();

		/** main */

		String input;
		boolean run = true;

		while (run) { // main run loop
			printMainMenu();
			input = in.nextLine().toUpperCase();

			/** DRINK MENU **/

			if (input.equals("M")) {// open drink menu
				a: while (run) {
					printMenuMenu();
					input = in.nextLine().toUpperCase();

					if (input.equals("V")) { // view the drink menu
						printMenuHeader();
						for (int i = 0; i < menu.getList().size(); i++) {
							printMenuLine(menu.getList().get(i));
						}
						printBorder(98);

					} else if (input.equals("A")) {// add customer
						b: while (run) {
							menu.manualAdd(in, run);

							while (run) {
								System.out.print("Add another item? (Y/N):");
								input = in.nextLine().toUpperCase();

								if (input.equals("N")) {
									break b;
								} else if (input.equals("Y")) {
									return;
								}
							}
						}

					} else
						c: if (input.equals("D")) {// delete menu item
							System.out.print("Please enter the ID of the item to delete:");
							input = in.nextLine();

							if (!menu.compareID(input)) {
								System.out.print("ID not found.\n\n");
								break c;
							}

							System.out.print(
									"Remove " + menu.getList().get(menu.getItemIndexByID(input)).getName().toUpperCase()
											+ "? (Y/N)");

							if (in.nextLine().toUpperCase().equals("Y")) {
								System.out.print("\nRemoved "
										+ menu.getList().get(menu.getItemIndexByID(input)).getName().toUpperCase()
										+ " with ID #" + input + "\n\n");
								menu.removeItem(menu.getItemIndexByID(input));
							}

							// } else if (input.equals("U")) {// upload a menu
							// System.out.println("Maybe later....");
							// write me

						} else if (input.equals("B")) {// go back
							break a;
						}

				}

				/** CUSTOMER MENU **/

			} else if (input.equals("C")) { // open customer menu

				a: while (run) {
					printCustomerMenu();
					input = in.nextLine().toUpperCase();

					b: if (input.equals("S")) { // search customer menu
						System.out.print("Search by:\n" + "----------------------------------------\n"
								+ "(N)ame\n(C)ustomer ID\n" + "----------------------------------------\n");
						input = in.nextLine().toUpperCase();

						if (input.equals("N")) { // search by name
							System.out.print("\nLast name: ");
							String last = in.nextLine();
							System.out.print("\nFirst name: ");
							String first = in.nextLine();

							if (last.equals("") || (first.equals(""))) {
								String name = "";
								if (last.equals("")) {
									name = first;
								}
								
								if (first.equals("")) {
									name = last;
								}
								
								if (customerList.getIndexByName(name) == -1) {
									System.out.print("No customer found.\n");
								} else {
									printCustomerHeader();
									printCustomerLine(
											customerList.getCustomerByIndex(customerList.getIndexByName(name)));
									printBorder(98);
									System.out.print("\n");
								}
								
							} else if (customerList.getIndexByName(first, last) >= 0) {
								printCustomerLine(
										customerList.getCustomerByIndex(customerList.getIndexByName(first, last)));
								printBorder(98);
								System.out.print("\n");

							} else {
								System.out.println("Customer not found.");
							}

						} else if (input.equals("C")) { // search by id
							System.out.print("\nCustomer ID: ");
							String custID = in.nextLine();
							if (java.util.regex.Pattern.matches("[a-zA-Z]+", custID)) {
								System.out.print("Invalid ID entered.\n");
								break b;
							} else if(customerList.getIndex(custID) == -1) {
								System.out.print("No customer found.\n");
							} else {
								printCustomerHeader();
								printCustomerLine(customerList.getCustomerByIndex(customerList.getIndex(custID)));
								printBorder(98);
								System.out.print("\n");
							}
						}

					} else if (input.equals("A")) { // add customer
						System.out.print("First name: ");
						String first = in.nextLine();
						System.out.print("Last name: ");
						String last = in.nextLine();
						System.out.print("Customer ID: ");
						String custID = in.nextLine();
						System.out.print("Email address: ");
						String email = in.nextLine();
						System.out.print("Phone number: ");
						String phone = in.nextLine();
						System.out.print("\nName: " + first + " " + last + "\nCustomer ID: " + custID + "\nEmail: "
								+ email + "\nPhone: " + phone);

						while (run) {
							System.out.print("\nAdd this customer?(Y/N): ");
							input = in.nextLine().toUpperCase();
							if (input.equals("Y")) {
								Customer customer = new Customer(last, first, custID, email, phone);
								customerList.add(customer);
								System.out.println("Customer added.");
								break;
							} else {
								break;
							}
						}

					} else if (input.equals("D")) {
						System.out.print("Delete customer by:\n" + "----------------------------------------\n"
								+ "(N)ame\n(C)ustomer ID\n" + "----------------------------------------\n");
						input = in.nextLine().toUpperCase();

						if (input.equals("N")) {
							System.out.print("\nLast name: ");
							String last = in.nextLine().toUpperCase();
							System.out.print("\nFirst name: ");
							String first = in.nextLine().toUpperCase();
							printCustomerHeader();

							if (customerList.getIndexByName(first, last) >= 0) {
								printCustomerLine(
										customerList.getCustomerByIndex(customerList.getIndexByName(first, last)));
								printBorder(99);
								System.out.print("\n");
								System.out.print("Delete this customer? (Y/N)");
								input = in.nextLine().toUpperCase();

								if (input.equals("Y")) {
									customerList.remove(customerList.getIndexByName(first, last));
									System.out.println("Customer deleted.");
								}

							} else {
								System.out.println("Customer not found.");
							}

						} else if (input.equals("C")) {
							System.out.print("\nCustomer ID: ");
							String custID = in.nextLine();
							printCustomerHeader();
							printCustomerLine(customerList.getCustomerByID(custID));
							printBorder(80);
							System.out.print("Delete this customer? (Y/N)");
							input = in.nextLine().toUpperCase();

							if (input.equals("Y")) {
								customerList.remove(customerList.getIndex(custID));
								System.out.println("Customer deleted.");
							}
						}

					} else if (input.equals("V")) {
						printCustomerHeader();

						for (int i = 0; i < customerList.getListSize(); i++) {
							printCustomerLine(customerList.getCustomerByIndex(i));
						}
						printBorder(98);

					} else if (input.equals("B")) {
						break a;
					}
				}

			} else if (input.equals("Q")) {
				runExport(menu, customerList, dailyTrans);
				run = false;

				/** RECORDS MENU **/

			} else
				Records: if (input.equals("R")) {

					printRecordsMenu();
					input = in.nextLine().toUpperCase();
					if (input.equals("R")) {
						double total = 0.0;
						System.out.println("\n\n\n**DAILY TRANSACTIONS**\n");
						printMenuHeader();
						for (int i = 0; i < dailyTrans.size(); i++) {
							total += dailyTrans.get(i).getPrice();
							printMenuLine(dailyTrans.get(i));
						}
						printBorder(98);
						System.out.print("Total: $");
						System.out.printf("%.2f %n", total);
						System.out.print("\n\n\n");
						break Records;
					} else if (input.equals("B")) {
						break Records;
					}

					/** Point of Sale **/
				} else
					a: if (input.equals("P")) {

						int customerIndex = -1;
						String itemID;
						boolean freeDrink = false;
						boolean noCust = false;
						ArrayList<MenuItem> orderList = new ArrayList<>();
						double subtotal = 0.0;

						System.out.print("Enter customer ID, (N)one to continue or go (B)ack:");
						input = in.nextLine();

						b: while (!input.equalsIgnoreCase("n") && !input.equalsIgnoreCase("b")) {
							for (int i = 0; i < customerList.getListSize(); i++) {
								if (input.equals(customerList.getCustomerByIndex(i).getID())) {
									customerIndex = i;
									break b;
								} else if (i == customerList.getListSize() - 1) {
									System.out.print(
											"Customer ID does not exist.\nEnter customer ID, (N)one to continue or go (B)ack:");
									input = in.nextLine();
								}
							}
						}
						if (input.equalsIgnoreCase("n")) {
							noCust = true;
							System.out.print(
									"Continuing without customer information. No points will be awarded for this sale.\n\n");
						}

						if (input.equalsIgnoreCase("b")) {
							break a;
						}

						if (!noCust) {
							System.out.print("Customer Name: "
									+ customerList.getCustomerByIndex(customerIndex).getName() + "\n" + "Points : "
									+ customerList.getCustomerByIndex(customerIndex).getPoints() + "\n\n");
							if (customerList.getCustomerByIndex(customerIndex).getPoints() >= 100) {
								System.out.print("***Customer is Eligible for free drink***\n\n");
								System.out.print("Use free drink?(Y/N)");
								input = in.nextLine().toUpperCase();
								if (input.equals("Y")) {
									freeDrink = true;
								}
							}
						}
						printMenuHeader();
						for (int i = 0; i < menu.getList().size(); i++) {
							printMenuLine(menu.getList().get(i));
						}
						printBorder(98);
						if (freeDrink) {
							System.out.print("Enter the item ID for free drink: ");
							itemID = in.nextLine();
							for (int i = 0; i < menu.getList().size(); i++) {
								if (itemID.equals(menu.getList().get(i).getID())) {
									orderList.add(menu.getList().get(i));
									customerList.getCustomerByIndex(customerIndex).removePoints(100);
									System.out.println("Free drink redeemed: " + menu.getList().get(i).getName());
									MenuItem temp = menu.getList().get(i);
									temp.setFree();
									break;
								} else if (i == menu.getList().size() - 1) {
									System.out.print("Item ID does not exist.\n");
									break;
								}
							}
						}
						c: while (run) {
							System.out.print("Item ID or (T)otal: ");
							itemID = in.nextLine();
							for (int i = 0; i < menu.getList().size(); i++) {
								if (itemID.equalsIgnoreCase("T")) {
									break c;
								} else if (itemID.equals(menu.getList().get(i).getID())) {
									orderList.add(menu.getList().get(i));
									subtotal += menu.getList().get(i).getPrice();
									System.out.print("Adding " + menu.getList().get(i).getName() + ": $");
									System.out.printf("%.2f %n", menu.getList().get(i).getPrice());
									break;
								} else if (i == menu.getList().size() - 1) {
									System.out.print("Item ID does not exist.\n");
									break;
								}
							}
						}
						double total = Math.round(subtotal * 100.00) / 100.00;
						printMenuHeader();
						for (int i = 0; i < orderList.size(); i++) {
							printMenuLine(orderList.get(i));
							subtotal += orderList.get(i).getPrice();
						}
						printBorder(98);
						System.out.println();
						System.out.print("TOTAL: $" + total);
						System.out.println();
						System.out.print("\n(F)inalize or (C)ancel Transaction: ");
						input = in.nextLine().toUpperCase();
						if (input.equals("C")) {
							break;
						} else if (input.equals("F")) {
							if (total > 0) {
								if (!noCust) {
									int totalPoints = (int) Math.round(total);
									customerList.getCustomerByIndex(customerIndex).addPoints(totalPoints);
									System.out.print("\nCustomer Name: "
											+ customerList.getCustomerByIndex(customerIndex).getName() + "\n"
											+ "Points : " + customerList.getCustomerByIndex(customerIndex).getPoints()
											+ "\n\n");
								}
								System.out.print("Payment accepted. Transaction added to daily totals.\n\n");
								dailyTrans.addAll(orderList);
							} else {
								System.out.print("Transaction aborted.\n\n");
							}
						}

					}
		}

	}

	/** init/exit methods */
	private static void runExport(Menu menu, CustomerList custList, ArrayList<MenuItem> dailyTrans)
			throws FileNotFoundException {
		PrintWriter menuout = new PrintWriter("menutext");
		PrintWriter custout = new PrintWriter("customerlisttext");
		PrintWriter dailyout = new PrintWriter(LocalDate.now() + " Report");
		System.out.print("Running exports...\n");
		for (int i = 0; i < menu.getCount(); i++) {
			menuout.println(menu.getList().get(i).getID());
			menuout.println(menu.getList().get(i).getName());
			menuout.println(menu.getList().get(i).getDesc());
			menuout.println(menu.getList().get(i).getPrice());
			// System.out.print(".");
		}
		System.out.print("Menu exported...\n");
		for (int i = 0; i < custList.getListSize(); i++) {
			custout.println(custList.getCustomerByIndex(i).getID());
			custout.println(custList.getCustomerByIndex(i).getLast());
			custout.println(custList.getCustomerByIndex(i).getFirst());
			custout.println(custList.getCustomerByIndex(i).getPhone());
			custout.println(custList.getCustomerByIndex(i).getEmail());
			custout.println(custList.getCustomerByIndex(i).getPoints());
			// System.out.print(".");
		}
		System.out.print("Customer list exported...\n");

		for (int i = 0; i < dailyTrans.size(); i++) {
			dailyout.println(dailyTrans.get(i).getID());
			dailyout.println(dailyTrans.get(i).getName());
			dailyout.println(dailyTrans.get(i).getDesc());
			dailyout.println(dailyTrans.get(i).getPrice());
		}
		System.out.print("Daily report exported...\n");
		System.out.print("Exports complete!");
		menuout.close();
		custout.close();
		dailyout.close();

	}

	private static void readMenuFromBlog(Menu menu) throws Exception {
		URL blog = new URL("https://coffeemenu.food.blog/");
		URLConnection connection = blog.openConnection();
		HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
		int code = httpsConnection.getResponseCode();
		String id;
		String name;
		String description;
		double price;
		if (code != HttpsURLConnection.HTTP_OK) {
			return;
		} else {
			System.out.print("Connected to https://coffeemenu.food.blog\n");
		}
		InputStream instream = connection.getInputStream();
		Scanner in = new Scanner(instream);

		a: while (in.hasNextLine()) {
			String scanLine = in.nextLine();
			if (scanLine.contains("<p> <strong>COFFEE MENU</strong> </p>")) {
				while (in.hasNextLine()) {
					scanLine = in.nextLine();
					if (scanLine.contains("<p>  </p>")) {
						in.close();
						instream.close();
						break a;
					} else if (scanLine.contains("<p>")) {
						if (scanLine.contains("&#8217;")) {
							scanLine = scanLine.replaceAll("&#8217;", "'");
						}

						id = scanLine.substring(3, 6);
						name = scanLine.substring(scanLine.indexOf("<strong>") + 8, scanLine.indexOf("</s"));
						description = scanLine.substring(scanLine.indexOf("<em>") + 4, scanLine.indexOf("</em>"));
						price = Double
								.parseDouble(scanLine.substring(scanLine.indexOf("12; ") + 5, scanLine.indexOf("</p")));
						menu.add(name, id, price, description);
					}
				}
			}
		}
		System.out.print("Menu downloaded.\n\n");
	}

	private static void appendMenu(Menu menu, Menu textMenu) {
		if (menu.getList().size() == textMenu.getList().size()) {
			for (int i = 0; i < menu.getList().size(); i++) {
				if (!menu.compareMenus(i, textMenu)) {
					menu.addItem(textMenu.getList().get(i));
				}
			}
		} else if (menu.getList().size() < textMenu.getList().size()) {
			for (int i = menu.getList().size(); i < textMenu.getList().size(); i++) {
				menu.addItem(textMenu.getList().get(i));
			}
		}

	}

	/** print methods */
	private static void printBorder(int count) {
		for (int i = 1; i <= count; i++) {
			System.out.print("-");
		}
		System.out.print("\n");
	}

	private static void printMenuLine(MenuItem menuItem) {
		System.out.printf("%-7s", menuItem.getID());
		System.out.printf("%-25s", menuItem.getName().toUpperCase());
		System.out.printf("%-60s", menuItem.getDesc());
		System.out.print("$");
		System.out.printf("%.2f %n", menuItem.getPrice());
	}

	private static void printMenuHeader() {
		System.out.printf("%-7s", "ID");
		System.out.printf("%-25s", "Name");
		System.out.printf("%-60s", "Description");
		System.out.printf("%-10s %n", "Price");
		System.out.println(
				"--------------------------------------------------------------------------------------------------");
	}

	private static void printMenuMenu() {
		System.out.print("Choose an option:\n" + "----------------------------------------\n" + "(V)iew the menu\n"
				+ "(A)dd an item to the menu\n" + "(D)elete an item from the menu\n" + "\n" + "(B)ack\n"
				+ "----------------------------------------\n");
	}

	private static void printMainMenu() {
		System.out.print("Choose an option:\n" + "----------------------------------------\n" + "(P)oint of Sale\n"
				+ "(M)enu Settings\n" + "(C)ustomer Settings\n" + "(R)eports\n" + "\n(Q)uit\n"
				+ "----------------------------------------\n");
	}

	private static void printCustomerMenu() {
		System.out.print("\nChoose an option:\n" + "----------------------------------------\n"
				+ "(V)iew the customer list\n" + "(S)earch for a customer\n" + "(A)dd a customer\n"
				+ "(D)elete a customer\n" + "\n(B)ack\n" + "----------------------------------------\n");
	}

	private static void printCustomerHeader() {
		System.out.printf("%-14s", "ID");
		System.out.printf("%-30s", "Name");
		System.out.printf("%-18s", "Phone");
		System.out.printf("%-30s", "Email");
		System.out.printf("%-7s", "Points\n");
		printBorder(98);
	}

	private static void printCustomerLine(Customer customer) {
		System.out.printf("%-15s", "\n" + customer.getID());
		System.out.printf("%-30s", customer.getName());
		System.out.printf("%-18s", customer.getPhone());
		System.out.printf("%-30s", customer.getEmail());
		System.out.printf("%-7s", customer.getPoints());
		System.out.print("\n");
	}

	private static void printRecordsMenu() {
		System.out.print("Choose an option:\n" + "----------------------------------------\n" + "(R)un Daily Report\n"
				+ "\n(B)ack\n" + "----------------------------------------\n");
	}

}
