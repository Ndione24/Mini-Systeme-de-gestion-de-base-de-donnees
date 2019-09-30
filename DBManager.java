package projetBdd;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//lhoumeau.maxmime@gmail.com
public class DBManager {
	/**
	 * Instance pour creer une instance unique de la classe
	 */
	private static DBManager INSTANCE;

	/**
	 * 1er constructeur le la classe DBManager, il se peut qu'on en ait besoin
	 */
	private DBManager() {
		INSTANCE = new DBManager();
	}

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
		// decoupage de la ligne de commande en plusieurs mots
		StringTokenizer st = new StringTokenizer(cmde);

		// création d'une liste dans laquelle sera stocker les mots decompsés
		List<String> mots = new ArrayList<String>();

		// Boucle de parcours du StringTokenizer
		while (st.hasMoreTokens()) {

			// Ajout du mot dans la liste
			mots.add(st.nextToken());
		}
		//Maintenant on met les types dans un autre vecteur
		ArrayList<String> lesTypes = new ArrayList<String>();
		for(int i=3;i<mots.size();i++) {
			lesTypes.add(mots.get(i));
		}

		// Gestion des mots clés avec switch
		switch (mots.get(0)) {

		case "create":
			// Créer d'une rélation
			createRelation(mots.get(1) ,Integer.parseInt(mots.get(2)), lesTypes);
			break;
	/*	case "insert":
			// Inserer un Record dans une Relation
			insert(mots);
			break;
		case "clean":
			// Fait le ménage général
			clean();
			break;*/

		default:
			// Affiche le message d'erreur
			System.out.println("Erreur: Saisie incorrecte");
			break;
		}

	}

	/**
	 * Cette methode cree une nouvelle relation et la rajoute à l'instance de DBDef
	 * avce les paramètres passés
	 * 
	 * @param nomRelation   : le nom de la relation
	 * @param nbRelation    : le nombre de colonnes
	 * @param typesColonnes : lestype des colonnes
	 */
	public void createRelation(String nomRelation, int nbRelation, List<String> typesColonnes) {
		RelDef relation = new RelDef(nomRelation, nbRelation);
		relation.setTypesColonnes(typesColonnes);
		DBDef.getINSTANCE().addRelation(relation);

	}
}
