package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			
			//we still have to set values for our "?"
			st = conn.prepareStatement(
					"DELETE FROM seller "
					//'WHERE' is going to be our restriction, otherwise we will delete (or update) everyone.
					+ "WHERE "
					+ "Id = ?");
			
			//if we tried, for example, to delete the department number 2, the program is going to give us an error.
			//because there's sellers working on that department.
			//we cannot delete a department with sellers linked to it.
			
			//first '?' - value (Id) = 8
			st.setInt(1, 8);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rowsAffected);
		}
		catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		//closing the resources
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
			
			//to check if it worked, we should go to mySQL Workbench and check the 'seller' tables
		}
	}
}
