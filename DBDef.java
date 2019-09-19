package DataBase;
import java.util.*;

public class DBDef {
	private int compteur;
	private ArrayList<RelDef> al;
	
	public DBDef(ArrayList<RelDef> al, int compteur ) 
	{
		this.al=new ArrayList<RelDef> ();
		this.compteur=compteur;
		
	}
}
