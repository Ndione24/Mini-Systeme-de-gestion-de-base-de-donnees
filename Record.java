package projetBdd;

import java.util.List;
import java.util.StringTokenizer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Record {
//cette varibale reldef correspond à la relation à laquelle «appartient» le record
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
	 * 2ème constructeur qui initialise les attributs en fonction des variables
	 * passés en paramètre
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
	 * methode qui devra écrire les valeurs du Record dans le buffer, l’une après
	 * l’autre, à partir de position.
	 */

	public void writeToBuffer(ByteBuffer buff, int position) {
		// On recupere les types de colonnes de RelDef
		typesValues = relDef.getTypesColonnes();
		
		// jusqu'à la fin du tableau values
		
		//on met la position du curseur à la position " position " donnée		
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
				// variable intermediaire (interm)pour convertir le type
				// avant de le mettre dans le buffer
				String interm = values.get(i);
				buff.put(ByteBuffer.wrap(interm.getBytes()));
			}

		} // fin boucle for

	}

	/**
	 * cette methode devra lire les valeurs du Record depuis le buffer, l’une après
	 * l’autre, à partir de position.
	 */

	public void readFromBuffer(byte[] buff, int position) {

		// on calcul de la position de lecture
		int pos = relDef.getSlotCount() + (position * relDef.getRecordSize());
		// on lit les données dans le buffer à partir de cette position
		ByteBuffer bf = ByteBuffer.wrap(buff, pos, buff.length - pos);
		// Parcours de la liste ReIDef pour vérifier le type des colonnes
		for (int i = 0; i < relDef.getNbColonnes(); i++) {

			// si la colonne est de type entier
			if ((relDef.getTypesColonnes().get(i).equals("int"))) {
				// on ajoute l'entier dans la liste des records
				values.add(""+bf.getInt());
			}

			// si la colonne est de type float
			else if (relDef.getTypesColonnes().get(i).equals("float")) {
				// Lire et Ajouter le float dans la liste
				values.add("" + bf.getFloat());
			}
            
			// si la colonne est de type String
			else {
				// on récupère la taille de la chaine
				String str = (relDef.getTypesColonnes().get(i)).replace("string", "").trim();

				// Utilisation de StringBuilder pour Récupérer la chaine
				StringBuilder sb = new StringBuilder();

				// on parcour la chaine
				for (int j = 0; j < Integer.parseInt(str); j++) {
					//on lit et ajoute carac par carac dans le StringBuilder
					sb.append((char) (bf.getChar()));
				}

				// on met  le contenu de StringBuilder dans la liste
				values.add(sb.toString());
			}
		}
	}

}// fin de la classe
