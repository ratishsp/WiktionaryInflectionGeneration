/**
 * 
 * 
 */
package in.ac.iiit;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ratish
 *
 */
public class VerbInflectionMultiple {
	private Set<String> simplePresent;
	private Set<String> simplePresentSingular;
	private Set<String> simplePresentPlural;
	private Set<String> presentParticiple;
	private Set<String> simplePast;
	private Set<String> pastParticiple;
	
	public VerbInflectionMultiple() {
		simplePresent = new HashSet<String>();
		presentParticiple  = new HashSet<String>();
		simplePast  = new HashSet<String>();
		pastParticiple  = new HashSet<String>();
		simplePresentSingular = new HashSet<String>();
		simplePresentPlural = new HashSet<String>();
	}
	
	public void add(String simplePresent, String presentParticiple, String simplePast) {
		this.simplePresent.add(simplePresent);
		this.simplePresentSingular.add(simplePresent);
		this.simplePast.add(simplePast);
		this.presentParticiple.add(presentParticiple);
		this.pastParticiple.add(simplePast);
	}

	public void add(String simplePresent, String presentParticiple, String simplePast, String pastParticiple) {
		this.simplePresent.add(simplePresent);
		this.simplePresentSingular.add(simplePresent);
		this.simplePast.add(simplePast);
		this.presentParticiple.add(presentParticiple);
		this.pastParticiple.add(pastParticiple);
	}
	public VerbInflectionMultiple(Set<String> simplePresent,
			Set<String> presentParticiple, Set<String> simplePast,
			Set<String> pastParticiple) {
		super();
		this.simplePresent = simplePresent;
		this.presentParticiple = presentParticiple;
		this.simplePast = simplePast;
		this.pastParticiple = pastParticiple;
	}



	public Set<String> getSimplePresent() {
		return simplePresent;
	}



	public void setSimplePresent(Set<String> simplePresent) {
		this.simplePresent = simplePresent;
	}



	public Set<String> getPresentParticiple() {
		return presentParticiple;
	}



	public void setPresentParticiple(Set<String> presentParticiple) {
		this.presentParticiple = presentParticiple;
	}



	public Set<String> getSimplePast() {
		return simplePast;
	}



	public void setSimplePast(Set<String> simplePast) {
		this.simplePast = simplePast;
	}



	public Set<String> getPastParticiple() {
		return pastParticiple;
	}



	public void setPastParticiple(Set<String> pastParticiple) {
		this.pastParticiple = pastParticiple;
	}



	public Set<String> getSimplePresentSingular() {
		return simplePresentSingular;
	}

	public void setSimplePresentSingular(Set<String> simplePresentSingular) {
		this.simplePresentSingular = simplePresentSingular;
	}

	public Set<String> getSimplePresentPlural() {
		return simplePresentPlural;
	}

	public void setSimplePresentPlural(Set<String> simplePresentPlural) {
		this.simplePresentPlural = simplePresentPlural;
	}

	

	@Override
	public String toString() {
		return "VerbInflectionMultiple [simplePresent=" + simplePresent
				+ ", simplePresentSingular=" + simplePresentSingular
				+ ", simplePresentPlural=" + simplePresentPlural
				+ ", presentParticiple=" + presentParticiple + ", simplePast="
				+ simplePast + ", pastParticiple=" + pastParticiple + "]";
	}

	public int size() {
		return simplePresent.size()+simplePast.size()+presentParticiple.size()+pastParticiple.size();
	}


	
	
	
}
