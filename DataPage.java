package DataBase;


import java.util.ArrayList;

public class DataPage {
	//slotCount le nombre de cases sur une page de données. 
		private int slotCount;
		
		PageId page;
		private int freeSlots;
		private byte [] cases;
		
		
		public DataPage(PageId page,RelDef reldef){
			//slotCount associé à une relation
			
			this.slotCount=reldef.getSlotCount();
			//le nombre de Slot libre correspond au nombre de Slot au debut 
			this.freeSlots=reldef.getSlotCount();
			this.page=page;
			
			
			
			//size des tableaux à determininer
			this.cases= new byte[ slotCount + (slotCount*reldef.getRecordSize())];
			
			//on condidere que la premiere case s'agit de la bytemap
			for(int i=0;i<slotCount;i++) 
			{
				this.cases[0]= 0;
			}
			
			
			}
				
		public byte[] getBufferDataPage() 
		{
			return this.cases;
		}
		
		public int getSlotCount() {
			return slotCount;
		}
		
		
		public int incrementefreeSlots() 
		{
			return this.freeSlots++;
		}

		
		public int decrementeFreeSlots() 
		{
			return this.freeSlots--;
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
