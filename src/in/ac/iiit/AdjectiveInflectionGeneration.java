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
public class AdjectiveInflectionGeneration {

	private static final String ADJECTIVE_SER = "/home/arya/project/intern/InflectionGeneration/adjective.ser";
	static Map<String, List<String>> inflectionMap;
	static{
		try {
			FileInputStream fileIn = new FileInputStream(ADJECTIVE_SER);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			inflectionMap = (Map<String, List<String>>) in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isAdjective(String input){
		if(inflectionMap.containsKey(input)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(AdjectiveInflectionGeneration.inflect("tough"));
//		System.out.println(AdjectiveInflectionGeneration.isAdjective("preceded"));
	}
	
	public static Set<String> inflect(String input){
		Set<String> inflections = new HashSet<String>();
		if(!inflectionMap.containsKey(input)){
			inflections.add(input);
			return inflections;
		}
		
		List<String> inflectionEntries = inflectionMap.get(input);
		String inflectionEntry = inflectionEntries.get(0);
//		System.out.println("input "+input+" template "+inflectionEntry);
		
		if(inflectionEntry.equals("{{en-adj|er}}")||inflectionEntry.equals("{{en-adj|er|more}}")){	//point
			inflect(input, inflections);
//			return input+"s";
		}else{
			inflectionEntry = inflectionEntry.replaceAll("\\{", "").replaceAll("}", "");
			String [] units = inflectionEntry.split("\\|");
			if(units.length==2 && units[0].equals("en-adj") && !units[1].equals("-")){
				inflections.add(units[1]);
				if(units[1].lastIndexOf("er") != -1){
					inflections.add(units[1].substring(0,units[1].lastIndexOf("er"))+"est");
				}else{
//					System.out.println("adj input "+input);
				}
//				inflections.add(units[1].substring(0,))
			}else if(units.length==3 && units[1].equals("er")){
				inflect(input,inflections);
				inflections.add(units[2]);
				if(units[2].lastIndexOf("er") != -1){
					inflections.add(units[2].substring(0,units[2].lastIndexOf("er"))+"est");
				}else{
					System.out.println("adj input2 "+input);
				}
			}
		}
		if(inflections.size()==0){
			inflections.add(input);
		}
			
		return inflections;
		
	}

	private static void inflect(String input, Set<String> inflections) {
		if(input.endsWith("y")){	//happy
			inflections.add(input.substring(0,input.lastIndexOf("y"))+"ier");
			inflections.add(input.substring(0,input.lastIndexOf("y"))+"iest");
		}else if(input.endsWith("e")){
			inflections.add(input+"r");
			inflections.add(input+"st");
//				inflections.add(input.substring(0,input.lastIndexOf("y"))+"iest");
		}else{
			inflections.add(input+"er");
			inflections.add(input+"est");
		}
	}

}
