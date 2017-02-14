/**
 * 
 */
package fr.emile.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.emile.iam.datamodel.Identity;
import fr.emile.iam.exceptions.DaoInitializationException;

/**
 * @author emilehoyek
 *
 */
public class IdentityJDBCDAO {

	private Connection currentConnection;

	/**
	 * create connection with the database
	 */
	public IdentityJDBCDAO() throws DaoInitializationException {
			
		try {
			getConnection();
		} catch (SQLException e) {
			DaoInitializationException die = new DaoInitializationException();
			die.initCause(e);
			throw die;
		}
	}

	/**
	 * get connection from the database
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		try {
			this.currentConnection.getSchema();
		} catch (Exception e) {
			//database connection
			String user = "user";
			String password = "emile";
			String connectionString = "jdbc:derby://localhost:1527/sample;create=true";
			this.currentConnection = DriverManager.getConnection(connectionString, user, password);
		}
		return this.currentConnection;
	}

	/**
	 * close connection with database
	 */
	private void releaseResources() {
		try {
			this.currentConnection.close();
		} catch (Exception e) {
			System.out.println("Connection Error");
		}
	}

	/**
	 * Read all the identities from the database
	 * @return
	 * @throws SQLException
	 */
	public List<Identity> readAllIdentities() throws SQLException {
		List<Identity> identities = new ArrayList<Identity>();

		Connection connection = getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			int uid = rs.getInt("IDENTITY_ID");
			String displayName = rs.getString("IDENTITY_DISPLAYNAME");
			String email = rs.getString("IDENTITY_EMAIL");
			String birthdate = rs.getString("IDENTITY_BIRTHDATE");
			Identity identity = new Identity(String.valueOf(uid), displayName, email, birthdate);
			identities.add(identity);
		}

		return identities;
	}

	/**
	 * write an identity in the database
	 * @param identity
	 * @throws SQLException
	 */
	public void write(Identity identity) throws SQLException {
		Connection connection = getConnection();

		String sqlInstruction = "INSERT INTO IDENTITIES(IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, IDENTITY_BIRTHDATE) VALUES(?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(sqlInstruction);
		pstmt.setString(1, identity.getDisplayName());
		pstmt.setString(2, identity.getEmail());
		pstmt.setString(3, identity.getBirthdate());

		pstmt.execute();
		this.releaseResources();
	}
	
	/**
	 * update user's credentials in the database
	 * @param identity
	 * @throws SQLException
	 */
	public void update(Identity identity) throws SQLException {
		Connection connection = getConnection();

		String sqlInstruction = "UPDATE IDENTITIES SET IDENTITY_DISPLAYNAME=?, IDENTITY_EMAIL=?, IDENTITY_BIRTHDATE=? WHERE IDENTITY_ID=?";
		PreparedStatement pstmt = connection.prepareStatement(sqlInstruction);
		pstmt.setString(1, identity.getDisplayName());
		pstmt.setString(2, identity.getEmail());
		pstmt.setString(3, identity.getBirthdate());
		pstmt.setString(4, identity.getUid());

		pstmt.execute();
		this.releaseResources();
	}

	/**
	 * delete a user in the database
	 * @param identity
	 * @throws SQLException
	 */
	public void delete(Identity identity) throws SQLException {
		Connection connection = getConnection();

		String sqlInstruction = "DELETE FROM IDENTITIES WHERE IDENTITY_ID=?";
		PreparedStatement pstmt = connection.prepareStatement(sqlInstruction);
		pstmt.setString(1, identity.getUid());

		pstmt.execute();
		this.releaseResources();	
	}
	
}
