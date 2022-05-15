package com.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerService {
	
	public Connection connect(){
    	
        //database connection details
        String dbDriver = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/";
        String dbName = "pgms";
        String dbUsername = "root";
        String dbPassword = "";
        
        Connection conn = null;
        
        try {
        	//connecting the database
        	Class.forName(dbDriver);
        	conn = DriverManager.getConnection(dbURL+dbName, dbUsername, dbPassword);
        	
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        return conn;
    }
	
	
	//INSERT CUSTOMER
	
    public String insertCustomer(String firstName, String lastName, String email, String mobile,  String address, String postalCode) {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "INSERT INTO customer(`firstName`,`lastName`,`email`,`mobile`,`address`,`postalCode`) VALUES( ?, ?, ?, ?, ?,?)";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setString(1, firstName);
        	preparedStatement.setString(2, lastName);
        	preparedStatement.setString(3, email);
        	preparedStatement.setString(4, mobile);
        	preparedStatement.setString(5, address);
        	preparedStatement.setString(6, postalCode);
			        	
        	//execute the SQL statement
        	preparedStatement.execute();
        	conn.close();

			String newCustomer = readAllCustomer(); 
			Output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";
        	
    	} catch(Exception e) {
			Output = "{\"status\":\"error\", \"data\": \"Failed to insert the Customer!\"}";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }

    
    
    //UPDATE CUSTOMER
    
    public String updateCustomer(String userID,String firstName, String lastName, String email, String mobile,  String address, String postalCode) {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "UPDATE customer SET firstName = ?, lastName = ?, email = ?, mobile = ?,  address= ?, postalCode = ? WHERE userID =?";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setString(1, firstName);
        	preparedStatement.setString(2, lastName);
        	preparedStatement.setString(3, email);
        	preparedStatement.setString(4, mobile);
        	preparedStatement.setString(5, address);
        	preparedStatement.setString(6, postalCode);
           	preparedStatement.setInt(7, Integer.parseInt(userID));
        	
        	
        	
        	//execute the SQL statement
        	preparedStatement.executeUpdate();
        	conn.close();
        	
        	String newCustomer = readAllCustomer(); 
      		Output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";
        	
    	} catch(Exception e) {
    		Output = "{\"status\":\"error\", \"data\":\"Failed to update the customer!\"}"; 
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    
     // READ CUSTOMER
    
    public String readAllCustomer() {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "SELECT * FROM customer";
        	
        	//executing the SQL query
        	Statement statement = conn.createStatement();
        	ResultSet resultSet = statement.executeQuery(query);
        	
        	// Prepare the HTML table to be displayed
    		Output = "<table class='table table-striped'><tr><th>UserID</th>" 
        	+"<th>First Name</th><th>Last Name</th>"
        	+"<th>Email</th><th>Mobile</th>"
        	+"<th>address</th><th>postalCode</th>"
    		+ "<th>Update</th><th>Remove</th></tr>";
        	
        	while(resultSet.next()) {
        		String userID = Integer.toString(resultSet.getInt("userID"));
        		String firstName = resultSet.getString("firstName");
        		String lastName = resultSet.getString("lastName");
        		String email = resultSet.getString("email");
        		String mobile = resultSet.getString("mobile");
        		String address = resultSet.getString("address");
        		String postalCode = resultSet.getString("postalCode");
        		
        		
        		// Add a row into the HTML table
        		Output += "<tr><td>" + userID + "</td>"; 
        		Output += "<td>" + firstName + "</td>"; 
        		Output += "<td>" + lastName + "</td>"; 
        		Output += "<td>" + email + "</td>";
        		Output += "<td>" + mobile + "</td>"; 
        		Output += "<td>" + address + "</td>"; 
        		Output += "<td>" + postalCode + "</td>";
  
        		
        		// buttons
        		Output += "<td>"
						+ "<input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-sm btn-secondary' data-userid='" + userID + "'>"
						+ "</td>" 
        				+ "<td>"
						+ "<input name='btnRemove' type='button' value='Remove' class='btn btn-sm btn-danger btnRemove' data-userid='" + userID + "'>"
						+ "</td></tr>";
        	}

        	conn.close();
        	
        	// Complete the HTML table
        	Output += "</table>";
        	
    	} catch(Exception e) {
    		Output = "Failed to read the customer";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    
    //DELETE CUSTOMER
    
  //method to delete data
    public String deleteCustomer(String userID) {
    	String Output = "";
    	Connection conn = connect();
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "DELETE FROM customer WHERE userID = ?";
        	
        	//binding data to the SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setInt(1, Integer.parseInt(userID));
        	
        	//executing the SQL statement
        	preparedStatement.execute();
        	conn.close();
        	
        	String newCustomer = readAllCustomer(); 
      		Output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}"; 
        	
    	} catch(Exception e) {
			Output = "{\"status\":\"error\", \"data\":\"Failed to delete the Customer.\"}";
    		System.err.println(e.getMessage());
    	}
    	return Output;
    }
}
