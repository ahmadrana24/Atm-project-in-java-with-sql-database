package projectatm;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
	
public class Selectapp {

	public static void selectAll(String name, int password) throws IOException {
		Matcher match = null;
		Scanner in = new Scanner(System.in);
		String dbemail = null, dbname = null, dbcash = null, dbcreditnum = null, dbcnic = null;
		int choice;
		String sql = "SELECT * FROM users WHERE id ='" + password + "' AND name ='" + name + "';";

		try (Connection conn = connection.connection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {

				dbname = rs.getString("name");
				dbcash = rs.getString("cash");
				dbcreditnum = rs.getString("creditcard");
				dbcnic = rs.getString("cnic");
				dbemail = rs.getString("email");

			}
			if (dbname == null || password == 0)
				pro.login();
			while (true) {
				System.out.print(
						"Press 1 to check current balance \t Press 2 to withdraw cash\nPress 3 to change pin \t Press 4 for transaction\nPress 5 to exit :  ");
				choice = in.nextInt();

				if (choice == 1) {

					File file = new File("./current.txt");
					file.createNewFile();
					FileWriter fr = new FileWriter(file);

					try {

						fr.write("Name\t" + dbname + "\n");
						fr.write("Balance  \t " + dbcash + "\n");
						fr.write("Cnic-number\t" + dbcnic + "\n");
						fr.write("Creditcard number\t" + dbcreditnum + "\n");
						fr.write("Mailing-address\t  " + dbemail + "\n");
						fr.close();

					} catch (Exception e) {
						e.printStackTrace();
					}

					BufferedReader reader;

					try {

						reader = new BufferedReader(new FileReader(file));
						String line = reader.readLine();
						while (line != null) {
							System.out.println(line);
							line = reader.readLine();
						}
						System.out.println();
						reader.close();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (choice == 2) {
					Double withdrawcash, newamount;
					System.out.println();
					System.out.println("How much cash you want to withdraw : ");
					int withdraw = in.nextInt();

					withdrawcash = Double.parseDouble(dbcash);
					newamount = withdrawcash - withdraw;

					File file = new File("./withdraw.txt");
					file.createNewFile();
					FileWriter fr = new FileWriter(file);

					try {

						fr.write("Name\t" + dbname + "\n");
						fr.write("Amount-deducted \t" + withdraw + "\n");
						fr.write("New-Balance  \t " + newamount + "\n");
						fr.write("Cnic-number\t" + dbcnic + "\n");
						fr.write("Creditcard number\t" + dbcreditnum + "\n");
						fr.write("Mailing-address\t  " + dbemail + "\n");
						fr.close();
						dbcash = Double.toString(newamount);
						String sql1 = "UPDATE users SET cash ='" + dbcash + "' " + "WHERE id ='" + password + "';";

						try (Connection conn1 = connection.connection();
								PreparedStatement pstmt = conn1.prepareStatement(sql1)) {

							pstmt.executeUpdate();
						} catch (SQLException e) {
							System.out.println(e.getMessage());
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

					BufferedReader reader;

					try {

						reader = new BufferedReader(new FileReader(file));
						String line = reader.readLine();
						while (line != null) {
							System.out.println(line);
							line = reader.readLine();
						}
						System.out.println();
						reader.close();

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if (choice == 3) {
					String newpass = null;
					do {
						try {

							System.out.print("Enter 4 digit pin code : ");
							newpass = in.nextLine();
							Pattern pattern = Pattern.compile("\\d{4}");
							match = pattern.matcher(newpass);
						} catch (Exception e) {
							System.out.println("Enter correct pin : ");
							continue;
						}

					} while (match.matches() != true);

					int newpass1 = Integer.parseInt(newpass);

					String sql11 = "UPDATE users SET id ='" + newpass1 + "' " + "WHERE id ='" + password + "';";

					try (Connection conn1 = connection.connection();
						PreparedStatement pstmt = conn1.prepareStatement(sql11)) {
						pstmt.executeUpdate();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					System.out.println("\nPIN CODE UPDATED SUCCESSFULLY!!!!");
				}
				if (choice == 4) {
					while (true) {
						String acc = null, dbcreditnum1 = null, dbcash1 = null, tran = null;

						do {
							try {
								System.out.print("Enter amount for transaaction : ");
								tran = in.nextLine();

							} catch (Exception e) {
								System.out.println("Enter correct amount...");
								continue;
							}

							Pattern pattern = Pattern.compile("[-+]?([0-9]*\\.[0-9]+|[0-9]+)");
							match = pattern.matcher(tran);

						} while (match.matches() == false);

						System.out.println();

						do {
							try {
								System.out.print("Enter correct Account number for transaaction :");
								acc = in.nextLine();
							} catch (Exception e) {
								System.out.println("Enter correct credit card number...");
							}
							Pattern pattern = Pattern.compile(
									"^((4\\d{3})|(5[1-5]\\d{2})|(6011))-?\\d{4}-?\\d{4}-?\\d{4}|3[4,7]\\d{13}$");
							match = pattern.matcher(acc);

						} while (match.matches() == false && luhncheck.Check(acc) == false);

						String sql1 = "SELECT * FROM users WHERE creditcard ='" + acc + "';";

						try (Connection conn1 = connection.connection();
								Statement stmt1 = conn1.createStatement();
								ResultSet rs1 = stmt1.executeQuery(sql1)) {

							while (rs1.next()) {
								dbcash1 = rs1.getString("cash");
								dbcreditnum1 = rs1.getString("creditcard");

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (dbcreditnum1 == null)
							continue;

						Double dbcash2 = Double.parseDouble(dbcash1);
						dbcash2 = dbcash2 + Double.parseDouble(tran);
						String newcash = Double.toString(dbcash2);
						Double cash = Double.parseDouble(dbcash);
						cash = cash - Integer.parseInt(tran);

						dbcash = Double.toString(cash);

						String sql11 = "UPDATE users SET cash ='" + newcash + "' " + "WHERE creditcard ='"
								+ dbcreditnum1 + "';";

						try (Connection conn1 = connection.connection();
								PreparedStatement pstmt = conn1.prepareStatement(sql11)) {

							pstmt.executeUpdate();
						} catch (SQLException e) {
							System.out.println(e.getMessage());
						}

						String sql111 = "UPDATE users SET cash ='" + cash + "' " + "WHERE creditcard ='" + dbcreditnum
								+ "';";

						try (Connection conn1 = connection.connection();
								PreparedStatement pstmt = conn1.prepareStatement(sql111)) {

							pstmt.executeUpdate();
						} catch (SQLException e) {
							System.out.println(e.getMessage());
						}
						System.out.println("\nTRANSACTION SUCCESSFUL!!!!!");
						break;
					}
				}

				if (choice == 5) {
					System.exit(0);
				}

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}
}
