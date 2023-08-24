package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;


public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			//instanciar um objeto do tipo 'Statement'
			st = conn.createStatement();
			
			//metodo executeQuery, que utiliza como argumento uma String SQL
			rs = st.executeQuery("select * from department");
			
			//while (is true). Because the 'next' returns false when there's no value left
			while(rs.next()) {
				//getting the 'Int' in the field "Id"
				//getting the 'String' in the field "Name"
				System.out.println(rs.getInt("Id") + " - " + rs.getString("Name"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		//our resources 'Statement', 'ResultSet' they are external resources, so we should close manually all of them to prevent our program to have any 'memory leak' issues
		finally {
			/*
			 * 
			//to prevent using try catch all the time, we decided to create two new static methods in our DB class, so we can close properly our resources.
			rs.close();
			st.close();
			*/
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

	}

}
