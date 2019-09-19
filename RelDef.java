package projetBdd;

import java.util.List;
import java.util.ArrayList;

public class RelDef {

	private String nomRelation;
	private int nbColonnes;
	private List<String> typesColonnes;

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

	public RelDef(String nomRelation, int nbColonnes) {

		this.nomRelation = nomRelation;
		this.nbColonnes = nbColonnes;
		this.typesColonnes = new ArrayList<String>();
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

}
