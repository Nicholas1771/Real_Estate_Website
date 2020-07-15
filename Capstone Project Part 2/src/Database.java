
import java.sql.*;

public class Database {
	
	private final static String USERNAME = "admin";
	private final static String PASSWORD = "capstoneproject123";
	private final static String CONN_STRING = "jdbc:mysql://localhost:3306/Capstone";
	private User user;
	
	/*
	 * the constructor sets up the connection
	 */
	public Database(User user) {
		this.user = user;
	}
	
	
	/*
	 * this method sets up connection
	 */
	
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		
		
		try {
			connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.err.println(e);
		}
		
		return connection;
		
	}
	
	
	/*
	 * this method checks if username is in database
	 */
	
	
	public boolean validUser(User user) {
		
		String username = user.getUsername();
		
		String SQL = "SELECT * FROM User WHERE userName = '" + username + "'"; 
		
		// execute statement
		
		try {
			Statement statement = Database.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(SQL);
			
			if (rs.next()) {
				System.out.println("returning true");
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// if user is not in database
		return false;
	}
	
public boolean login (String username, String password) {
		
		String SQL = "SELECT * FROM User WHERE userName = '" + username + "'"; 
		
		// execute statement
		
		try {
			Statement statement = Database.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(SQL);
			
			if (rs.next()) {
				System.out.println("FOUND EXISTING USER");
				
				if (rs.getString("password").equals("password") ) {
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
	
	
	/*
	 * this methods add the User to the database
	 */
	public void addUserToDatabase(User user) {
		String username = user.getUsername();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String password = user.getPassword();
		String email = user.getEmail();
		
		// statement
		String SQL = "INSERT INTO `Capstone`.`user` (`userName`, `firstName`, `lastName`, `password`, `email`) VALUES ('" + username + "', '" + firstName + "', '" + lastName + "', '" + password + "', '" + email + "');";
		
		
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
		
		String SQL = "Delete FROM `Capstone`.`User` WHERE userName = '" + username +"';";
		
		// execute Statement
		
		try {
			Statement statement = Database.getConnection().createStatement();
			statement.executeUpdate(SQL);
			System.out.println("User has been deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}