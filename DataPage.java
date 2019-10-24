package projetBdd;
//for a page
public class DataPage {
//id de la page
	private int idxDeLaPage;
//nbre de cases dispo
	private int freeSlots;
	
	public DataPage() {
		this.idxDeLaPage=0;
		this.freeSlots=0;
	}
	/**
	 * constructeur 2
	 */
	public DataPage(int idxDeLaPage,int freeSlots) {
		this.idxDeLaPage=idxDeLaPage;
		this.freeSlots=freeSlots;
	}

	public int getIdxDeLaPage() {
		return this.idxDeLaPage;
	}

	public void setIdxDeLaPage(int idx) {
		this.idxDeLaPage = idx;
	}

	public int getFreeSlots() {
		return freeSlots;
	}

	public void setFreeSlots(int freeSlots) {
		this.freeSlots = freeSlots;
	}
}
