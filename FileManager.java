package projetBdd;

import java.util.ArrayList;

public class FileManager {
	// instance unique
		private static FileManager INSTANCE;
		private ArrayList<HeapFile> heapFiles;

		//
		public static FileManager getInstance() {
			if (INSTANCE == null) {
				INSTANCE = new FileManager();
				return INSTANCE;
			} else
				return INSTANCE;
		}
		
		/*
		 * la methode parcour et insert les relDef dans l'objet HeapFile 
		 * 
		 */
		public void init() {
			for(int i=0; i<DBDef.getINSTANCE().getListeRelDef().size(); i++) {
				DBDef.getINSTANCE().getListeRelDef().get(i);
				HeapFile hp = new HeapFile(DBDef.getINSTANCE().getListeRelDef().get(i));
				this.heapFiles.add(hp);
			}
		}
		
		/*
		 * @param record la relation a inserer
		 * @param relName le nom correspondant au record a inserer
		 * la methode insert une relation dans un record
		 * 
		 */
		public Rid InsertRecordInRelation(Record record, String relName) {
			for(int i=0; i<this.heapFiles.size(); i++) {
				if(this.heapFiles.get(i).nomRelation().equals(relName)) {
					return this.heapFiles.get(i).insertRecord(record);
				}
			}
			return null;
			
		}
	
	public ArrayList<Record> SelectAllFromRelation(String relName)
	{
		//on parcours HeapFiles pour selectionner le record de nom RelName
		
		HeapFile laRelation;
		
		boolean cherche=false;
		
		
		
		for(int i=0;i<this.heapFiles.size();i++) 
		{
			if(this.heapFiles.get(i).nomRelation().equals(relName)) 
			{
				return this.heapFiles.get(i).GetAllRecords();
				
			}
			
		}
		
	
		return null;
	
		
		
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Record> SelectFromRelation(String relName, int idxCol, String valeur) 
	{
		
		
	
		ArrayList<Record> listeDeRecordsSelectionner=new ArrayList<>();
		boolean cherche=false;
		
		//on verifie si la relation extiste
		for(int i=0;i<this.heapFiles.size();i++) 
		{
			if(this.heapFiles.get(i).nomRelation().equals(relName)) 
			{
				
				
				//si la valeur de du record à la colonne idxCol est égal à valeur, on l'insere la liste qu'on doit retourner
				if(this.heapFiles.get(i).GetAllRecords().get(i).getValues().get(idxCol).equals(valeur)) 
				{
				 listeDeRecordsSelectionner.add(this.heapFiles.get(i).GetAllRecords().get(i));
				}
					
					
			}
			
			
		}
		
		
		
		return listeDeRecordsSelectionner;
		
	
	}
	

}
