package DataBase;

import java.io.IOException;


public class TestTp2 {

	
	public static void main(String [] args) throws IOException 
	{
		
		// test DiskManager
		
		BufferManager2 bm= BufferManager2.getInstance();
		
		DiskManager dm =DiskManager.getInstance();
		
		System.out.println("Creation de fichier");
		//dm.createFile(2);
		
	/*	PageId firstpage=dm.addPage(2);
		
		System.out.println(firstpage.getFileIdx()+" "+firstpage.getPageIdx()); */
		
	
		
		
		PageId firstPage= new PageId(2,0);
	
		byte[] buff= new byte [10];
		
		/*
		byte [] buf= {1,3};
		
		System.out.println("Ecriture sur la page 1 du fichier 2 ");
		dm.writePage(firstPage, buf);   */
		
		
		
		
		System.out.println("sur la page 1 du fichier 1  ");
		dm.readPage(firstPage, buff);
		
		for(byte item: buff) 
		{
			System.out.println(item);
		}
		
	
		
		
		
		
		
		
		PageId deuxiemePage= new PageId(2,1);
		
		byte[] buff1= new byte [2];
		
		/*byte [] buf1= {2,7};
		
		System.out.println("Ecriture sur la page 2 du fichier 1 ");
		dm.writePage(deuxiemePage, buf1); */
		
		
		
		System.out.println("Lecture sur la page 2 du fichier 1 ");
		dm.readPage(deuxiemePage, buff1);
		
		for(byte item1: buff1) 
		{
			System.out.println(item1);
		}
		
		
		
		
		
		
		
		PageId troisiemePage= new PageId(2,2);
		
		byte[] buff2= new byte [2];
		
		/*byte [] buf2= {1,1};
		
		System.out.println("Ecriture sur la page 3 du fichier 1 ");
		dm.writePage(troisiemePage, buf2);  */
		
		System.out.println("Lecture sur la page 3 du fichier 1  ");
		dm.readPage(troisiemePage, buff2);
		
		for(byte item: buff2) 
		{
			System.out.println(item);
		}
		
		
		
		
	PageId quatriemePage= new PageId(2,3);
	
	PageId cinq =new PageId(2,4);
		
		byte[] buff4= new byte [2];
		
	/*	byte [] buf4= {1,9};
		
		System.out.println("Ecriture sur la page 4 du fichier 1 ");
		dm.writePage(quatriemePage, buf4);*/ 
		
		System.out.println("Lecture sur la page 4 du fichier 1 ");
		dm.readPage(quatriemePage, buff4);
		
		for(byte item: buff4) 
		{
			System.out.println(item);
		}
		
		
		//End test DiskManager
		
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Test Buffer Manager ");
		
		//Test BufferManager 
		
		byte [] resultGet;
		
		byte [] resultGet2;
		
		Frame a= new Frame(buff,deuxiemePage);
		
		
		//ajout d'une frame dans le buffer 
		
		bm.addFrame(a);
		
	
		
		//bm.removeFrame(1);
		
		
		
		
		//taille du buffer actuellement
				bm.sizeBuffer();
				
	/*******************Test Get ************************/
		
		//cette page n'existe pas dans le buffer et il y'a une case libre
		
		
		
		System.out.println("Un get sur une page qui n'est pas encore chargé dans le bufferpool ");
		bm.getPage(firstPage);
		
		
		
		bm.infoFrameBufferpool();
		System.out.println("");
		
		
		//firstPage existe deja; on doit incrementer son pincount 
		
		System.out.println("Un get sur une page qui est deja  chargé dans le bufferpool ");
		bm.getPage(firstPage);
		
		bm.infoFrameBufferpool();
		
		System.out.println("");
		
		/*******************End Test Get ************************/
		
		/*******************Test Free ************************/
		System.out.println("Apres un Free et un setBuffer ");
		bm.freePage(firstPage, 0);
		
		
		byte [] bufff= {9,9,7,5,2,7,1,8,18,17};
		
		bm.remplirBuffer(firstPage, bufff);
		bm.infoFrameBufferpool();
		
		
		System.out.println("Apres un Free avec dirty = 1");
		bm.freePage(firstPage, 1);
		bm.infoFrameBufferpool();
		
		
		bm.sizeBuffer();
		
		/*******************End Test Free ************************/
		
		
		
		/******************* Test LRU ************************/
		
		//une case à son pincount =0 et on souhaite la remplacer par une autre qui n'est pas encore chargé dans le bufferpool
		
		
		System.out.println("Test LRU");
		bm.getPage(cinq);
		
		bm.infoFrameBufferpool(); 
		
		
		
		/*******************End Test LRU ************************/
		
		
		bm.sizeBuffer();
		
		
		
		
		/*
		 * for(byte item: resultGet) 
		{
			System.out.println(item);
		}
		*/
		
		/*
		for(byte item: resultGet2) 
		{
			System.out.println(item);
		}
		*/
		
		
		//dm.displayNumberPage(2);
		
		
		//bm.getPage(quatriemePage);
		
		
		
		
	}
}
