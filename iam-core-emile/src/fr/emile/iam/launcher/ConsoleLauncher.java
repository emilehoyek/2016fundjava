/**
 * 
 */
package fr.emile.iam.launcher;

import java.util.Scanner;

import fr.emile.iam.business.CreateActivity;

/**
 * @author emilehoyek
 *
 */
public class ConsoleLauncher {

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the IAM software");
		Scanner scanner = new Scanner(System.in);
		
		//authentication
		if (!authenticate(scanner)){
			end(scanner);
			return;
		}
		
		String choice;
		//menu
		do{
		System.out.println("Please select an action :");
		System.out.println("a. Create an Identity");
		System.out.println("b. Modify an Identity");
		System.out.println("c. Delete an Identity");
		System.out.println("d. Quit");
		
		choice = scanner.next();

		switch (choice) {
		case "a":
			//Create
			CreateActivity.execute(scanner);
			break;
		case "b":
			//Modify
			CreateActivity.update(scanner);
			break;
			
		case "c":
			//Delete
			CreateActivity.delete(scanner);
			break;
			
		case "d":
			//Quit
			break;
			
		default:
			System.out.println("Your choice is not recognized");
			break;
		}
		}while(!choice.equals("d"));

		
		end(scanner);
	}

	/**
	 * end application
	 * @param scanner
	 */
	private static void end(Scanner scanner) {
		System.out.println("Thanks for using this application, good bye!");
		scanner.close();
	}

	/**
	 * authenticate
	 * @param scanner
	 */
	private static boolean authenticate(Scanner scanner) {
		System.out.println("Please type your login : ");
		String login = scanner.nextLine();
		
		System.out.println("Please type your password : ");
		String password = scanner.nextLine();
		
		if (login.equals("adm") && password.equals("pwd")){
			System.out.println("Athentication was successful");
			return true;
		}else{
			System.out.println("Athentication failed");
			return false;
		}
	}

}
