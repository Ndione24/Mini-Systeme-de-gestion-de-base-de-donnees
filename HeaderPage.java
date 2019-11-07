package DataBase;

import java.util.ArrayList;
import java.util.Arrays;

public class HeaderPage {
	
	private PageId page;
	private ArrayList<Byte> hp;
	private ArrayList<DataPage> datapage;
	
	public HeaderPage(PageId page){
		this.page=page;
		this.hp=new ArrayList<>();
		this.datapage=new ArrayList<>();
	}
	
	
	
	//remplace le contenu de la headerpage
	public void setHpBuffer(byte [] buffer) 
	{
		ArrayList newHp = new ArrayList(Arrays.asList(buffer));
		this.hp=newHp;
		
	}
	
	
	public byte[] getBuffer() 
	{
		Byte [] buff= new Byte[this.hp.size()];
		buff=this.hp.toArray(buff);
		
		String buf=buff.toString();
		return null;
	}
	
	

	public void addDataPageToHeaderPager(DataPage data) 
	{
		this.datapage.add(data);
	}
	
	public void incrementeDataPageCount() 
	{
		byte currentValue=this.hp.get(0);
		byte nextValue=(byte) (currentValue+1);
		this.hp.set(0, nextValue) ;
	}
	
	
	public void decrementeDataPageCount() 
	{
		byte currentValue=this.hp.get(0);
		byte nextValue=(byte) (currentValue-1);
		this.hp.set(0, nextValue) ;
	}
	
	
	public void incrementeFreeSlotDataPage(int position)
	{
		byte currentValue=this.hp.get(position);
		byte nextValue=(byte) (currentValue+1);
		this.hp.set(position, nextValue);
	}
	
	
	public void decrementeFreeSlotDataPage(int position)
	{
		byte currentValue=this.hp.get(position);
		byte nextValue=(byte) (currentValue-1);
		this.hp.set(position, nextValue);
	}
	
	public void setFreeSlotDataPage(int position,byte value)
	{
		this.hp.set(position, value);
	}
	
	//Quand une nouvelle page de donnée  est ajouté, on incremente le nombre de page
	//dans le fichier et on ajoute son slot dans le dans la headerPage 
	public void updateHeaderPageWhenAddDataPage(int freeSlotCount)
	{	
		
		incrementeDataPageCount();
		byte toByte=(byte)freeSlotCount;
		this.hp.add(toByte);
	}
	
	public void updateHeaderPageWhenWriteRecordToDataPage(int position)
	{	
		this.datapage.get(position).decrementeFreeSlots();
		
	}
	
	
	
	//retourne le nombre de page dans le fichier
	public byte getDataPageCount() 
	{
		return this.hp.get(0);
	}
	
	
}
