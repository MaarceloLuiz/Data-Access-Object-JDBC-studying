package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = DB.getConnection();
			
			//operations will not be confirmed automatically
			conn.setAutoCommit(false);
			
			st = conn.createStatement();
			
			//Updating every salary of sellers in the Department 1 to 2090
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			int x = 1;
			if(x<2) {
				throw new SQLException("fake error");
			}
			
			//Updating every salary of sellers in the Department 2 to 3090
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			//after all the operations are finished, we can call the 'commit' to confirm that now it's done.
			conn.commit();
			
			System.out.println("rows1 " + rows1);
			System.out.println("rows2 " + rows2);
		}
		catch(SQLException e) {
			try {
				//in case we have a problem in the middle of the operation, we can use rollback
				//rollback is used to go back the transaction in case it find an error in the middle of the process
				conn.rollback();
				//we are now warning the application that a rollback was made
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			}
			catch (SQLException e1) {
				//in that case, we found an error in the rollback by it self, so we also have to we have to make a personalized warning
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
			}
		}
		//closing the resources
		finally {
			DB.closeStatement(st);
			DB.closeConnection();

			//to check if it worked, we should go to mySQL Workbench and check the 'seller' tables
		}
		
		//now, our 'fake error' will pop out and the transaction will be rolled back
	}
}
