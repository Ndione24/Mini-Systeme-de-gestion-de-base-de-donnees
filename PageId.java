package projetBdd;

public class PageId {
	// identifiant du fichier
	private int fileIdx;
	// identifiant de la page
	private int pageIdx;

	/**
	 * <Ce constructeur met à jour les attributs
	 * 
	 * @param fileIdx <i>Représente l'indentifier du fichier</i>
	 * @param pageIdx <i>Représente l'indentifier de la page</i>
	 */
	public PageId(int fileIdx, int pageIdx) {
		this.fileIdx = fileIdx;
		this.pageIdx = pageIdx;
	}

	/**
	 * Constructeur sans argument initialise les attributs
	 */
	public PageId() {
		fileIdx = 0;
		pageIdx = 0;
	}

//getters et setters
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}

	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}

	public int getFileIdx() {
		return fileIdx;
	}

	public int getPageIdx() {
		return pageIdx;
	}

	/**
	 * <i>Rédefinition de la méthode toString de la classe Object</i>
	 * 
	 * @return <i>L'identifiant du fichier et de la pageId</i>
	 */
	public String toString() {
		return " " + fileIdx + " " + pageIdx;
	}
/**
 * 
 * @param pageId comparer à ce pageId
 * @return vraie s'ils ont la même reference
 */
	public boolean equals(PageId pageId) {
		return this == pageId;
	}
}
