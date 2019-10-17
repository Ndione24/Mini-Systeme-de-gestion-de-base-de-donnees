package projetBdd;
//classe Rid qui correspond au Rid (Record Id, donc identifiant) d’un Record.

public class Rid {
    // indique la page à laquelle appartient le Record
	private PageId pageId;
	// slotIdx, un entier qui est l’indice de la case où le Record est stocké. 
	private int slotIdx;
	
	/*
	 * Constructeur 1 avec les attributs
	 */
	public Rid(PageId pageId,int slotIdx) {
		this.pageId=pageId;
		this.slotIdx=slotIdx;
	}
	/*
	 * Constructeur 2 sans paramètres
	 */
	
	/**
	 * getters and setters
	 */
	public PageId getPageId() {
		return pageId;
	}
	public void setPageId(PageId pageId) {
		this.pageId = pageId;
	}
	public int getSlotIdx() {
		return slotIdx;
	}
	public void setSlotIdx(int slotIdx) {
		this.slotIdx = slotIdx;
	}
}
