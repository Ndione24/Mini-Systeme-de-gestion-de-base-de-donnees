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
		//appel de la methode init() de la classe HeapFile
		FileManager.getInstance().init();

	}

	/**
	 * @description : s'occupe du ménage
	 */
	public void finish() {
		// Appel à la méthode finish() de la classe DBDef
		DBDef.getINSTANCE().finish();
		// Appel à la méthode flushAll() de la classe BufferManager
		BufferManager.getInstance().flushAll();
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
		// Maintenant on met les types dans un autre vecteur
		ArrayList<String> lesTypes = new ArrayList<String>();
		for (int i = 3; i < mots.size(); i++) {
			lesTypes.add(mots.get(i));
		}

		// Gestion des mots clés avec switch
		switch (mots.get(0)) {

		case "create":
			// Créer d'une rélation
			createRelation(mots.get(1), Integer.parseInt(mots.get(2)), lesTypes);
			break;
		/*
		 * case "insert": // Inserer un Record dans une Relation insert(mots); break;
		 * case "clean": // Fait le ménage général clean(); break;
		 */

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

		// on va rajouter les calculs demandés au niveau
		// du tp4 pour mettre à jour recodSize et slotCount dansRelDef
		int recordSize = 0;
		// On parcours le tableau de types
		for (String type : typesColonnes) {
			// si c'est un entier, on ajoute 4 qui est la taille d'un int en java
			if (type.equals("int"))
				// On met la taille d'un int
				recordSize += 4;// ou Integer.byteValue() mais pas reconnu par tous les compilateurs
			// Si c'est un type float
			else if (type.equals("float"))
				// Mettre 4 octets
				recordSize += 4;
			// si c'est un char on ajoute 2
			else if (type.equals("char"))
				recordSize += 2;
			else {
				// on recupere la taille de la chaine
				// càd le chiffre à la fin du string
				// exple string7 donc 7
				String reste = type.replace("string", "").trim();
				// Mettre 2 octets par caractère
				recordSize += (Integer.parseInt(reste) * 2);
			}
		}
		/*
		 * Maintenant on mets à jour les valeurs des varaibles recordSize et slotCount
		 * dans la classe RelDef
		 */
		RelDef relation = new RelDef(nomRelation, nbRelation);

		relation.setRecordSize(recordSize);
		/*
		 * le nombre de slot sera la taille de la page divisé par le recodSize
		 */
		relation.setSlotCount(Constants.PAGESIZE / (recordSize+1));

	

		// Ajout des types de colonnes de la rélation
		relation.setTypesColonnes(typesColonnes);
		DBDef.getINSTANCE().addRelation(relation);

	}
}
