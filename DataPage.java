package projetBdd;
//for a page
public class DataPage {
//slotCount le nombre de cases sur une page de données. 
	private int slotCount;
//nbre de cases dispo
	private int freeSlots;
	
	public DataPage() {
		this.slotCount=0;
		this.freeSlots=0;
	}

	public int getSlotCount() {
		return slotCount;
	}

	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}

	public int getFreeSlots() {
		return freeSlots;
	}

	public void setFreeSlots(int freeSlots) {
		this.freeSlots = freeSlots;
	}
}
