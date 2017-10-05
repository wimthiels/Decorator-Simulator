package DecoratorSimulator;

/**
 * a class of guys (extending Person) 
 * the player of the game will always be of the guy-type
 * 
 * @author Wim Thiels
 */
public class Guy extends Person {

	/**
	 * Initialise this guy with the given budget and given monthly salary
	 * @param budget
	 * @param monthlySal
	 */
	public Guy(int budget, int monthlySal) {
		super(budget, monthlySal, 0);
	}

	/**
	 * get the total points for this guy.
	 * this is will be the sum of the roomLikePoints and the eventpoints
	 * @return the totalPoints
	 */
	public int getTotalPoints() {
		return getRoomLikePoints() + getEventPoints();
	}


	@Override
	public String toString() {
		return "you";
	}

}
