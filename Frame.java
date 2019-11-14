package DataBase;

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
	
	public Frame(PageId page) {
		this.buffer = new byte[Constants.PAGESIZE];
		this.idDeLaPage = page;//AR
		this.pin_count = 1;
		this.flagDirty = 0;
		this.estCharge = true;
	}

	/**
	 * Ce constructeur met à jour les attributs buffer et pageId
	 * 
	 * @param buffer Représente le buffer
	 * @param pageId : id de la page
	 * ssr
	 */
	
	public Frame(byte[] buffer, PageId pageId) {
		this.buffer = buffer;
		pin_count = 0;
		flagDirty = 0;
		this.idDeLaPage = pageId;
		estCharge = true;
	}
	

	// gettes and setters
	public byte[] getBuffer() {
		return this.buffer;
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

	public void allInfoFrame() 
	{
		System.out.println("chargement "+this.estCharge);
		System.out.println("dirty "+this.flagDirty);
		System.out.println("pincount "+this.pin_count);
		System.out.println("PageId "+this.idDeLaPage.getPageIdx());
		System.out.println("PageId "+this.idDeLaPage.getFileIdx());
		System.out.println("Contenu du Buffer");
		
		for(byte a: this.buffer) 
		{
			System.out.print(a+" ");
			
		}
		
	}
	/**
	 * methode qui renitialise de nouveau une Frame
	 */
	public void renitialiser() 
	{
		buffer = new byte[Constants.PAGESIZE];
		idDeLaPage = null;
		pin_count = 0;
		flagDirty = 0;
		estCharge = false;
	}
	
	
}

