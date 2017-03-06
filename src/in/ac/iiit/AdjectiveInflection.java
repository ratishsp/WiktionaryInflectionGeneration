/**
 * 
 */
package in.ac.iiit;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ratish
 *
 */
public class AdjectiveInflection {
	private Set<String> comparative = new HashSet<String>();
	private Set<String> superlative = new HashSet<String>();
	public Set<String> getComparative() {
		return comparative;
	}
	public void setComparative(Set<String> comparative) {
		this.comparative = comparative;
	}
	public Set<String> getSuperlative() {
		return superlative;
	}
	public void setSuperlative(Set<String> superlative) {
		this.superlative = superlative;
	}
	@Override
	public String toString() {
		return "AdjectiveInflection [comparative=" + comparative
				+ ", superlative=" + superlative + "]";
	}
}
