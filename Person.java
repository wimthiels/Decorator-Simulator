/**
 * 
 */
package DecoratorSimulator;

import java.util.ArrayList;

/**
 * A abstract class of persons involving the points (roomlikepoints, eventpoins), score history, budget, salary
 * 
 * @author Wim Thiels
 *
 */
abstract class Person {

	
	//the sum of all the likes of all the items in the current room.  is recalculated every month from scratch (so non cumulative)
	private int roomLikePoints;
	
	//the points scored through the impact of events.  a cumulative score, only updated when proceeding to the next month
	private int eventPoints;
	
	// the money that can be spent to buy items.  only updated when proceeding to the next month (i.e. you buy things on credit)
	private int budget;
	
	// the salary of the person, a certain fraction of this will be added to the budget every month
	private int monthlySalary;
	
	//a record of the score and budget for a certain month (used with the 'Score Log' option) 
	//layout => { month, roomLikePoints, eventPoints, budget, totalPoints }
	//remark : totalPoints is a derived value.  for the girlfriend this will be called the 'mood'
	private ArrayList<int[]> histScore;



	/**
	 * Initialise this new person with the given budget, given monthly salary and given
	 * startscore.  
	 * @param budget
	 * @param monthlySal
	 * @param startScore
	 */
	Person(int budget, int monthlySal, int startScore) {
		setBudget(budget);
		setMonthlySalary(monthlySal);
		setRoomLikePoints(0);
		setEventPoints(0);
		this.histScore = new ArrayList<int[]>();
		setHistScoreMonth(0, 0, 0, budget, startScore, 0);
	}



	/**
	 * get the budget for this person
	 */
	public int getBudget() {
		return budget;
	}

	/**
	 * set the budget for this person to the given value
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	/**
	 * change the budget for this person based on his/her monthly salary
	 * return the added amount
	 */
	public int addMonthlySalaryToBudget() {
		int extraBudget = getMonthlySalary() / 10;
		budget += extraBudget;
		return extraBudget;
	}

	
	/**
	 * decrease the budget for this person with the given amount
	 * @param 	amount
	 * 			the amount that will be substracted from the budget (can be negative)
	 */
	public void decreaseBudget(int amount) {
		budget -= amount;
	}

	/**
	 * get the roomLikePoints for this person
	 */
	public int getRoomLikePoints() {
		return roomLikePoints;
	}

	/**
	 * set the roomLikePoints for this person to the given value
	 */
	public void setRoomLikePoints(int roomLikePoints) {
		this.roomLikePoints = roomLikePoints;
	}

	/**
	 * change the roomLikePoints for this person with the  given amount
	 * @param 	amount
	 *   		the amount that will be added to the roomLikePoints of this person (can be negative)
	 */
	public void addRoomLikePoints(int amount) {
		this.roomLikePoints += amount;
	}


	/**
	 * get the eventpoints for this person
	 */
	public int getEventPoints() {
		return eventPoints;
	}

	
	/**
	 * set the eventpoints for this person to the given value
	 */
	public void setEventPoints(int points) {
		this.eventPoints = points;
	}

	/**
	 * increase the eventpoints for this person with the given amount
	 * @param 	amount
	 *  		the amount that will be added to the eventpoints (can be negative)
	 */
	public void addEventPoints(int amount) {
		this.eventPoints += amount;
	}

	/**
	 * get the score history of this person for the given month
	 * layout score history => { month, roomLikePoints, eventPoints, budget, totalPoints }
	 * @param	month
	 * 			the month for which the score history will be retrieved
	 */
	
	/**
	 * get the monthly salary for this person
	 */
	public int getMonthlySalary() {
		return monthlySalary;
	}


	/**
	 * set the monthly salary of this person to the given value
	 * @param monthlySal
	 */
	private void setMonthlySalary(int monthlySal) {
		this.monthlySalary = monthlySal;	
	}
	
	
	public int[] getHistScore(int month) {
		return histScore.get(month);
	}

	/**
	 * append the score history for this person for the given month, given roomlikepoints, given
	 * eventpoints, given budget and given totalpoints
	 * @param month
	 * @param roomLikePoints
	 * @param eventPoints
	 * @param budget
	 * @param totalPoints
	 */
	public void setHistScoreMonth(int month, int roomLikePoints, int eventPoints, int budget, int totalPoints, int matchPoints) {
		int[] scoresMonth = { month, roomLikePoints, eventPoints, budget, totalPoints, matchPoints };
		histScore.add(scoresMonth);
	}

}
