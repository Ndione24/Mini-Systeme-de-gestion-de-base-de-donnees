package projetBdd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

//on implemente la classe serializable pour pouvoir effectuer une sauvegarde
public class DBDef implements Serializable {

	/**
	 * 
	 */
	private String cheminDuFichier = "home/users/pc/Documents/eclipseWorkSpaceJava/ProjetMiniSGBD/BD";
	private static final long serialVersionUID = 1L;
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
		/*
		 * Si le repertoire ne contient pas le fichier catalog alors on fait rien
		 */
		if (!cheminDuFichier.contains("Catalog.def")) {
			System.out.println("Rien à restaurer !");
		} else {
			try {
				FileInputStream fistream = new FileInputStream(cheminDuFichier + "Catalog.def");
				ObjectInputStream oistream = new ObjectInputStream(fistream);
				/*
				 * on ecrit maintenant le contenu du fichier catalog.def dans l'instance de
				 * DBdef
				 */
				DBDef dbdef = getINSTANCE();
				dbdef = (DBDef) oistream.readObject();

				/*
				 * on met à jour de l'objet actuel par celui du fichier
				 */
				if (!dbdef.listeRelDef.isEmpty()) {
					setListeRelDef(dbdef.getListeRelDef());
					setCompteur(dbdef.getCompteur());
				}
				oistream.close();
			} catch (Exception e) {
				System.out.println("Erreur survenue à");

			}
		}
	}

	public void finish() {
		FileOutputStream fostream;
		ObjectOutputStream oostream;
		// on verifie l'existence des relations
		if (this.listeRelDef.size() > 0) {
			try {
				// creation du fichier dans le dossier specifié
				fostream = new FileOutputStream(
						"home/users/pc/Documents/eclipseWorkSpaceJava/ProjetMiniSGBD/BD" + "Catalog.def");
				// ecriture de l'objet dans le fichier catalog
				oostream = new ObjectOutputStream(fostream);
				oostream.writeObject(getINSTANCE());
				// maintenant on ferme le flux
				oostream.close();
			} catch (FileNotFoundException e) {

				System.out.println("Le fichier n'a pas été trouvé " + e.getMessage());
			} catch (IOException e) {

				System.out.println("Erreur Catalog.def " + e.getMessage());
				e.printStackTrace();
			}
		}
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

	public int getCompteur() {
		return compteur;
	}

	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}

	public void setListeRelDef(ArrayList<RelDef> listeRelDef) {
		this.listeRelDef = listeRelDef;
	}

	public ArrayList<RelDef> getListeRelDef() {
		return this.listeRelDef;
	}
}
