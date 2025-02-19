package DataBase;

import java.io.IOException;

public class HeapFile {

	private RelDef relDef;
	
	
	public HeapFile(RelDef relDef) 
	{
		this.relDef=relDef;
	}
	
	// getters pour recuperer le nom de la relation dans HeapFile
	public String nomRelation() {
		return this.relDef.getNomRelation();
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
	 * 
	 * @param record
	 * @param pageId
	 * @return Cette méthode doit écrire record dans la page de données identifiée
	 *         par pageId, et renvoyer son Rid
	 */
	public Rid writeRecordToDataPage(Record record, PageId pageId) {
		/*
		 * On recupere le contenu du buffer de la page de via le buferManager
		 */

		byte[] page = BufferManager.getInstance().getPage(pageId);
		ByteBuffer bf = ByteBuffer.wrap(page);
		int nbslots = relDef.getSlotCount();
		int i = 0;
		boolean isFounded = false;
		// on recherche un slot libre dans la page
		while (i < nbslots && !isFounded) {
			if (page[i] == 1)
				isFounded = true;
			else
				i++;
		}
		record.writeToBuffer(bf, nbslots + i * relDef.getRecordSize());
		// mise à jour de bytemap et HeaderPage
		relDef.setSlotCount(nbslots - 1);
		page[i]--;
		return new Rid(pageId, i);

	}

	/**
	 * Cette méthode doit retourner le PageId d’une page de données qui a encore des
	 * cases libres.
	 */
	public PageId getFreeDataPageId() {
		int indiceFichier = relDef.getFileIdx();
		PageId pageId = new PageId(indiceFichier, 0);
		// je recupère le headerpage
		byte[] page = BufferManager.getInstance().getPage(pageId);

		ByteBuffer b = ByteBuffer.wrap(page);

		int nbPages = (b.getInt(0));
		for (int i = 1; i <= nbPages; i++) {
			if (b.getInt(i * Integer.BYTES) > 0) {
				return new PageId(this.relDef.getFileIdx(), i);
			}
		}
		throw new RuntimeException("No available page");

	}
	
	/**
	 * 
	 * @param pageId le pageId
	 * @return tous les records qui se trouvent dans cette pageId
	 */
	public List<Record> getRecordsInDataPage(PageId pageId) {
		// je recupère le headerpage
		byte[] hp = BufferManager.getInstance().getPage(pageId);

		ByteBuffer bb = ByteBuffer.wrap(hp);

		List<Record> listeRec = new ArrayList<Record>();
		// parcour du bytemap
		for (int i = 0; i < this.relDef.getSlotCount(); i++) {
			// si le slot contient 1 record
			if (bb.get() == (byte) 1) {
				Record rec = new Record(this.relDef);
				rec.readFromBuffer(hp, i);
				listeRec.add(rec);
			}
		}
		return listeRec;
	}

	/**
	 * cette methode insère un record dans une page qui a des slots libres et
	 * retourne son rid
	 */
	Rid insertRecord(Record record) {
		// on recupère une page libre
		PageId pageAvecSlotsLibre = getFreeDataPageId();
		// j'ecris le record dans la page libre que l'on vient de récuperer
		return writeRecordToDataPage(record, pageAvecSlotsLibre);
		
	}
	
	/*
	 * la methode doit lister tous les records dans HeapFile
	 */
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


}
