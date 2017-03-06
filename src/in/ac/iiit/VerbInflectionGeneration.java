/**
 * 
 */
package in.ac.iiit;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author ratish
 *
 */
public class VerbInflectionGeneration {

	private static final String VERB_SER = "/home/arya/project/intern/InflectionGeneration/verb.ser";
	private static Map<String, List<String>> inflectionMap = null;
	static{
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(VERB_SER);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			inflectionMap = (Map<String, List<String>>) in.readObject();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	//TODO cater for multiple verb senses
	public static void main(String[] args) {
//		VerbInflectionGeneration.inflect("");
		VerbInflectionMultiple verbInflection = VerbInflectionGeneration.inflectMultiple("debilitate");
		System.out.println(verbInflection);
		System.out.println("debilitate "+VerbInflectionGeneration.isVerb("debilitate"));
		
	}
	
	public static boolean isVerb(String input){
//		boolean isVerb = false;
		if(input.equals("be")){
			return true;
		}else if (inflectionMap.containsKey(input)){
			return true;
		}
		return false;
	}
	public static VerbInflectionMultiple inflectMultiple(String input){
		VerbInflectionMultiple verbInflectionMultiple = new VerbInflectionMultiple();
		if(input.equals("be")){
			verbInflectionMultiple.getSimplePresent().addAll(Arrays.asList(new String[]{"am","is","are","'s"}));
			verbInflectionMultiple.getSimplePresentSingular().addAll(Arrays.asList(new String[]{"am","is","'s"}));
			verbInflectionMultiple.getSimplePresentPlural().addAll(Arrays.asList(new String[]{"are"}));
			verbInflectionMultiple.getSimplePast().addAll(Arrays.asList(new String[]{"was","were"}));
			verbInflectionMultiple.getPresentParticiple().addAll(Arrays.asList(new String[]{"being"}));
			verbInflectionMultiple.getPastParticiple().addAll(Arrays.asList(new String[]{"been"}));
			return verbInflectionMultiple;
		}else if(input.equals("prepay")){
			verbInflectionMultiple.getPastParticiple().addAll(Arrays.asList(new String[]{"prepaid"}));			
		}else if(input.equals("say")){
			verbInflectionMultiple.getSimplePresent().addAll(Arrays.asList(new String[]{"say","says"}));
			verbInflectionMultiple.getSimplePresentSingular().add("says");
			verbInflectionMultiple.getSimplePresentPlural().add("say");
			verbInflectionMultiple.getPresentParticiple().add("saying");
			verbInflectionMultiple.getSimplePast().add("said");
			verbInflectionMultiple.getPastParticiple().add("said");
			return verbInflectionMultiple;
		}
		
		verbInflectionMultiple.getSimplePresentPlural().add(input);
		if(!inflectionMap.containsKey(input)){
//			System.out.println("Verb Input is not present "+input);
			verbInflectionMultiple.add(input, input, input);
			return verbInflectionMultiple;
		}
		List<String> inflections = inflectionMap.get(input);
//		System.out.println("inflections "+inflections);
		
		
		verbInflectionMultiple.getSimplePresent().add(input);
		for(String inflectionEntry: inflections){
			if(inflectionEntry.equals("{{en-verb}}")){
//				return new VerbInflection(input+"s",input+"ing",input+"ed");
				verbInflectionMultiple.add(input+"s",input+"ing",input+"ed");
				continue;
			}
			
			inflectionEntry = inflectionEntry.replaceAll("\\{", "").replaceAll("}", "");
			String [] units = inflectionEntry.split("\\|");
			if(input.equals("carry")){
				verbInflectionMultiple.add("carries","carrying","carried");
				continue;
			}
			if(units.length==3 && units[2].equals("ies")){ //cry = cr|ies
				verbInflectionMultiple.add(units[1]+"ies",units[1]+"ying",units[1]+"ied");
				continue;
			}
			if(units.length==2 && units[1].equals("d") ){ //d for free
				verbInflectionMultiple.add(input+"s",input+"ing", input+"d");
				continue;
			}
			
			if(units.length==2 && units[1].equals("es") ){//buzz
				verbInflectionMultiple.add(input+"es",input+"ing", input+"ed");
				continue;
			}
			
			if(units.length==2 || units.length==3 && units[2].equals("ing")){	//trek= trekk //fertilize = fertiliz|ing
				verbInflectionMultiple.add(input+"s",units[1]+"ing",units[1]+"ed");
				continue;
			}
			if(getVerbForms(inflectionEntry).size()==2){
				List<String> verbForms = getVerbForms(inflectionEntry);
				verbInflectionMultiple.add(input+"s",verbForms.get(1)+"ing",verbForms.get(1)+"ed");
				continue;
			}
			
			if(units.length==3 && units[2].equals("es")){ //buzz
				verbInflectionMultiple.add(units[1]+"es",units[1]+"ing", units[1]+"ed");
				continue;
			}
			
			
			if(units.length==4 && units[3].equals("ing")){
				String simplePresent = input + "s";
				String simplePast = "";
				if(units[2].equals("y")){	//tie t|y|ing
					simplePast = input + "d";
				}else{
					simplePast = units[1]+units[2]+"ed";
				}
				String presentContinuous = units[1]+units[2]+"ing";
				verbInflectionMultiple.add(simplePresent,presentContinuous,simplePast);
				continue;
			}
			
			List<String> verbForms = new ArrayList<String>();
			if(units.length>=4){
				for (int counter = 0; counter < units.length; counter++) {
					int presptc2 =-1;
					int past2 =-1;
					int past_ptc2=-1;
					if(units[counter].indexOf('=')==-1){
						verbForms.add(units[counter]);
					} else if((presptc2 = units[counter].indexOf("pres_ptc2=")) !=-1){
							verbInflectionMultiple.getPresentParticiple().add(units[counter].substring(presptc2+"pres_ptc2=".length()));
					}else if((past2 = units[counter].indexOf("past2=")) !=-1){
						verbInflectionMultiple.getSimplePast().add(units[counter].substring(past2+"past2=".length()));
						verbInflectionMultiple.getPastParticiple().add(units[counter].substring(past2+"past2=".length()));
					}else if((past_ptc2=units[counter].indexOf("past_ptc2="))!=-1){
						verbInflectionMultiple.getPastParticiple().add(units[counter].substring(past_ptc2+"past_ptc2=".length()));
					}
				}
				if(verbForms.size()==4){
					verbInflectionMultiple.add(verbForms.get(1),verbForms.get(2),verbForms.get(3));
				}else if(verbForms.size()==5){
					verbInflectionMultiple.add(verbForms.get(1),verbForms.get(2),verbForms.get(3),verbForms.get(4));
				}
				continue;
			}
		}
		if(verbInflectionMultiple.size()==0){
			throw new IllegalArgumentException("No verb form found");
		}
		
//		System.out.println(verbInflectionMultiple);
		return verbInflectionMultiple;
	}
	public static VerbInflection inflect(String input){
		try {
			
			if(!inflectionMap.containsKey(input)){
				System.out.println("Verb Input is not present "+input);
//				return input;
				return new VerbInflection(input,input,input);
//				throw new IllegalArgumentException("Verb Input is not present "+input);
			}
			List<String> inflections = inflectionMap.get(input);
			String inflectionEntry = inflections.get(0);
//			System.out.println("input "+input+" template "+inflectionEntry);
			if(inflectionEntry.equals("{{en-verb}}")){
				return new VerbInflection(input+"s",input+"ing",input+"ed");
			}
			
			inflectionEntry = inflectionEntry.replaceAll("\\{", "").replaceAll("}", "");
			String [] units = inflectionEntry.split("\\|");
			if(units.length==3 && units[2].equals("ies")){ //cry = cr|ies
				return  new VerbInflection(units[1]+"ies",units[1]+"ying",units[1]+"ied");
			}
			
			if(units.length==2 && units[1].equals("d") ){ //d for free
				return new VerbInflection(input+"s",input+"ing", input+"d");
			}
			
			if(units.length==2 && units[1].equals("es") ){//buzz
				return new VerbInflection(input+"es",input+"ing", input+"ed");
			}
			
			if(units.length==2 || units.length==3 && units[2].equals("ing")){	//trek= trekk //fertilize = fertiliz|ing
				return new VerbInflection(input+"s",units[1]+"ing",units[1]+"ed");				
			}
			if(getVerbForms(inflectionEntry).size()==2){
				List<String> verbForms = getVerbForms(inflectionEntry);
				return new VerbInflection(input+"s",verbForms.get(1)+"ing",verbForms.get(1)+"ed");	
			}
			
			if(units.length==3 && units[2].equals("es")){ //buzz
				return new VerbInflection(units[1]+"es",units[1]+"ing", units[1]+"ed");	
			}
			
			
			if(units.length==4 && units[3].equals("ing")){
				String simplePresent = input + "s";
				String simplePast = "";
				if(units[2].equals("y")){	//tie t|y|ing
					simplePast = input + "d";
				}else{
					simplePast = units[1]+units[2]+"ed";
				}
				String presentContinuous = units[1]+units[2]+"ing";
				return new VerbInflection(simplePresent,presentContinuous,simplePast);
			}
			
			List<String> verbForms = new ArrayList<String>();
			if(units.length>=4){
				for (int counter = 0; counter < units.length; counter++) {
					if(units[counter].indexOf('=')==-1){
						verbForms.add(units[counter]);
					}
				}
				VerbInflection verbInflection = null;
				if(verbForms.size()==4){
					verbInflection = new VerbInflection(verbForms.get(1),verbForms.get(2),verbForms.get(3));
				}else if(verbForms.size()==5){
					verbInflection = new VerbInflection(verbForms.get(1),verbForms.get(2),verbForms.get(3),verbForms.get(4));
				}
				return verbInflection;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
        
	}
	
	private static List<String> getVerbForms(String inflectionEntry){
		List<String> verbForms = new ArrayList<String>();
		String [] units = inflectionEntry.split("\\|");
		for (int counter = 0; counter < units.length; counter++) {
			if(units[counter].indexOf('=')==-1){
				verbForms.add(units[counter]);
			}
		}
		return verbForms;
	}

	/*private static void inflect(String input){
		String regex = "";
		regex = "\\{\\{en-verb[0-9a-z=|_-]+\\}\\}";
		input = "===Verb=== {{en-verb|fuels|fueling|pres_ptc2=fuelling|fueled|past2=fuelled}} # To provide with fuel.";
//		input = "{{en-verb}}";
//		input = "{{en-verb|fuels|fueli_=ng}}";
//		input = "{{en-verb|fuels|fueling|pres_ptc2=fuelling|fueled|past2=fuelled}}";
//		regex = "a";
//		input = "ccabc";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(input);
		boolean b = m.find();
		System.out.println(b);
		System.out.println(m.group());
	}*/
	
}
