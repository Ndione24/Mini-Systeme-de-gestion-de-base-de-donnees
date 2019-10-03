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

	public byte[] GetPage(PageId pageId) {

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

}// fin classe
