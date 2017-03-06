/**
 * 
 * 
 */
package in.ac.iiit;

/**
 * @author ratish
 *
 */
public class VerbInflection {
	private String simplePresent;
	private String presentParticiple;
	private String simplePast;
	private String pastParticiple;
	
	public VerbInflection() {
		// TODO Auto-generated constructor stub
	}
	
	
	public VerbInflection(String simplePresent, String presentParticiple, String simplePast) {
		super();
		this.simplePresent = simplePresent;
		this.simplePast = simplePast;
		this.presentParticiple = presentParticiple;
		this.pastParticiple = simplePast;
	}

	public VerbInflection(String simplePresent, String presentParticiple, String simplePast, String pastParticiple) {
		super();
		this.simplePresent = simplePresent;
		this.presentParticiple = presentParticiple;
		this.simplePast = simplePast;		
		this.pastParticiple = pastParticiple;
	}

	public String getSimplePresent() {
		return simplePresent;
	}
	public void setSimplePresent(String simplePresent) {
		this.simplePresent = simplePresent;
	}
	public String getSimplePast() {
		return simplePast;
	}
	public void setSimplePast(String simplePast) {
		this.simplePast = simplePast;
	}
	public String getPastParticiple() {
		return pastParticiple;
	}
	public void setPastParticiple(String pastParticiple) {
		this.pastParticiple = pastParticiple;
	}
	

	public void setPresentParticiple(String presentParticiple) {
		this.presentParticiple = presentParticiple;
	}
	
	public String getPresentParticiple() {
		return presentParticiple;
	}


	@Override
	public String toString() {
		return "VerbInflection [simplePresent=" + simplePresent
				+ ", presentParticiple=" + presentParticiple + ", simplePast="
				+ simplePast + ", pastParticiple=" + pastParticiple + "]";
	}
	
	
	
}
