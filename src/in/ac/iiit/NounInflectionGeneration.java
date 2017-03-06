/**
 * 
 */
package in.ac.iiit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author ratish
 *
 */
public class NounInflectionGeneration {
	private static final String NOUN_SER = "/home/arya/project/intern/InflectionGeneration/noun.ser";
	static Map<String, List<String>> inflectionMap;
	static{
		try {
			FileInputStream fileIn = new FileInputStream(NOUN_SER);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			inflectionMap = (Map<String, List<String>>) in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(NounInflectionGeneration.inflectMultiple("earning"));
	}

	public static boolean isNoun(String input){
//		boolean isVerb = false;
		if (inflectionMap.containsKey(input)){
			return true;
		}
		return false;
	}
	public static Set<String> inflectMultiple(String input){
		Set<String> inflectedForms = new HashSet<String>();
//		System.out.println(inflectionMap.containsKey(input));
		if(!inflectionMap.containsKey(input)){
			if(inflectionMap.containsKey(capitalize(input))){
				input = capitalize(input);
			}else if(input.equals("idea")|| input.equals("earning") || input.equals("area")){
				inflectedForms.add(input+"s");
				return inflectedForms;
			}else{
//				throw new IllegalArgumentException("Input is not present "+input);
//				System.out.println("Input is not present "+input);
				inflectedForms.add(input);
//				inflectedForms.add(input+"s");
				return inflectedForms;
//				return input;
			}
		}
		if(input.equals("people")){
			inflectedForms.add("people");
		}
		List<String> inflections = inflectionMap.get(input);
		for(String inflectionEntry: inflections){
//			System.out.println(inflectionEntry);
			if(inflectionEntry.equals("{{en-noun}}")){	//point
				inflectedForms.add(input+"s");
			}
			inflectionEntry = inflectionEntry.replaceAll("\\{", "").replaceAll("\\}", "");
			String [] units = inflectionEntry.split("\\|");
			
			if(units.length==2){
				if(units[1].equals("es")){	//fish
					inflectedForms.add(input + "es");
				}else if(units[1].equals("-")){	//awe
					inflectedForms.add(input);
				}else if(units[1].equals("~")){	//beer
					inflectedForms.add(input+"s");
				}
				else{
					inflectedForms.add(units[1]);
				}
			}
			if(units.length>=3){
				if(units[1].equals("~")|| units[1].equals("-")){
					if(units[2].equals("s")|| units[2].equals("es")){		//beer
						inflectedForms.add(input+units[2]);
					}else{
						for(int loopIndex = 2; loopIndex<units.length;loopIndex++){	//zeros, zeroes
							if(units[loopIndex].indexOf("=")==-1){
								inflectedForms.add(units[loopIndex]);	
							}	
						}
//						inflectedForms.add(units[2]);		//geometries
					}
				}
				else if(units[1].equals("es")||units[1].equals("s")){
					inflectedForms.add(input+units[1]);
					for(int loopIndex = 2; loopIndex<units.length;loopIndex++){
						if(units[loopIndex].indexOf("=")==-1){
							inflectedForms.add(units[loopIndex]);	
						}	
					}
				}
				else{
					inflectedForms.add(units[1]);
				}
			}
		}
		
		//lowercased to handle aremenian etc
		Set<String> output = new HashSet<String>();
		for(String inflectedForm:inflectedForms){
			output.add(inflectedForm.toLowerCase());
		}
		
		return output;
	}
	public static String inflect(String input){
//		FileInputStream fileIn;
		try {
//			fileIn = new FileInputStream("/home/arya/project/intern/InflectionGeneration/noun.ser");
			
			if(!inflectionMap.containsKey(input)){
				if(inflectionMap.containsKey(capitalize(input))){
					input = capitalize(input);
				}else{
	//				throw new IllegalArgumentException("Input is not present "+input);
//					System.out.println("Input is not present "+input);
					return input;
				}
			}
			List<String> inflections = inflectionMap.get(input);
			String inflectionEntry = inflections.get(0);
//			System.out.println("input "+input+" template "+inflectionEntry);
			
			if(inflectionEntry.equals("{{en-noun}}")){	//point
				return input+"s";
			}
			inflectionEntry = inflectionEntry.replaceAll("\\{", "").replaceAll("\\}", "");
			String [] units = inflectionEntry.split("\\|");
			
			if(units.length==2){
				if(units[1].equals("es")){	//fish
					return input + "es";
				}
				if(units[1].equals("-")){	//awe
					return input;
				}
				if(units[1].equals("~")){	//beer
					return input+"s";
				}
				return units[1];
			}
			if(units.length>=3){
				if(units[1].equals("~")|| units[1].equals("-")){
					if(units[2].equals("s")|| units[2].equals("es")){		//beer
						return input+units[2];
					}
					return units[2];		//geometries
				}
				if(units[1].equals("es")||units[1].equals("s")){
					return input+units[1];	
				}
				return units[1];
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private static String capitalize(final String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}
