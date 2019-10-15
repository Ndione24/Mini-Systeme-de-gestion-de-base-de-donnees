package DataBase;
import java.util.ArrayList;
import java.util.List;
public class BufferManager2 {
	

	

	
		

	
		private List<Frame> bufferpool ;
		private List<Frame> pagesLRU;

		// constructeur du singleton
				private BufferManager2() {
				
				this.bufferpool = new ArrayList<Frame>(2);
				this.pagesLRU = new ArrayList<>(2);
				
				
				}

				
		private static BufferManager2 INSTANCE= new BufferManager2();
		
		public static  BufferManager2 getInstance() {
			return INSTANCE ;
		}

		

		// liste des frames pour avoir un historique des pages à remplacer

		List<Frame> listeDesPagesPourLRU = new ArrayList<Frame>();
		
		public void sizeBuffer() 
		{
			System.out.println("taille du bufferpool: "+this.bufferpool.size());
			System.out.println("taille de la liste LRU: "+this.pagesLRU.size());
		}
		
		
		public void addFrame(Frame alpha) 
		{	
			
			this.bufferpool.add(alpha);
		}
		
		public void removeFrame(int alpha) 
		{
			this.bufferpool.remove(alpha);
			
		}

		public boolean lRuAdd(Frame uneFrame) 
		{
		   /*	if(this.bufferpool.size()==2)
			{
				System.out.println("Liste Lru pleine ");
				return false;
			} */
			
		
			boolean result=this.pagesLRU.add(uneFrame);
			return result;
		}
		
		public boolean lRuRemove(Frame uneFrame) 
		{
			boolean result=this.pagesLRU.remove(uneFrame);
			return result;
		}
		
		
		public void infoFrameBufferpool() 
		{
			for(int i=0;i<this.bufferpool.size();i++) 
			{
				System.out.println();
				System.out.println("Frame  "+(i+1) );
				this.bufferpool.get(i).allInfoFrame();
				System.out.println();
			}
		}
		
		
		public void remplirBuffer(PageId page, byte[] buff) 
		{
			int i=0;
			boolean tr=false;
			while(i<this.bufferpool.size() && !tr) {
			PageId pageId=this.bufferpool.get(i).getIdDeLaPage();
			if (page.getFileIdx() == pageId.getFileIdx() && page.getPageIdx() == pageId.getPageIdx())
			{
				this.bufferpool.get(i).setBuffer(buff);
				tr=true;
			}
			i++;
			}
		}
		/**
		 * cette methode retourne le contenu du buffer associé à une page
		 * 
		 * @param pageId
		 * @return
		 */
		
		
		public  byte[] allFrameAvailable(PageId page,int position) 
		{
			Frame a= new Frame(page);
//Son buffer étant vide pour l'instant, on peut le remplire avec un appel à la méthode read de DiskManager depuis le Test
			
			//charger la frame dans le bufferpool
			
			this.bufferpool.add(a);
			
			
			return this.bufferpool.get(position).getBuffer();
			
		}
		
		public  byte[] oneFrameAvailable(PageId page) 
		{
			
		//on vérifie si la page existe dans le bufferpool 	
		PageId pageId=this.bufferpool.get(0).getIdDeLaPage();
			
		if (page.getFileIdx() == pageId.getFileIdx() && page.getPageIdx() == pageId.getPageIdx()) 
		{
			// Si oui,on fait une mise à jour de cette frame
			// incrementer le pin_count
			
			this.bufferpool.get(0).incrementerPinCount();
			
			updateListFrameLRU(this.bufferpool.get(0));
			
			// Signaler que la case a été chargée
			this.bufferpool.get(0).setEstCharge(true);
			
			return this.bufferpool.get(0).getBuffer();
		}

		else 
		{
			return allFrameAvailable(page,1);
			
		}
			
		}
		
		
		public  byte[] noFrameAvailable(PageId page) 
		{
			//une verification
			for(Frame item : this.bufferpool)
			{
				//si la page existe déjà, on fait une mise à jour de cette frame et on retourne son buffer
				
				if (page.getFileIdx() == item.getIdDeLaPage().getFileIdx() && page.getPageIdx() ==item.getIdDeLaPage().getPageIdx()) 
				{
						item.incrementerPinCount();
						updateListFrameLRU(item);
						return item.getBuffer();
						
				}
				
			}
			
			//Sinon proceder au remplacement LRU
			
			return meaningLru(page);
			
			
			
		
		}
		public byte[] getPage(PageId pageId) {

			int size=this.bufferpool.size();
			
			switch(size)
			
			{
			case 0:
				return allFrameAvailable(pageId,0);
				
			case 1:
				return oneFrameAvailable(pageId);
				
			case 2:
				return noFrameAvailable(pageId);
				
			}
			
			byte []a =null;
			return a;
			
		}
			
			
			
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * cette methode retourne le contenu du buffer associé à une page
		 * 
		 * @param pageId
		 * @return
		 */
		/*public byte[] getPageOld(PageId pageId) {

			boolean tr = false;
			// indice de la boucle while
			int i = 0;

			System.out.print(this.bufferpool.size());
			
			
			
			 Recherche le buffer correspondant à la page reçue en paramètre
			 
			while (!tr && i<this.bufferpool.size()){
				Frame alpha=this.bufferpool.get(i);
				
				
				
				// Récupérer la page du bufferPool
				PageId page =alpha.getIdDeLaPage();

				// Si la page se trouve dans le bufferPool
				if (page.getFileIdx() == pageId.getFileIdx() && page.getPageIdx() == pageId.getPageIdx()) {

					// incrementer le pin_count
					this.bufferpool.get(i).incrementerPinCount();
					;

					// mettre à jour la liste PagesLRU
					miseAJourLRU(this.bufferpool.get(i));

					// Signaler que la case a été chargée
					this.bufferpool.get(i).setEstCharge(true);
					;

					// Mise à true la variable boolenne, ce qui signifie que la page a été trouvée
					tr = true;
				}
				else {
				// incrementer l'indice de parcours
				i++;
				}
			}

			// Si la page existe dans le bufferPool
			if (tr) {
				// retourner la page
				
				System.out.println("Trouvé");
				byte [] a = {1};
				return this.bufferpool.get(i).getBuffer();
				//return a ;
			}

			// Si la page n'existe pas dans le bufferPool
			else {
				// Appel de la méthode de remplacement de pages
				return remplacementLRU(pageId);
			}

		} */

		
		/**
		 * <i>Cette méthode permet de libérer une page</i>
		 * 
		 * @param pid   <i>La page à libérer</i>
		 * 
		 * @param dirty <i>Correspond au flag dirty(qui specifie si la page a été
		 *              modifiée ou non)</i>
		 */
		
		
		
		
		
		public void freePage(PageId pid, int dirty) {
			/*
			 * utilisée pour arrêter la boucle while une fois que l'élément recherché est
			 * trouvé
			 */
			boolean tr = false;

			// indice de la boucle while
			int i = 0;

			// Recherche de la page dans le bufferPool et mise à jour des variables
			// pin_count et dirty
			while (i < this.bufferpool.size() && !tr) {

				// Récupération de page dans le bufferPool
				PageId page = this.bufferpool.get(i).getIdDeLaPage();

				// Comparaison entre la page du buffer et celle reçue en paramètre
				if (page.getFileIdx() == pid.getFileIdx() && page.getPageIdx() == pid.getPageIdx()) {

					// si Pin_count >0
					if (this.bufferpool.get(i).getPin_count() > 0)
						// décrementer le pin_count
						this.bufferpool.get(i).decrementerPinCount();
					/*
					 * Gestion du flag dirty ne pas mettre à 0 dirty si la page a été modifiée
					 * 
					 */
					// si la page n'a pas été modifiée
					if (dirty == 0 && bufferpool.get(i).getFlagDirty() == 0)
						// Mettre à jour le dirty
						this.bufferpool.get(i).setFlagDirty(dirty);

					// Si la valeur du dirty n'est pas 0
					else if (dirty == 1)
						// Mettre à jour le dirty
						this.bufferpool.get(i).setFlagDirty(dirty);

					/*
					 * Ajouter à la liste à la liste de remplacement de pages, la page dont le
					 * pin_count=0;
					 */
					if (this.bufferpool.get(i).getPin_count() == 0)
						// Ajouter dans la liste de remplacement de page
						this.pagesLRU.add(bufferpool.get(i));

					// Mise à true la variable boolenne, ce qui signifie que la page a été trouvée
					tr = true;
				}

				// incrementer l'indice de parcours
				i++;
			}

		}
		
		

		
		
		
		
		
		
		
		public byte [] meaningLru(PageId page ) 
		{
			
			byte [] a =null;
			
			if(this.pagesLRU.isEmpty()) 
			{
				
				System.out.println("La Liste LRU est Vide Pas de remplacement possible");
				return a ;
			}
			
			//On compare la pageId qui se trouve à l'indice 0 de la liste  LRu et supprime sa réference dans le bufferpool 
			PageId pagedeleted= this.pagesLRU.get(0).getIdDeLaPage();
			
			PageId test1 =this.bufferpool.get(0).getIdDeLaPage();
			PageId test2 =this.bufferpool.get(1).getIdDeLaPage();
			
			if(pagedeleted.getFileIdx()==test1.getFileIdx() && pagedeleted.getPageIdx()==test1.getPageIdx()) 
			{
				//On verifie si la flag dirty de cette frame est égale à 1. si oui on ecrit sur le disk
				if(this.bufferpool.get(0).getFlagDirty()==1) 
				{
					//disk 
					DiskManager.getInstance().writePage(test1, this.bufferpool.get(0).getBuffer());
					this.bufferpool.remove(0);
					this.pagesLRU.remove(0);
					return getPage(page);
					
					
				}
				else 
				{
					this.bufferpool.remove(0);
					this.pagesLRU.remove(0);
					return getPage(page);
				}
			}
			
			else if(pagedeleted.getFileIdx()==test2.getFileIdx() && pagedeleted.getPageIdx()==test2.getPageIdx()) 

			{
				if(this.bufferpool.get(1).getFlagDirty()==1) 
				{
					//disk 
					DiskManager.getInstance().writePage(test2, this.bufferpool.get(1).getBuffer());
					this.bufferpool.remove(1);
					this.pagesLRU.remove(0);
					return getPage(page);
					
					
				}
				else 
				{
					this.bufferpool.remove(1);
					this.pagesLRU.remove(0);
					return getPage(page);
				}
			}
			
			else {
				System.out.println("Probleme: aucune réferencement ");
				return a;
			}
		}
		
		
		
		
		
		

		/**
		 * Cette méthode permet de supprimer dans la liste listeDesPagesLRU
		 * 
		 * la case dont le pin_count n'est plus 0;
		 *
		 * @param frame <i>La case à supprimer</i>
		 */
		
		private void updateListFrameLRU(Frame frame) {
			// Récupérer la page du frame
			PageId page = frame.getIdDeLaPage();

			// les variables utilisées dans la recherche
			boolean tr = false;
			int i = 0;
			/*
			 * trouver la page dont le pin_coint n'est plus 0 et la supprimer
			 */
			while (i < this.pagesLRU.size() && !tr) {
				// Récupérer la page
				PageId pageUrl = this.pagesLRU.get(i).getIdDeLaPage();

				// Verifier s'il s'agit bien de la page à supprimer
				if (page.getFileIdx() == pageUrl.getFileIdx() && page.getPageIdx() == pageUrl.getPageIdx()) {
					// Supprimer la page
					this.pagesLRU.remove(i);

					// on met la variable boolenne à true,donc la page à supprimer a
					// été trouvée
					tr = true;
				}

				// Incrémenter le compteur
				i++;
			}

		}
		
		
		

		/**
		 * Cette méthode permet d'écrire toutes les pages dont le flag dirty=1 sur
		 * disque et initialise le flag dirty
		 */
		public void flushAll() {
			// l'indice de parcours
			int i = 0;

			// Boucle de parcours du bufferPool
			while (i < this.bufferpool.size()) {
				// Si dirty=1
				if (this.bufferpool.get(i).getFlagDirty() == 1) {
					// Récupérer la frame
					Frame frame = this.bufferpool.get(i);

					// Ecrire les pages dirty sur disque
					DiskManager.getInstance().writePage(frame.getIdDeLaPage(), frame.getBuffer());
				}

				// incrementer l'indice de parcours
				i++;
			}
			// initialisation
			initialiser();

		}

		/**
		 * Cette méthode initialise les attributs de cette classe
		 */
		public void initialiser() {
			// Initialiser le bufferpool et la liste des page pour LRU
			this.bufferpool.clear();
			this.pagesLRU.clear();

		}

	

}
