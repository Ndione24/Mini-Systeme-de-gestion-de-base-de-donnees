package projetBdd;

import java.util.List;

public class DBManager {

	/**
	 * 1er constructeur le la classe DBManager, il se peut qu'on en ait besoin
	 */
	private DBManager() {

	}

	/**
	 * Instance pour creer une instance unique de la classe
	 */
	private static DBManager INSTANCE;

	/**
	 * cette méthode retourne une Instance unique de cette classe synchronized :
	 * pour pourvoir le partager entre plusieurs threads
	 */
	public static synchronized DBManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DBManager();
			return INSTANCE;
		} else
			return INSTANCE;
	}

	/********** Definition de méthode de la classe ************/
	/**
	 * @description : s'occupe de l'initialisation d'une instance
	 */

	public void init() {
		// Appel à la methode init de la classe DBDef
		DBDef.getINSTANCE().init();

	}

	/**
	 * @description : s'occupe du ménage
	 */
	public void finish() {
		// Appel à la méthode finish() de la classe DBDef
		DBDef.getINSTANCE().finish();
	}

	/**
	 * 
	 * @param cmde
	 */

	public void processCommand(String cmde) {

	}

	public void createRelation(String nomRelation, int nbRelation, List<String> typesColonnes) {

	}
}
