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
	
	
	
	/**
	*Cette methode supprime tous les fichiers .rf du repertoire DB ainsi que le fichier Catalog.def
	*/
	public void cleanCommand() 
	{
		
		//le repertoire DB
		File repertoire= new File(Constantes.CHEMINDB);
		
		//on met tous les fichiers du repertoire DB dans un tableau de fichiers 
		File[] lesFichiers=repertoire.listFiles();
		
		
		//on supprime tous les fichiers .rf ainsi que le fichier Catatog.def
		
		for(File key : lesFichiers)
		{
		//on supprime les fichiers dont le chemin se termine par ".rf" ou "Catalog.def"
			if(key.getAbsolutePath().endsWith(".rf") || key.getAbsolutePath().endsWith("Catalog.def")) {
				if(key.delete())
				{
					System.out.println(" le fichier "+ key+ " a été bien supprimé");
				}
				else 
				{
					System.out.println("Error: le fichier "+ key+ " n'a pas été supprimé");
				}
		    }
		}
		//System.out.println(Arrays.toString(lesFichiers));
		
		
		BufferManager2.getInstance().reset();
		FileManager.getInstance().reset();
		DBDef.getINSTANCE().reset();
	}
	
		/**
	 * la methode doit inserer une relation 
	 * @param nomRelation : nom de la relation auquel on doit inserer un record 
	 * @param values : les valeures du record à inserer
	 */
	public void insertCommand(String nomRelation,ArrayList<String> values) 
	{
		String nomRelationLowerCase=nomRelation.toLowerCase();
		
		//on cherche le nom de la relation dans le FileManager 
		for(int i=0;i<FileManager.getInstance().getAllHeapFile().size();i++)
		{
		if(FileManager.getInstance().getAllHeapFile().get(i).getRelDef().getNomRelation().toLowerCase().equals(nomRelationLowerCase)) 
		{
			//si le nom de la relation a ete trouvé, on cree un nouveau record et on l'insere
			Record newRecord= new Record(DBDef.getINSTANCE().listeRelDef.get(i),values);
			
			FileManager.getInstance().getAllHeapFile().get(i).InsertRecord(newRecord);
			break;
			
			
		}
		}
		
	}
	
	
	public void insertAllCommand(String nomRelation,String nomFichierCsv) 
	{
	
	
		//chemin du fichier
		String cheminFileCsv=Constantes.CHEMINRACINE+nomFichierCsv;
		
		String ligne=null;
		
		
		try {
			BufferedReader br= new BufferedReader(new FileReader (cheminFileCsv));
			
			//ligne est une chaine de caratere correspondant à une ligne( un record) du fichier CSV
			while((ligne=br.readLine())!=null)
			{
				ArrayList<String> values =new ArrayList<>();
				//les valeurs du record sont separé par une virgule
				StringTokenizer st = new StringTokenizer(ligne,",");
				 while (st.hasMoreTokens()) {  
			         values.add(st.nextToken());
			     }
				 insertCommand(nomRelation,values);
				 
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectAllCommand(String nomRelation) 
	{
		String nomRelationLowerCase=nomRelation.toLowerCase();
		//on cherche le nom de la relation dans le FileManager 
		for(int i=0;i<FileManager.getInstance().getAllHeapFile().size();i++)
		{
		if(FileManager.getInstance().getAllHeapFile().get(i).getRelDef().getNomRelation().toLowerCase().equals(nomRelationLowerCase)) 
		{
			//on recupere la liste de record associée à cette relation
			List<Record> listDeRecords=FileManager.getInstance().getAllHeapFile().get(i).GetAllRecords();
			
			//on affiche maintenant les valeurs des records separés par un point virgule
			
			int nb=0;
			for(Record item: listDeRecords) 
			{
				String ligneRecord="";
				for (int j=0;j<item.getValues().size();j++)
				{
					if(j==(item.getValues().size()-1))
					{
						//pour eviter d'avoir un point virgule à la fin du record
						ligneRecord=ligneRecord+item.getValues().get(j);
					}
					else 
					{
						ligneRecord=ligneRecord+item.getValues().get(j)+";";
					}
				}
				
				System.out.println("record n°"+(nb+1)+ " "+ligneRecord);
				nb++;
			}
			
			System.out.println("Total records = "+nb);
			
			}
		}
		
	}
	
	
		public void selectCommand(String nomRelation,int indiceColonne,String valeur) 
	{
		//on recupere la liste des records selectionnés
		ArrayList<Record> recordSelectione=FileManager.getInstance().SelectFromRelation(nomRelation, indiceColonne, valeur);
	
		int nb=0;
		for(Record item: recordSelectione) 
		{
			String ligneRecord="";
			for (int j=0;j<item.getValues().size();j++)
			{
				if(j==(item.getValues().size()-1))
				{
					//pour eviter d'avoir un point virgule à la fin du record
					ligneRecord=ligneRecord+item.getValues().get(j);
				}
				else 
				{
					ligneRecord=ligneRecord+item.getValues().get(j)+";";
				}
			}
			
			System.out.println("record n°"+(nb+1)+ " "+ligneRecord);
			nb++;
		}
		
		System.out.println("Total records = "+nb);
	}
	
	
}
