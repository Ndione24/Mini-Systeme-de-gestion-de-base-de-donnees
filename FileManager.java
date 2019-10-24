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

}
