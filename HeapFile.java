package DataBase;

import java.io.IOException;

public class HeapFile {

	private RelDef relDef;
	
	
	public HeapFile(RelDef relDef) 
	{
		this.relDef=relDef;
	}
	
	
	public void createNewOnDisk()
	{
		//buff vide
		byte buff[]= new byte[Constantes.PAGESIZE];
		
		//index: l'identifiant du fichier
		int index=this.relDef.getFileIdx();
		//creation du fichier disque
		DiskManager.getInstance().createFile(index);
		
		//ajout d'une header page vide 
		
		try {
			//ajout d'une page au fichier nouvellement crée  (A)
			PageId newPage=DiskManager.getInstance().addPage(index);

			
			
			
			//recuperer la page via le buffermanager
			BufferManager2.getInstance().getPage(newPage);
			
			DiskManager.getInstance().writePage(newPage, buff);
			
			BufferManager2.getInstance().freePage(newPage,0);//A revoir
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		public PageId addDataPage()
		{
			int fileIdx=this.relDef.getFileIdx();
			PageId nouvellePage=DiskManager.getInstance().addPage(fileIdx);
			
			//on recupere la page via le buffermanager 
			BufferManager2.getInstance().getPage(nouvellePage);
			
			BufferManager2.getInstance().freePage(nouvellePage,1);
			
			
			
			return nouvellePage;
			
		}
		
		
	}
}
