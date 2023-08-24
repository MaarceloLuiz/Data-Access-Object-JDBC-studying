package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			
			//? = place holder
			//we will insert values on it
			
			//inserting the new value and return the 'Id' number
			st = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					//prepareStatement overload
					Statement.RETURN_GENERATED_KEYS);
			
			//replacing the first '?' by a String (Name)
			st.setString(1, "Carl Purple");
			//replacing the second '?' by a String (Email)
			st.setString(2, "carl@gmail.com");
			//replacing the third '?' by a Date (BirthDate)
			//we have to use 'java.sql.Date()'
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
			//replacing the forth '?' by a Double (BaseSalary)
			st.setDouble(4, 3000.0);
			//replacing the fifth '?' by a Integer (Email)
			st.setInt(5, 4);
			
			//we are updating the data, so we have to use 'executeUpdate()'
			//the result is an Integer number depending on how many line were changed 
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				//this function return an object of type ResultSet
				ResultSet rs = st.getGeneratedKeys();
				while(rs.next()) {
					//only 1 column including all the 'Id' 
					//i.e., we need only the value of the first column
					int id = rs.getInt(1);
					
					System.out.println("Done! Id = " + id);
					
					//to check if it worked, we should go to mySQL Workbench and check the 'seller' tables
				}
			}
			else {
				System.out.println("No rows affected!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		//parseException otherwise the Date parse won't work
		catch(ParseException e) {
			e.printStackTrace();
		}
		//closing our resources
		//always close connection last
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
			
			//to check if it worked, we should go to mySQL Workbench and check the 'seller' tables
		}

	}
}
