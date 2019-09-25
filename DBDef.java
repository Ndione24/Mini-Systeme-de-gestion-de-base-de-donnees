package DataBase;
import java.util.*;

public class DBDef {
	
	private int compteur;
	private ArrayList<RelDef> listeRelDef ;
	private static DBDef instanceUniqueDBDef ;
	
	public DBDef(ArrayList<RelDef> listeRelDef, int compteur ) 
	{
		this.listeRelDef=new ArrayList<RelDef> ();
		this.compteur=compteur;
		
	}
	
	public void init () {
	}
	
	public void finish () {
	}
	
	public void addRelation (RelDef rel ) {
		listeRelDef.add(rel) ;
		compteur++ ;
		
	}
}
