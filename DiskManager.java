package DataBase;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DiskManager {
	//private RandomAccessFile isr;
	
	
	/*public DiskManager(String Data_) {
		try {
			isr = new RandomAccessFile(Data_,"rw");
		}catch(IOException e) {
			System.out.println("Erreur d'ouverture du fichier"+ e.getMessage());
		}
	}*/
	
	public void createFile(int fileIdx) {
		File f = new File("Data_"+fileIdx+".rf");
		if(!f.exists()) {
			try {
				f.createNewFile();
			}catch(IOException e){
				System.out.println("Le nom du fichier existe déjà\n"+ e.getMessage());
			}
		}
	}

	public PageId addPage(int fileIdx) {
		
		return null;
	}
	
	public void ReadPage(PageId id, ByteBuffer buff) {
		
	}
	
	public void WritePage(PageId id, ByteBuffer buff) {
		
	}
}
