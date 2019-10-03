package projetBdd;

public class Frame {
	// buffer associé à cette case
	private byte[] buffer;
	// ID de la page
	private PageId idDeLaPage;
	// pin_count de la case
	private int pin_count;
	// flag_dirty pour copier ou non une page avant de remplacer
	private int flagDirty;
	// cette variable permet de savoir siv la page est chargée ou pas
	private boolean estCharge;

	/**
	 * Contructeur
	 */
	public Frame() {
		buffer = new byte[Constants.PAGESIZE];
		idDeLaPage = null;
		pin_count = 0;
		flagDirty = 0;
		estCharge = false;
	}

	/**
	 * Ce constructeur met à jour les attributs buffer et pageId
	 * 
	 * @param buffer Représente le buffer
	 * @param pageId : id de la page
	 * 
	 */
	public Frame(byte[] buffer, PageId pageId) {
		this.buffer = buffer;
		pin_count = 0;
		flagDirty = 0;
		this.idDeLaPage = pageId;
		estCharge = false;
	}

	// gettes and setters
	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public PageId getIdDeLaPage() {
		return idDeLaPage;
	}

	public void setIdDeLaPage(PageId idDeLaPage) {
		this.idDeLaPage = idDeLaPage;
	}

	public int getPin_count() {
		return pin_count;
	}

	public void setPin_count(int pin_count) {
		this.pin_count = pin_count;
	}

	public int getFlagDirty() {
		return flagDirty;
	}

	public void setFlagDirty(int flagDirty) {
		this.flagDirty = flagDirty;
	}

	public boolean isEstCharge() {
		return estCharge;
	}

	public void setEstCharge(boolean estCharge) {
		this.estCharge = estCharge;
	}

	// Methodes pour la mise à jour des variables pin_count et flagDirty
	/**
	 * cette methode increment le pin_count
	 */
	public void incrementerPinCount() {
		this.pin_count++;
	}

	/**
	 * cette methode decremente le pin_count
	 */
	public void decrementerPinCount() {
		this.pin_count--;
	}

	/**
	 * cette méthode incremente le flag dirty
	 */
	public void incrementerFlagDirty() {
		this.flagDirty++;
	}

	public void decrementerFlagDirty() {
		this.flagDirty--;
	}
}
