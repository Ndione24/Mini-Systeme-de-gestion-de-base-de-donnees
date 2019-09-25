package DataBase;

public class DBManager {
	
	private static DBManager instanceUnique ;
	
	public static synchronized DBManager getInstance () {
		
		
		return instanceUnique ;
	}
		
	/**
	 * description: qui fera le nécessaire pour l’initialisation d’une instance
	 * @param
	 */
	public static  void init(){
		DBDef.getInstance().init() ;
	}
	/**
	 * @Description: s'occupe du ménage 
	 */
	public static void finish(){
		DBDef.getInstance().init() ;
	}
	
	/*
	 * @param :commande entrée 
	 */
	public void processCommand(byte command) {
		
	}
	
	/*
	 * @Description: cette méthode créera une RelDef conformément aux arguments et la rajoutera au DBDef
	 * @param :nomRel
	 * @param :nbCol
	 * @param :typeCol
	 */
	
	public void createRelation (String nomRel , int nbCol , String[] typeCol ) {
		
		DBDef.getInstance().addRelation(new RelDef(nomRel , nbCol , typeCol )) ;
	}

	public static void main(String [] args)
	{
		//la création (si besoin) de l’instance de DBManage
		
		init();
		
		do
		{
			
		} while(args[0]!="exit");
	}
}


