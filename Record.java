package projetBdd;

import java.util.List;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Record {
//cette varibale reldef correspond à la relation à laquelle «appartient» le record
	private RelDef relDef;
	// cette variable values correspond aux valeurs du record.
	private List<String> values;
	// Cette variable contriendra les types des colonnes de la relation
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
		// on boucle à partir de position
		// jusqu'à la fin du tableau values
		for (int i = position; i < this.values.size(); i++) {
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

	public void readFromBuffer(ByteBuffer buff, int position) {
       
		//On converti le buffer en String
		 String s =Arrays.toString(buff.array());
		 //onn Affiche le budder à partir de position
		for(int i=position;i<buff.capacity();i++) {
			System.out.println(s.charAt(i));
		}

	}

}// fin de la classe
