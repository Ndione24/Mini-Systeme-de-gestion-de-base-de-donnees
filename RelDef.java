package projetBdd;

import java.util.List;
import java.util.ArrayList;

public class RelDef {

	// nomRelation qui represente le nom d'une relation donnees
	private String nomRelation;
	// nbColonnes rep le nombre de colonnes de la relation
	private int nbColonnes;
	// typesColonnes qui rep les types de differents colonnes
	private List<String> typesColonnes;
	// fileIdx, qui correspond à l’indice du fichier disque qui stocke la relation
	private int fileIdx;
	// recordSize qui représente la taille d’un record
	private int recordSize;
	// slotCount, qui représente le nombre de cases (slots) sur une page
	private int slotCount;

	/**
	 * @description :1er constructeur de la classe
	 * @param nomRelation
	 * @param nbColonnes
	 * @param typesColonnes
	 */

	public RelDef(String nomRelation, int nbColonnes, List<String> typesColonnes) {

		this.nomRelation = nomRelation;
		this.nbColonnes = nbColonnes;
		this.typesColonnes = typesColonnes;
	}

	public RelDef() {

		this.nomRelation = "";
		this.nbColonnes = 0;
		this.typesColonnes = new ArrayList<String>();
	}

	public RelDef(String nomRelation, int nbColonnes) {

		this.nomRelation = nomRelation;
		this.nbColonnes = nbColonnes;
		this.typesColonnes = new ArrayList<String>();
	}

	/**
	 * constructeur definit au niveau tp4
	 * 
	 * @param nomRelation
	 * @param nbColonnes
	 * @param typesColonnes
	 * @param fileIdx
	 * @param recordSize
	 * @param slotCount
	 */
	public RelDef(String nomRelation, int nbColonnes, List<String> typesColonnes, int fileIdx, int recordSize,
			int slotCount) {
		this.nomRelation = nomRelation;
		this.nbColonnes = nbColonnes;
		this.typesColonnes = typesColonnes;
		this.fileIdx = fileIdx;
		this.recordSize = recordSize;
		this.slotCount = slotCount;
	}

	public String getNomRelation() {
		return nomRelation;
	}

	public void setNomRelation(String nomRelation) {
		this.nomRelation = nomRelation;
	}

	public int getNbColonnes() {
		return nbColonnes;
	}

	public void setNbColonnes(int nbColonnes) {
		this.nbColonnes = nbColonnes;
	}

	public List<String> getTypesColonnes() {
		return typesColonnes;
	}

	public void setTypesColonnes(List<String> typesColonnes) {
		this.typesColonnes = typesColonnes;
	}

	public int getFileIdx() {
		return fileIdx;
	}

	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public int getSlotCount() {
		return slotCount;
	}

	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}

}
