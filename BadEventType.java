/**
 * 
 */
package DecoratorSimulator;

/**
 * @author Administrator
 *
 */
public enum BadEventType {
	noPurchase(5), inDebt(15), deepInDebt(40), takingTooLong(5);

	private final int badnessLevel;

	/**
	 * @param badnessLevel
	 */
	private BadEventType(int badnessLevel) {
		this.badnessLevel = badnessLevel;
	}

	/**
	 * get the badness level for this bad event type
	 */
	public int getBadnessLevel() {
		return badnessLevel;
	}
	


}
