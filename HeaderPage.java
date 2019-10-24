package projetBdd;

import java.nio.ByteBuffer;
import java.util.*;
//for all pages

public class HeaderPage {
	RelDef relDef;
	// le nombre de pages dans un fichier
	private int dataPageCount;
	// liste des pages et leur nombre de slots libre
	private List<DataPage> listeDataPages;

	public HeaderPage() {
		this.dataPageCount = 0;
		this.listeDataPages = new ArrayList<DataPage>();
	}

	public RelDef getRelDef() {
		return this.relDef;
	}

	public void setRelDef(RelDef relDef) {
		this.relDef = relDef;
	}

	public int getDataPageCount() {
		return dataPageCount;
	}

	public void setDataPageCount(int dataPageCount) {
		this.dataPageCount = dataPageCount;
	}

	public List<DataPage> getListeDataPages() {
		return listeDataPages;
	}

	public void setListeDataPages(List<DataPage> listeDataPages) {
		this.listeDataPages = listeDataPages;
	}

	/**
	 * methode pour mettre le buffer dans le HeaderPage
	 */
	public void readFromBufferToHeaderPage(byte[] buffer) {
		// Utilisation de ByteBuffer pour lire les données dans le buffer
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

		// Récupérer le nombre de pages
		dataPageCount = byteBuffer.getInt();

		// Parcours de la liste DataPage(couple page et freeslots)
		for (int i = 0; i < dataPageCount; i++) {
			// Récupérer la DataPage
			DataPage data = new DataPage(byteBuffer.getInt(), byteBuffer.getInt());

			// Ajout de la DataPage dans la liste
			listeDataPages.add(data);
		}
	}

	/**
	 * ecrire le contenu du header Page dans le buffer
	 *
	 * @param buffer buffer dans lequel sera ecrit le content de headerpage Buffer
	 *               dans laquel sera écrit la HeaderPageInfo
	 */
	public void writeHeaderPageToBuffer(byte[] buffer) {
		// on utilise bytebuffer pour écrire les données dans le buffer
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

		// Récupérer le nombre de pages
		byteBuffer.putInt(dataPageCount);

		/*
		 * Boucle pour récupérer la liste DataPage(couple de pageIdx et freeSlots) dans
		 * la HeaderPageInfo
		 */
		for (int i = 0; i < dataPageCount; i++) {
			// Recupération d'une pageIdx et insertion dans le Bytebuffer
			byteBuffer.putInt(listeDataPages.get(i).getIdxDeLaPage());

			// Recupération d'un freeSlots et insertion dans le Bytebuffer
			byteBuffer.putInt(listeDataPages.get(i).getFreeSlots());
		}
		// Mettre le contenu du ByteBuffer dans le buffer
		buffer = byteBuffer.array();
	}

	/**
	 * Cette méthode  lit un record dans le buffer à une position donnée
	 */
	public Record readRecordFromBuffer(byte[] buffer, int slotIdx) {
		// Calcul de la position de lecture
		int position = relDef.getSlotCount() + (slotIdx * relDef.getRecordSize());

		// Utilisation de ByteBuffer pour lire(ou et ecrire) les données dans le buffer
		// à une position
		ByteBuffer bf = ByteBuffer.wrap(buffer, position, buffer.length - position);

		// Création d'une liste de valeurs du record
		List<String> recordValues = new ArrayList<String>();

		// Parcours de la liste ReIDef pour vérifier le type des colonnes
		for (int i = 0; i < relDef.getNbColonnes(); i++) {

			// si colonne de type entier
			if ((relDef.getTypesColonnes().get(i).equals("int"))) {
				// Lire et Ajouter l'entier dans la liste
				recordValues.add("" + bf.getInt());
			}

			// si colonne de type float
			else if (relDef.getTypesColonnes().get(i).equals("float")) {
				// Lire et Ajouter le float dans la liste
				recordValues.add("" + bf.getFloat());
			}

			// si colonne de type String
			else {
				// Récupérer la taille de la chaine
				String str = (relDef.getTypesColonnes().get(i)).replace("string", "").trim();

				// Utilisation de StringBuffer pour Récupérer la chaine
				StringBuffer sb = new StringBuffer();

				// Parcourir la chaine
				for (int j = 0; j < Integer.parseInt(str); j++) {
					// Lecture et Ajout de caractere par caractere dans le StringBuffer
					sb.append((char) (bf.getChar()));
				}

				// Mettre le contenu de StringBuffer dans la liste
				recordValues.add(sb.toString());
			}
		}

		// Création d'un record
		Record rec = new Record();

		// Mise à jour des valeurs de record
		rec.setValues(recordValues);

		// libérer le buffer
		bf.clear();

		// Retouner le record
		return rec;
	}

}
