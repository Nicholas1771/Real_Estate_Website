package com.capstone;

import java.sql.*;

public class Database {
	
	private final static String USERNAME = "root";
	private final static String PASSWORD = "";
	private final static String DATABASE_NAME = "capstone";
	private final static String CONN_STRING = "jdbc:mysql://localhost:3306";
	private final static String CONN_STRING_DB = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;

	/*
	 * this method sets up connection
	 */
	
	//Used only when we need to change the table
	public static void deleteDB (Connection connection) {
		String sql = "DROP DATABASE " + DATABASE_NAME + ";";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Error deleting database");
		}
		
	}
	
	public static String getDBCreationSQL () {
		String sql;
		
		sql =
				"CREATE DATABASE " + DATABASE_NAME + ";";
		return sql;
	}
	
	public static String getTableCreationSQL () {
		String sql;
		
		sql =
				"CREATE TABLE User "
				+ "(UserName varchar(255) not null primary key, "
				+ "FirstName varchar(255) not null, "
				+ "LastName varchar(255) not null, "
				+ "Password varchar(255) not null, "
				+ "Email varchar(255) not null, "
				+ "Code char(6) not null, "
				+ "Verified varchar(255) not null);";
		return sql;
	}
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;		
		
		try {
			//Connect to the server
			connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("Connected to mysql server");
			// Code below used from https://stackoverflow.com/questions/12414596/how-to-check-if-a-particular-database-in-mysql-already-exists-using-java
			ResultSet resultSet = connection.getMetaData().getCatalogs();
		
			boolean dbExists = false;
			
			//iterate each catalog in the ResultSet
			while (resultSet.next()) {
				// Get the database name, which is at position 1
				String databaseName = resultSet.getString(1);
				System.out.println("found database with name: " + databaseName);
				if (databaseName.equals(DATABASE_NAME)) {
					dbExists = true;
				}	  
			}
			resultSet.close();
			
			if (dbExists) {
				System.out.println("Database already exists");
			} else {
				System.out.println("Database does not exist yet, attempting to create it");
				
				Statement statement = connection.createStatement();
				statement.executeUpdate(getDBCreationSQL());
				System.out.println("Datbase created");
				statement.executeUpdate("USE " + DATABASE_NAME + ";");
				System.out.println("Using database");
				statement.executeUpdate(getTableCreationSQL());
				System.out.println("Table created");
				
			}

				connection = DriverManager.getConnection(CONN_STRING_DB, USERNAME, PASSWORD);
				System.out.println("Connected to database");
			} catch (Exception e) {
				System.err.println(e);
			}
		return connection;		
	}
	
	
	/*
	 * this method checks if username is in database
	 */
	
	public String getUserVerificationCode (String username) {
		String SQL = "SELECT * FROM User WHERE userName = '" + username + "'"; 
		
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(SQL);

            if (rs.next()) {

                return rs.getString("Code");
            } else {
            	System.out.println("User is not in database");
            	return "ERROR!";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ERROR!";
        }
	}
	
	public String getUserEmail (String username) {
		String SQL = "SELECT * FROM User WHERE userName = '" + username + "'"; 
		
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(SQL);

            if (rs.next()) {

                return rs.getString("Email");
            } else {
            	System.out.println("User is not in database");
            	return "ERROR!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR!";
        }
	}
	
	public boolean validUser(User user) {
		
		String username = user.getUsername();
		
		String SQL = "SELECT * FROM User WHERE userName = '" + username + "'"; 
		
		// execute statement
		
		try {
			Statement statement = Database.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(SQL);
			
			if (rs.next()) {
				System.out.println("User is in database");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// if user is not in database
		return true;
	}
	
	
	/*
	 * this method deals with the user login
	 */

	public boolean login (String username, String password) {

        String SQL = "SELECT * FROM User WHERE UserName = '" + username + "'"; 

        // execute statement

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(SQL);

            if (rs.next()) {
                System.out.println("FOUND EXISTING USER");

                if (rs.getString("Password").equals(password) ) {
                    System.out.println("CORRECT USERNAME AND PASSWORD");
                    return true;
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // if user is not in database or password is incorrect
        return false;
    }
	
	//Checks if the user has been email verified
	public boolean isUserVerified (String username) {
		String SQL = "SELECT * FROM User WHERE userName = '" + username + "'"; 
		
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(SQL);

            if (rs.next()) {
                System.out.println("Checking if user is verified in database");

                if (rs.getString("Verified").equals("true") ) {
                    System.out.println("User is verified");
                    return true;
                } else {
                	System.out.println("User is not verified");
                	return false;
                }
            } else {
            	System.out.println("User is not in database");
            	return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
	}
	
	//Verifies the user in the database
	public void verifiyUser (String username) {
		
		String SQL = "UPDATE `Capstone`.`User` SET `Verified` = 'true' WHERE (`userName` = '"+ username + "');";
		
		try {
		Statement statement = Database.getConnection().createStatement();
		statement.executeUpdate(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Verified User");
	}
	/*
	 * this methods add the User to the database
	 */
	public void addUserToDatabase(User user, String code) {
		String username = user.getUsername();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String password = user.getPassword();
		String email = user.getEmail();
		
		// statement
		String SQL = "INSERT INTO `Capstone`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Email`, `Code`, `Verified`) VALUES ('" + username + "', '" + firstName + "', '" + lastName + "', '" + password + "', '" + email + "', '" + code + "', 'false');";
		
		
		// execute statement
		
		try {
			Statement statement = Database.getConnection().createStatement();
			statement.executeUpdate(SQL);
			System.out.println("Added to database");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * this method removes a User from Database (used for deactivation)
	 */
	
	public void deleteUserFromDatabase(User user) {
		String username = user.getUsername();
		
		String SQL = "Delete FROM `Capstone`.`User` WHERE UserName = '" + username +"';";
		
		// execute Statement
		
		try {
			Statement statement = Database.getConnection().createStatement();
			statement.executeUpdate(SQL);
			System.out.println("User has been deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/*
	 * this methods updated the first Name of the User
	 */

	public void updateFirstName(User user) {
		String firstName = user.getFirstName();
		String username = user.getUsername();
		
		String SQL = "UPDATE `Capstone`.`User` SET `FirstName` = '" + firstName + "' WHERE (`userName` = '"+ username + "');";
		
		// execute Statement
		
		try {
			Statement statement = Database.getConnection().createStatement();
			statement.executeUpdate(SQL);
			System.out.println("First Name has been updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * this method updates the lastName of the User
	 * 
	 */
	
	public void updateLastName(User user) {
		String lastName = user.getLastName();
		String username = user.getUsername();
		
		String SQL = "UPDATE `Capstone`.`User` SET `LastName` = '" + lastName + "' WHERE (`userName` = '"+ username + "');";
		
		try {
			Statement statement = Database.getConnection().createStatement();
			statement.executeUpdate(SQL);
			System.out.println("Last Name has been updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * this method checks if username is in database
	 */
	
	public void setNewVerificationCode (String username, String code) {
		
		String SQL = "UPDATE `Capstone`.`User` SET `Code` = '" + code + "' WHERE (`userName` = '"+ username + "');";
		
		// execute Statement
		
		try {
			Statement statement = Database.getConnection().createStatement();
			statement.executeUpdate(SQL);
			System.out.println("Code has been changed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * this method allows user to change password
	 */
	
	public void updatePassword(String username, String password) {
		
		String SQL = "UPDATE `Capstone`.`User` SET `Password` = '" + password + "' WHERE (`UserName` = '"+ username + "');";
		
		try {
			Statement statement = Database.getConnection().createStatement();
			statement.executeUpdate(SQL);
			System.out.println("Password has been updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * this method creates the Listing Table
	 */
	

	public static String listingTableCreation () {
		String sql;
		
		sql =
				"CREATE TABLE `Listing` "
				+ "(`ListingID` INT NOT NULL AUTO_INCREMENT,"
				+ "`Address` VARCHAR(255) NOT NULL,"
				+ "`city` VARCHAR(255) NOT NULL,"
				+ "`country` VARCHAR(255) NOT NULL,"
				+ "`postalCode` VARCHAR(255) NOT NULL,"
				+ "`numberOfBedrooms` VARCHAR(255) NOT NULL,"
				+ "`numberOfBathrooms` VARCHAR(225) NOT NULL,"
				+ "`homeType` VARCHAR(255) NOT NULL,"
				+ "`price` VARCHAR(255) NOT NULL," 
				+ "PRIMARY KEY (`ListingID`));";
				
		return sql;
	}
	
	
	
	
	
	
	
}


