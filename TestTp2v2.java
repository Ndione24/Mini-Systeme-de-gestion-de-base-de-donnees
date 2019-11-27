/*************************************************************************
 *                                                                       *
 * 					Test BufferManager et DisKmanager					 *

 * 
 *************************************************************************/


package DataBase;

import java.io.IOException;
import java.util.Arrays;

public class TestBufferDiskManager {
	
	public static void main(String [] args) 
	{
		
		
		BufferManager2 bm= BufferManager2.getInstance();
		DiskManager dm =DiskManager.getInstance();
		
		//System.out.println("Creation de fichier");
		
		//dm.createFile(7);
		
		try {
			
			/*PageId firstPage= dm.addPage(7);
			PageId twPage= dm.addPage(7);
			PageId thPage= dm.addPage(7); */
			
			
			PageId firstPage= new PageId(7,0);
			
			System.out.println("Nombre De page ajouté dans le fichier d'indice 7");
			dm.displayNumberPage(7);
			
			System.out.println("Ecriture sur la page 1 du fichier d'indice 7 ");
			//byte bufWrite[]=new byte[10];
			byte buffRead[]=new byte[10];
			/*for(byte i=0;i<10;i++) 
			{
				bufWrite[i]=(byte) (i+1);
			}*/
			//dm.writePage(firstPage, bufWrite);
			byte [] buffWritep2=new byte[10];
			for(byte i=0;i<10;i++) 
			{
				buffWritep2[i]=(byte) (2*(i+1));
			}
			
			dm.readPage(firstPage, buffRead);
			
			System.out.println("Ce qu'on a lu");
			System.out.println(Arrays.toString(buffRead));
			byte [] retour;
			retour=bm.getPage(firstPage);
			
			//test pageid avec equal
			/*PageId p1=new PageId(7, 2);
			PageId p2=new PageId(7, 2);
			
			if(p1.equals(p2)) 
			{
				System.out.println("oui");
			}
			else 
			{
				System.out.println("non");
			} */
			
			//tester les equals de pageId avant d'avancer
			//le get fonctionne
			//essayons de faire un free en modifiant la valeur donne par get	
			//getPage doit remplir le buffer de la frame avec le buffer associé à la page
			System.out.println("Apres getPage");
			System.out.println(Arrays.toString(retour));
			
			bm.infoFrameBufferpool();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
