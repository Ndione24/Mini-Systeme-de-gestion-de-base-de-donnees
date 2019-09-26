package projetBdd;

import java.util.ArrayList;

public class DBDef {

	private int compteur;
	private ArrayList<RelDef> listeRelDef;
	private static DBDef INSTANCE = new DBDef();

	public DBDef(ArrayList<RelDef> listeRelDef, int compteur) {
		this.listeRelDef = new ArrayList<RelDef>();
		this.compteur = compteur;

	}

	/**
	 * Constructeur sans argument qui initialise les attributs
	 */
	private DBDef() {
		listeRelDef = new ArrayList<RelDef>();
		compteur = 0;
	}

	/**
	 * <i>L'accès à l'unique Instance Instance de la classe</i>
	 * 
	 * @return <i>Unique instance</i>
	 */
	public static DBDef getINSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new DBDef();
		}
		return INSTANCE;
	}

	public void init() {
	}

	public void finish() {
	}

	/**
	 * Cette metode ajoute la relation rel dans la liste si elle la contient pas
	 * d'abord
	 * 
	 * @param rel : la nouvelle relation à ajouter
	 */
	public void addRelation(RelDef rel) {
		if (!listeRelDef.contains(rel)) {
			listeRelDef.add(rel);
			compteur++;
		} else {
			System.out.println("La liste contient déjà cette relation");
		}

	}
}
