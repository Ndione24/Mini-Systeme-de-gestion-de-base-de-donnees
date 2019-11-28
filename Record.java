package projetBdd;

import java.util.List;
import java.util.StringTokenizer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Record {
//cette varibale reldef correspond Ã  la relation Ã  laquelle Â«appartientÂ» le record
	private RelDef relDef;
	// cette variable values correspond aux valeurs du record.
	private List<String> values;
	// Cette variable contriendra les types des colonnes de la relation relDef
	List<String> typesValues = new ArrayList<String>();

	/**
	 * 1er constructeur sans parametres qui initialise les attributs de la classe
	 */

	public Record() {
		relDef = new RelDef();
		values = new ArrayList<>();

	}

	/**
	 * 2Ã¨me constructeur qui initialise les attributs en fonction des variables
	 * passÃ©s en paramÃ¨tre
	 */

	public Record(RelDef relDef, List<String> values) {
		this.relDef = relDef;
		this.values = values;

	}

	/**
	 * constructeur qui prend en parametre une reldef
	 */

	public Record(RelDef relDef) {
		this.relDef = relDef;
		this.values = new ArrayList<String>();
		for (String s : relDef.getTypesColonnes()) {
			typesValues.add(s);
		}
	}

	/**
	 * Les gettters et setters necessaires
	 */
	public RelDef getRelDef() {
		return this.relDef;
	}

	public void setRelDef(RelDef relDef) {
		this.relDef = relDef;
	}

	public List<String> getValues() {
		return this.values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	/**
	 * methode qui devra Ã©crire les valeurs du Record dans le buffer, lâ€™une
	 * aprÃ¨s lâ€™autre, Ã  partir de position.
	 */

	public void writeToBuffer(ByteBuffer buff, int position) {

		// on calcul de la position de lecture
		// On recupere les types de colonnes de RelDef
		typesValues = relDef.getTypesColonnes();

		// jusqu'Ã  la fin du tableau values

		// on met la position du curseur Ã  la position " position " donnÃ©e
		buff.position(position);

		for (int i = 0; i < this.values.size(); i++) {
			// je verifie le type des valeurs puis
			// je le stocke dans le buffer sous forme de string

			// On verifie si c'est un int
			if (typesValues.get(i).equals("int")) {
				// variable intermediaire pour convertir le type
				// avant de le mettre dans le buffer
				int interm = Integer.parseInt(values.get(i));
				buff.putInt(interm);
			}

			// On verifie si c'est un float
			if (typesValues.get(i).equals("float")) {
				// variable intermediaire pour convertir le type
				// avant de le mettre dans le buffer
				float interm = Float.parseFloat(values.get(i));
				buff.putFloat(interm);
			}

			// On verifie si c'est un double
			if (typesValues.get(i).equals("double")) {
				// variable intermediaire pour convertir le type
				// avant de le mettre dans le buffer
				double interm = Double.parseDouble(values.get(i));
				buff.putDouble(interm);
			}
			// On verifie si c'est un string
			if (typesValues.get(i).equals("string")) {
				// on rÃ©cupÃ¨re la taille de la chaine
				String str = (relDef.getTypesColonnes().get(i)).replace("string", "").trim();
				int tailleDeLaChaine = Integer.parseInt(str);
				for(int j=0;j<tailleDeLaChaine;j++) {
					String interm = values.get(j);
					for(int k=0;k<interm.length();k++)
					buff.putChar(interm.charAt(k));
				}

				
			}

		} // fin boucle for

	}

	/**
	 * cette methode devra lire les valeurs du Record depuis le buffer, lâ€™une
	 * aprÃ¨s lâ€™autre, Ã  partir de position.
	 */

	public void readFromBuffer(byte[] buff, int position) {

		// on calcul de la position de lecture
		int pos = relDef.getSlotCount() + (position * relDef.getRecordSize());
		// on lit les donnÃ©es dans le buffer Ã  partir de cette position
		ByteBuffer bf = ByteBuffer.wrap(buff, pos, buff.length - pos);
		// Parcours de la liste ReIDef pour vÃ©rifier le type des colonnes
		for (int i = 0; i < relDef.getNbColonnes(); i++) {

			// si la colonne est de type entier
			if ((relDef.getTypesColonnes().get(i).equals("int"))) {
				// on ajoute l'entier dans la liste des records
				values.add("" + bf.getInt());
			}

			// si la colonne est de type float
			else if (relDef.getTypesColonnes().get(i).equals("float")) {
				// Lire et Ajouter le float dans la liste
				values.add("" + bf.getFloat());
			}

			// si la colonne est de type String
			else {
				// on rÃ©cupÃ¨re la taille de la chaine
				String str = (relDef.getTypesColonnes().get(i)).replace("string", "").trim();

				// Utilisation de StringBuilder pour RÃ©cupÃ©rer la chaine
				StringBuilder sb = new StringBuilder();

				// on parcour la chaine
				for (int j = 0; j < Integer.parseInt(str); j++) {
					// on lit et ajoute carac par carac dans le StringBuilder
					sb.append((char) (bf.getChar()));
				}

				// on met le contenu de StringBuilder dans la liste
				values.add(sb.toString());
			}
		}
	}

	public void addValue(String value) {
		values.add(value);
	}
	
	

}// fin de la classe
