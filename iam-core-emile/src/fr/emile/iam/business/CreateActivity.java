/**
 * 
 */
package fr.emile.iam.business;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import fr.emile.iam.datamodel.Identity;
import fr.emile.iam.services.FileIdentityDAO;
import fr.emile.iam.services.IdentityJDBCDAO;

/**
 * @author emilehoyek
 *
 */
public class CreateActivity {
	
	/**
	 * create a user
	 * @param scanner
	 */
	public static void execute(Scanner scanner){
		System.out.println("Identity Creation:");
		//Insert name
		System.out.println("Please input the displayName:");
		String displayName = scanner.next();
		//Insert email
		System.out.println("Please input the email address:");
		String email = scanner.next();
		//Insert birthdate
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Please input the birthdate in the format yyyy-MM-dd:");
		System.out.println("For example, it is now: " + format.format(new Date()));
		
		String birthdate = scanner.next();
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		try {
			calendar.setTime(sdf.parse(birthdate));
		} catch (ParseException e) {
			System.out.println("Birthate Format Incorrect!");
		}
		
		Identity identity = new Identity("",displayName, email, birthdate);
		
		IdentityJDBCDAO identityAdd = new IdentityJDBCDAO();
		
		try {
			//persist the identity somewhere
			System.out.println("Identity Created in a File!");
			FileIdentityDAO identityWriter = new FileIdentityDAO("tests.txt");
			identityWriter.write(identity);
			
			//add user to the database
			identityAdd.write(identity);
			System.out.println("Creation in Database Done");
			
		} catch (SQLException e) {
			System.out.println("Creation in Database Failed");
			}
				
				
	}
	
	/**
	 * Update a user
	 * @param scanner
	 */
	public static void update(Scanner scanner){
		IdentityJDBCDAO identityUpdate = new IdentityJDBCDAO();
		Identity identity = searchUid(identityUpdate,scanner);
		if(identity != null){
			try {
				
				System.out.println("You are about to update the uid: "+identity.getUid());
				
				//update menu
				String choice;
				do{
					System.out.println("Please select an action :");
					System.out.println("a. Change Name");
					System.out.println("b. Change Email");
					System.out.println("c. Change Birthdate");
					System.out.println("d. Quit");
				
					choice = scanner.next();
				
					switch (choice) {
					
					case "a":
						//Change name
						System.out.println("Enter New Name:");
						String displayName = scanner.next();
						identity.setDisplayName(displayName);
						System.out.println("Name Changed!");
						break;
					
					case "b":
						//Change email
						System.out.println("Enter New Email:");
						String email = scanner.next();
						identity.setEmail(email);
						System.out.println("Email Changed!:");
						break;
						
					case "c":
						//Change birthdate
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						System.out.println("Enter New Birthdate in the format yyyy-MM-dd");
						System.out.println("For example, it is now " + format.format(new Date()));
						
						String birthdate = scanner.next();
						
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
						try {
							calendar.setTime(sdf.parse(birthdate));
							identity.setBirthdate(birthdate);
							System.out.println("Birthdate Changed!");
						} catch (ParseException e) {
							System.out.println("Birthate Format Incorrect!");
						}
						break;
						
					case "d":
						//Quit
						break;
						
					default:
						System.out.println("Your choice isn't valid!!");
						break;
					}
					
				}while(!choice.equals("d"));
				
				//Update user in the database
				identityUpdate.update(identity);
			} catch (SQLException e) {
				System.out.println("A Problem Occurred.\nTry Again Later!");
			}
		}
	}
	
	/**
	 * Search for uid
	 * @param identityJDBCDAO
	 * @param scanner
	 * @return
	 */
	public static Identity searchUid(IdentityJDBCDAO identityJDBCDAO,Scanner scanner){
        int id = 0;
        Identity uid = null;
		try {	
			//scan the database for present users
			List<Identity> identities = identityJDBCDAO.readAllIdentities();
			for(Identity identity : identities) {
	            System.out.println(identity.toString());
	        }
			//search for the id
			System.out.println("Please enter the user's ID:");
			try{	
				//checks if the id entered is an integer
				id = scanner.nextInt();
				int exist = 0;
				while(exist == 0){
					//checks if the id corresponds to a user
				    for(Identity identity : identities) {
				    	
				       	int dbId = Integer.parseInt(identity.getUid());
				       	if(dbId == id){
				       		uid = identity; 
				       		exist = 1;
				       		break;
				       	}
				    }
				    if(exist == 0){
				      	System.out.println("Please enter a valid ID:");
				       	id = scanner.nextInt();
				    }
				}
			}catch(InputMismatchException exception){
				System.out.println("The ID entered is incorrect");
			}
			
		} catch (SQLException e) {
			System.out.println("There is no user with that ID");
		}
		return uid;
	}
	
	/**
	 * Delete a user
	 * @param scanner
	 */
	public static void delete(Scanner scanner){
		IdentityJDBCDAO identityDelete = new IdentityJDBCDAO();
		Identity identity = searchUid(identityDelete,scanner);
		//delete menu
		System.out.println("You are deleting the user with the uid: "+identity.getUid());
		System.out.println("Are you sure you want to delete the user? ");
		System.out.println("a. Yes");
		System.out.println("b. No");
		
		String choice = scanner.next();
		
		switch (choice) {
		
		case "a":
			try {
				//delete user form the database
				identityDelete.delete(identity);
				System.out.println("User Deleted Successfully!");
			} catch (SQLException e) {
				System.out.println("A Problem Occurred.\nTry Again Later!");
			}
			break;
			
		case "b":
			//do not delete user
			System.out.println("User hasn't been deleted!");
			break;
			
		default:
			System.out.println("Your choice isn't valid!!");
			break;
		}
		
	}
}

