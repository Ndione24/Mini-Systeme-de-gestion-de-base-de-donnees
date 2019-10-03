package projetBdd;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class BufferManager {

	// Question pour le prof: Qu'est-ce qu'un bufferpool?
	private List<Frame> bufferpool = new ArrayList(2);

	// liste des frames pour avoir un historique des pages à remplacer

	List<Frame> listeDesPagesPourLRU = new ArrayList<Frame>();
/**
 * cette methode retourne le contenu du buffer associé à une page 
 * @param pageId
 * @return
 */
	public byte[] getPage(PageId pageId) {

		boolean tr = false;
		// indice de la boucle while
		int i = 0;

		/*
		 * Recherche le buffer correspondant à la page reçue en paramètre
		 */
		while (!tr) {

			// Récupérer la page du bufferPool
			PageId page = bufferpool.get(i).getIdDeLaPage();

			// Si la page se trouve dans le bufferPool
			if (page.getFileIdx() == pageId.getFileIdx() && page.getPageIdx() == pageId.getPageIdx()) {

				// incrementer le pin_count
				bufferpool.get(i).incrementerPinCount();
				;

				// mettre à jour la liste PagesLRU
				miseAJourLRU(bufferpool.get(i));

				// Signaler que la case a été chargée
				bufferpool.get(i).setEstCharge(true);
				;

				// Mise à true la variable boolenne, ce qui signifie que la page a été trouvée
				tr = true;
			}
			// incrementer l'indice de parcours
			i++;

		}

		// Si la page existe dans le bufferPool
		if (tr) {
			// retourner la page
			return bufferpool.get(i).getBuffer();
		}

		// Si la page n'existe pas dans le bufferPool
		else {
			// Appel de la méthode de remplacement de pages
			return remplacementLRU(pageId);
		}

	}

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
		while (i < bufferpool.size() && !tr) {

			// Récupération de page dans le bufferPool
			PageId page = bufferpool.get(i).getIdDeLaPage();

			// Comparaison entre la page du buffer et celle reçue en paramètre
			if (page.getFileIdx() == pid.getFileIdx() && page.getPageIdx() == pid.getPageIdx()) {

				// si Pin_count >0
				if (bufferpool.get(i).getPin_count() > 0)
					// décrementer le pin_count
					bufferpool.get(i).decrementerPinCount();
				/*
				 * Gestion du flag dirty ne pas mettre à 0 dirty si la page a été modifiée
				 * 
				 */
				// si la page n'a pas été modifiée
				if (dirty == 0 && bufferpool.get(i).getFlagDirty() == 0)
					// Mettre à jour le dirty
					bufferpool.get(i).setFlagDirty(dirty);

				// Si la valeur du dirty n'est pas 0
				else if (dirty != 0)
					// Mettre à jour le dirty
					bufferpool.get(i).setFlagDirty(dirty);

				/*
				 * Ajouter à la liste à la liste de remplacement de pages, la page dont le
				 * pin_count=0;
				 */
				if (bufferpool.get(i).getPin_count() == 0)
					// Ajouter dans la liste de remplacement de page
					listeDesPagesPourLRU.add(bufferpool.get(i));

				// Mise à true la variable boolenne, ce qui signifie que la page a été trouvée
				tr = true;
			}

			// incrementer l'indice de parcours
			i++;
		}

	}

	/**
	 * <i> Cette méthode applique la politique de remplacement de pages LRU</i>
	 * 
	 * @param idp
	 * 
	 * @return retourne le buffer de la page remplacée si possible ou null si non
	 */
	private byte[] remplacementLRU(PageId idp) {
		// les variables utilisées dans la recherche
		boolean tr = false;
		int i = 0;

		// Créer un buffer vide
		byte[] buffer = new byte[Constants.PAGESIZE];

		/*
		 * vérifier s'il ya une frame libre dans le bufferPool si oui returner le buffer
		 * en utilisant la méthode getPage
		 */
		while (i < bufferpool.size() && !tr) {

			// si la page n'est pas chargée
			if (!bufferpool.get(i).isEstCharge()) {

				// Mise à jour de la case dans LRU
				miseAJourLRU(bufferpool.get(i));

				// Mise à jour de la case
				bufferpool.get(i).setIdDeLaPage(idp);

				// Signaler que la case a été chargée
				bufferpool.get(i).setEstCharge(true);

				// Initialiser le pin_count
				bufferpool.get(i).setPin_count(0);

				// Mettre la variable boolenne à true,ce qui signifie que la page a été trouvée
				tr = true;

				// Retourner le buffer de la nouvelle page
				return getPage(idp);
			}

			// incrementer l'indice de parcours
			i++;
		}

		// Si aucune page n'a été trouvée
		if (!tr) {
			// les variables utilisées dans la recherche
			tr = false;
			i = 0;
			/*
			 * vérifier s'il ya une page dans le bufferPool qui peut être remplacée En
			 * utlisant la liste oldPagesLRU si oui returner le buffer en utilisant la
			 * méthode getPage
			 */
			while (i < bufferpool.size() && !tr && listeDesPagesPourLRU.size() > 0) {
				// Récupérer les frames
				PageId pageb = bufferpool.get(i).getIdDeLaPage();
				PageId pageRlu = listeDesPagesPourLRU.get(0).getIdDeLaPage();

				/*
				 * Comparaison entre la page dans oldPagesLRU et celle dans le bufferPool Si
				 * elles sont identiques returner le buffer et ecrire la page dans le fichier si
				 * dirty=1
				 */
				if (pageb.getFileIdx() == pageRlu.getFileIdx() && pageb.getPageIdx() == pageRlu.getPageIdx()) {
					// si la page a été modifiée
					if (bufferpool.get(i).getFlagDirty() == 1)
						// Ecrire la page dans le fichier
						DiskManager.getInstance().writePage(pageb, bufferpool.get(i).getBuffer());

					// Remplacement de la page
					bufferpool.get(i).setPin_count(0);
					bufferpool.get(i).setFlagDirty(0);

					// Récupérer le buffer de la page demandée depuis le disque
					byte[] b = new byte[Constants.PAGESIZE];
					DiskManager.getInstance().readPage(idp, b);

					// Modifier le buffer de la page à remplacer par celui de la page demandée
					bufferpool.get(i).setBuffer(b);

					// Mettre à jour la liste de LRU
					miseAJourLRU(bufferpool.get(i));

					// Récuperer le buffer de la nouvelle page
					buffer = getPage(pageb);

					// Remplacer les anciennes pages par les nouvelles
					bufferpool.get(i).getIdDeLaPage().setFileIdx(idp.getFileIdx());
					bufferpool.get(i).getIdDeLaPage().setPageIdx(idp.getPageIdx());

					// Mettre la variable boolenne à true,ce qui signifie que la page a été trouvée
					tr = true;
				}
				// Incrémenter le compteur
				i++;
			}
		}

		// récupérer le buffer
		return buffer;
	}

	/**
	 * Cette méthode permet de supprimer dans la liste listeDesPagesLRU
	 * 
	 * la case dont le pin_count n'est plus 0;
	 *
	 * @param frame <i>La case à supprimer</i>
	 */
	private void miseAJourLRU(Frame frame) {
		// Récupérer la page du frame
		PageId page = frame.getIdDeLaPage();

		// les variables utilisées dans la recherche
		boolean tr = false;
		int i = 0;
		/*
		 * trouver la page dont le pin_coint n'est plus 0 et la supprimer
		 */
		while (i < listeDesPagesPourLRU.size() && !tr) {
			// Récupérer la page
			PageId pageUrl = listeDesPagesPourLRU.get(i).getIdDeLaPage();

			// Verifier s'il  s'agit bien de la page à supprimer
			if (page.getFileIdx() == pageUrl.getFileIdx() && page.getPageIdx() == pageUrl.getPageIdx()) {
				// Supprimer la page
				listeDesPagesPourLRU.remove(i);

				// on met la variable boolenne à true,donc la page à supprimer a
				// été trouvée
				tr = true;
			}

			// Incrémenter le compteur
			i++;
		}

	}

}// fin classe
