/**
 * 
 */
package fr.emile.iam.tests;

import java.sql.SQLException;

import fr.emile.iam.services.IdentityJDBCDAO;

/**
 * @author emilehoyek
 *
 */
public class TestJDBC {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		//The goal is to connect to this url : jdbc:derby://localhost:1527/IAM;create=true
		
		IdentityJDBCDAO dao = new IdentityJDBCDAO();
		System.out.println(dao.readAllIdentities());
		 

	}

}
