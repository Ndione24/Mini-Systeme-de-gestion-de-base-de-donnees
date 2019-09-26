package DataBase;
import java.util.Scanner;
public class Main {
	public static void main(String [] args) 
	{
		DBManager dbmanager= DBManager.getInstance();
		dbmanager.init();
		Scanner scann = new Scanner(System.in);
		String command;
		
		
		System.out.println("Veuillez entrez votre commande");
		command=scann.nextLine();
		dbmanager.processCommand(command);
		
	}

}
