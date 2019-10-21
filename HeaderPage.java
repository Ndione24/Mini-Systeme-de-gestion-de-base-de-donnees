package projetBdd;

import java.util.*;
//for all pages

public class HeaderPage {
	//le nombre de pages dans un fichier
	private int dataPageCount;
	// N entiers correspondant au nombre de slots (cases) restant 
	//disponibles sur chacune des N pages de données
	private List<DataPage> listeDataPages;

	public HeaderPage() {
		this.dataPageCount=0;
		this.listeDataPages = new ArrayList<DataPage>();
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
}
