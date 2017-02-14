/**
 * 
 */
package fr.emile.iam.tests;

import java.io.FileNotFoundException;
import java.util.List;

import fr.emile.iam.datamodel.Identity;
import fr.emile.iam.services.FileIdentityDAO;

/**
 * @author emilehoyek
 *
 */
public class TestFiles {

	public static void main(String[] args) throws FileNotFoundException {
		
	

		FileIdentityDAO identityDAO = new FileIdentityDAO("tests.txt");
		identityDAO.write(new Identity("123", "Thomas Broussard", "thomas.broussard@gmail.com", "23-05-1994" ));
		identityDAO.write(new Identity("456", "Emile Hoyek", "emilehoyek24@gmail.com", "24-05-1994"));

		
		List<Identity> list = identityDAO.readAllIdentities();
		System.out.println(list);

	}

}
