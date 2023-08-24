package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
			st = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)");
			
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
			
			System.out.println("Done! Rows affected: " + rowsAffected);
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
