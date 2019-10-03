package DataBase;
import java.io.IOException;
import java.util.Scanner;
public class Main {
	public static void main(String [] args) throws IOException 
	{
		DBManager dbmanager= DBManager.getInstance();
		//dbmanager.init();
		
		Scanner scann = new Scanner(System.in);
		String command;
		
		boolean rep=true;
		
		
		
		
		while(rep) {
			System.out.print("Veuillez entrez votre commande >");
			command=scann.nextLine();
			if(command.equals("exit")){
				System.out.println("Au revoir ...");
				rep=false;
				
			}
			else {
			dbmanager.processCommand(command);
			}
		
		
		}
		
		
		
		
	}

}
