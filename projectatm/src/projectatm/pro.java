package projectatm;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;

public class pro {

	public static void main(String[] args) throws IOException {
		System.out.println("\t \t WELCOME USER :)))");
		System.out.println();
		String a;
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.printf("\t \t Press 1 to login\n\t \t Press 2 to register\n\t \t Press 3 to exit : ");
			a = in.nextLine();
			if (a.equals("1")) {
				login();
			} else if (a.equals("2")) {
				register();
			} else if (a.equals("3"))
				System.exit(0);
			else
				continue;
		}

	}

	public static void login() throws IOException {
		Scanner in = new Scanner(System.in);
		String name, password = null;
		Matcher match = null;
		connection.connection();
		int pass;
		System.out.print("Enter your correct name : ");
		name = in.nextLine();

		do {
			try {

				System.out.print("Enter 4 digit pin code : ");
				password = in.nextLine();
				Pattern pattern = Pattern.compile("\\d{4}");
				match = pattern.matcher(password);
			} catch (Exception e) {
				System.out.println("Enter correct pin : ");
				continue;
			}

		} while (match.matches() != true);
		pass = Integer.parseInt(password);
		Selectapp.selectAll(name, pass);
	}

	public static void register() throws IOException {
		register.register();
		String cash = null, password = null, email = null, creditcard = null, cnic = null, number = null;
		Matcher match = null;
		Pattern ptr;
		Scanner in = new Scanner(System.in);
		int pass;

		System.out.print("Enter your name : ");

		String name = in.nextLine();

		do {
			try {
				System.out.print("Enter your valid email : ");
				email = in.nextLine();
			} catch (Exception e) {
				System.out.println("Enter correct email...");
			}
			ptr = Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
		} while (ptr.matcher(email).matches() == false);

		do {
			try {

				System.out.print("Enter 4 digit pin code : ");
				password = in.nextLine();
				Pattern pattern = Pattern.compile("\\d{4}");
				match = pattern.matcher(password);
			} catch (Exception e) {
				System.out.println("Enter correct pin : ");
				continue;
			}

		} while (match.matches() != true);
		pass = Integer.parseInt(password);

		do {
			try {

				System.out.print("Enter your cnic : ");
				cnic = in.nextLine();
			} catch (Exception e) {
				System.out.println("Enter correct cnic...");
				continue;
			}
			Pattern pattern = Pattern.compile("^[0-9+]{5}-[0-9+]{7}-[0-9]{1}$");
			match = pattern.matcher(cnic);

		} while (match.matches() == false);

		do {
			try {
				System.out.print("Enter your phone number : ");
				number = in.nextLine();
			} catch (Exception e) {
				System.out.println("Enter correct phone number...");
				continue;
			}
			Pattern pattern = Pattern.compile("^((\\+92)|(0092))-{0,1}\\d{3}-{0,1}\\d{7}$|^\\d{11}$|^\\d{4}-\\d{7}$");
			match = pattern.matcher(number);

		} while (match.matches() == false);

		do {
			try {
				System.out.print("Enter your credit card number : ");
				creditcard = in.nextLine();
				Pattern pattern = Pattern.compile("^((4\\d{3})|(5[1-5]\\d{2})|(6011))-?\\d{4}-?\\d{4}-?\\d{4}|3[4,7]\\d{13}$");
				match = pattern.matcher(creditcard);
			} catch (Exception e) {
				System.out.println("Enter correct credit card number...");
				continue;
			}

		} while (match.matches() == false && luhncheck.Check(creditcard) == false);

		
		do {
			try {
				System.out.print("Enter the amount you want to deposit : ");
				cash = in.nextLine();

			} catch (Exception e) {
				System.out.println("Enter correct amount...");
				continue;
			}

			Pattern pattern = Pattern.compile("[-+]?([0-9]*\\.[0-9]+|[0-9]+)");
			match = pattern.matcher(cash);

		} while (match.matches() == false);

		InsertApp app = new InsertApp();
		app.insert(name, pass, cash, email, creditcard, cnic, number);
		System.out.println("YOU ARE SUCCESSFULLY REGISTERED!!!!!!!!!!!");
	}
}
