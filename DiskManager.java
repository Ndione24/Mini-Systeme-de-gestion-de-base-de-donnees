package projetBdd;

import java.io.*;

public class DiskManager {
	private static DiskManager INSTANCE;

	/**
	 * Constructeur sans parametres de cette classe
	 */
	private DiskManager() {
	
	}

	/**
	 * 
	 * @return : une instance unqiue de cette classe
	 */
	public static synchronized DiskManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DiskManager();
			return INSTANCE;
		} else
			return INSTANCE;
	}

	/**
	 * Cette méthode crée (dans le sous-dossier DB) un fichier Data_fileIdx.rf
	 * initialement vide
	 * 
	 * @param fileIdx : indice du fichier
	 */
	public void createFile(int fileIdx) {
		try {
			// Création du fichier
			RandomAccessFile file = new RandomAccessFile("DB" + "Data_" + fileIdx + ".rf", "rw");
			// fermeture du fichier
			file.close();
		} catch (FileNotFoundException e) {

			// Affichage du message d'erreur
			System.out.println("Fichier non trouvé " + e.getMessage());
			e.getStackTrace();
		} catch (IOException e) {

			// Affichage du message d'erreur
			System.out.println(e.getMessage());
			e.getStackTrace();
		}

	}

	public PageId addPage(int fileIdx, PageId pageId) {
		// variable dans laquelle sera stockée l'identifiant de la page
		int idDeLaPage = 0;

		try {
			// ouverture du fichier en mode lecture et écriture
			RandomAccessFile file = new RandomAccessFile("../../DB/" + "Data_" + fileIdx + ".rf", "rw");

			// Positionner le curseur à la fin du fichier
			file.seek(file.length());

			// Calcul de l'indentifiant de la page
			idDeLaPage = (int) (file.length()) / Constants.PAGESIZE - 1;
			// Ecrire la page dans le fichier
			file.write(new byte[Constants.PAGESIZE]);

			// fermeture du fichier
			file.close();

		} catch (FileNotFoundException e) {

			// affichage du message d'erreur
			System.out.println(e.getMessage());
			e.getStackTrace();
		} catch (IOException e) {
			// affichage du message d'erreur
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		// Remplir la variable pageId avce les valeurs correspondantes
		pageId.setFileIdx(fileIdx);
		pageId.setPageIdx(idDeLaPage);
		// on retourne padeId
		return pageId;

	}

	/**
	 * 
	 * @param pid    : id de la pade
	 * @param buffer : tableau qui contiendra le le contenu du fichier à la page
	 *               idDeLaPage
	 */
	public void readPage(PageId idDeLaPage, byte[] buffer) {

		try {
			// ouverture du fichier en mode lecture
			RandomAccessFile file = new RandomAccessFile("../../DB/" + "Data_" + idDeLaPage.getFileIdx() + ".rf", "rw");

			// Positionner le curseur sur la page à lire
			file.seek(idDeLaPage.getPageIdx() * (Constants.PAGESIZE));

			// Lire la page dans le buffer
			file.read(buffer);

			// Fermeture du fichier
			file.close();
		} catch (FileNotFoundException e) {

			// affichage du message d'erreur
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// affichage du message d'erreur
			System.out.println(e.getMessage());
		}
	}

	public void writePage(PageId idDeLaPage, byte[] buffer) {

		try {
			// ouverture du fichier en mode lecture
			RandomAccessFile file = new RandomAccessFile("../../DB/" + "Data_" + idDeLaPage.getFileIdx() + ".rf", "r");

			// Positionner le curseur sur la page à lire
			file.seek(idDeLaPage.getPageIdx() * (Constants.PAGESIZE));

			// Lire la page dans le buffer
			file.write(buffer);

			// Fermeture du fichier
			file.close();
		} catch (FileNotFoundException e) {

			// affichage du message d'erreur
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// affichage du message d'erreur
			System.out.println(e.getMessage());
		}
	}

}
