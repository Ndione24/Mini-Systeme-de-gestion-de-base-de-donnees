package DataBase;
import java.awt.List;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class HeapFileOld {

	private RelDef relDef;
	
	
	public HeapFileOld(RelDef relDef)
	{
		this.relDef=relDef;
	}
	
	
	public String nomRelation() 
	{
		return this.relDef.getNomRelation();
	}
	
	public RelDef getRelDef() 
	{
		return this.relDef;
	}
	
	
	
	public void createNewOnDisk()
	{
		//buff vide
		byte buff[]= new byte[Constants.PAGESIZE];
		
		//index: l'identifiant du fichier
		int index=this.relDef.getFileIdx();
		//creation du fichier disque
		DiskManager.getInstance().createFile(index);
		
		//ajout d'une header page vide 
		
		try {
			//ajout d'une page au fichier nouvellement crée 
			PageId newPage=DiskManager.getInstance().addPage(index);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
		
		
	
		
		public PageId addDataPage() throws IOException{
			//l'indice du fichier 
			int fileIdx=this.relDef.getFileIdx();
			
			//on ajoute une nouvelle page au fichier
			
			PageId nouvellePage=DiskManager.getInstance().addPage(fileIdx);
			
			PageId headerpage= new PageId(fileIdx,0);
			
			HeaderPage hp= new HeaderPage(headerpage);
			
			//on recupere le buffer de la headerpage
			byte[] buffer=BufferManager2.getInstance().getPage(headerpage);
			
			//on l'affecte à hp pour pouvoir actualiser les informations
			hp.setHpBuffer(buffer);
			//actualiser les informations de la Header Page.
			
			//on cree la dataPage de la page  nouvellement crée et on l'ajoute dans la headerPage
			DataPage pg=new DataPage(nouvellePage,this.relDef);
			hp.addDataPageToHeaderPager(pg);
			hp.updateHeaderPageWhenAddDataPage(pg.getFreeSlots());
			
			//on actualise la headerpage maintenant dans le disque
			DiskManager.getInstance().writePage(headerpage, hp.getBuffer());
			
			BufferManager2.getInstance().freePage(nouvellePage,1);
			
			
			return nouvellePage;
			
		}
		
		

		/**
		 * Cette méthode doit retourner le PageId d’une page de données qui a encore des
		 * cases libres.
		 */
		public PageId getFreeDataPageId() {
			int indiceFichier = relDef.getFileIdx();
			//nombre de case
			int nombreDeCases= this.relDef.getSlotCount();
			PageId pageId = new PageId(indiceFichier, 0);
			// je recupère le headerpage
			byte[] page = BufferManager2.getInstance().getPage(pageId);

			ByteBuffer b = ByteBuffer.wrap(page);

			int nbPages = (b.getInt(0));
			
			for (int i = 1; i <= nbPages; i++) {
				if (b.getInt(i) < nombreDeCases) {
					BufferManager2.getInstance().freePage(pageId, 1);
					return new PageId(this.relDef.getFileIdx(), i);
					
				
				}
			}
			
			return null;

		}
		

		
		
		
		/**
		 * 
		 * @param pageId le pageId
		 * @return tous les records qui se trouvent dans cette pageId
		 */
		
		public ArrayList<Record> getRecordsInDataPage(PageId pageId) {
			// je recupère le headerpage
			byte[] hp = BufferManager2.getInstance().getPage(pageId);

			ByteBuffer bb = ByteBuffer.wrap(hp);

			ArrayList<Record> listeRec = new ArrayList<Record>();
			// parcour du bytemap
			for (int i = 0; i < this.relDef.getSlotCount(); i++) {
				// 0 libre 1 occupé
				if (bb.get() == 1) {
					Record rec = new Record(this.relDef);
					rec.readFromBuffer(bb, i);
					listeRec.add(rec);
				}
			}
			return listeRec;
		}
		
		
		
		
		public Rid insertRecord (Record record)
		{
		
				// on recupère une page libre
				PageId pageAvecSlotsLibre = getFreeDataPageId();
				// j'ecris le record dans la page libre que l'on vient de récuperer
				System.out.println("insertion");
				
				return writeRecordToDataPage(record, pageAvecSlotsLibre);
				
				//update headerPage
				
			
		}
		
		
		public Rid writeRecordToDataPage(Record record, PageId pageId) {
			/*
			 * On recupere le contenu du buffer de la page de via le buferManager
			 */
			System.out.println(" writeRecordToDataPage");
			
			byte[] page = BufferManager2.getInstance().getPage(pageId);
			
			ByteBuffer bf = ByteBuffer.wrap(page);
			int nbslots = relDef.getSlotCount();
			int i = 0;
			boolean isFounded = false;
			// on recherche un slot libre dans la page
			while (i < nbslots && !isFounded) {
				//0 s'il ya une case libre
				if (page[i] == 0)
					isFounded = true;
				else
					i++;
			}
			
			record.writeToBuffer(page, nbslots + i * relDef.getRecordSize());
			// mise à jour de bytemap et HeaderPage
			relDef.setSlotCount (nbslots - 1);
			page[i]++;
			
			//on sauvegarde les modification sur la page
			DiskManager.getInstance().writePage(pageId, page);
			BufferManager2.getInstance().freePage(pageId, 1);
			
			//on decremente le nombre de freeSlots dans cette page depuis la headerpage
			PageId hp = new PageId(this.relDef.getFileIdx(),0);
			byte [] bufferHeaderPage= BufferManager2.getInstance().getPage(hp);
			
			HeaderPage headerpage= new HeaderPage(hp);
			headerpage.setHpBuffer(bufferHeaderPage);
			headerpage.updateHeaderPageWhenWriteRecordToDataPage(pageId.getPageIdx());
			byte [] buff=headerpage.getBuffer();
			DiskManager.getInstance().writePage(hp, buff);
		
			//liberer la header page
			return new Rid(pageId, i);

		}
		/*private Rid writeRecordToDataPage(Record record, PageId pageAvecSlotsLibre){
			
			byte[] buffer=BufferManager2.getInstance().getPage(pageAvecSlotsLibre);
			
			
			return null;
		} */


		public ArrayList<Record> getAllRecords()
		{
			ArrayList<Record> listeDeRecords= new ArrayList<>();
			
			int fileIdx=this.relDef.getFileIdx();
			
			//on recupere la headerpage
			PageId hpId=new PageId(fileIdx,0);
			
			byte[] buff =BufferManager2.getInstance().getPage(hpId);
			//il nous faut le nombre de page dse trouvant dans la headerPage
			HeaderPage hp= new HeaderPage(hpId);
			hp.setHpBuffer(buff);
		
			
			//i=1 car pageId 0 correspond à la headerPage
			for(int i=1;i<hp.getDataPageCount();i++) 
			{
				PageId pg =new PageId(this.relDef.getFileIdx(),i);
				listeDeRecords.addAll(getRecordsInDataPage(pg));
			}
			
			
			return listeDeRecords;
		}
	
		
}
