package DecoratorSimulator;

/**
 * an abstract class of events.   Events are triggered during gameplay.  
 * They unleash their impact on the persons in the game when proceeding to the next month.
 * 
 * @author wim thiels
 */
abstract class Event implements ImpactGenerateable {
	//the impact of the event on the points of the guy player
	private int guyScoreImpact;
	//the impact of the event on the mood of the girlfriend
	private int gfMoodImpact;

	//the impact of the event on the budget of the guy player, and girlfriend
	private int guyBudgetImpact;
	private int gfBudgetImpact;

	//the comment of the girlfriend on the event
	private String gfComment;
	
	//the month when the event took place
	private final int monthOfOccurence;

	
	/**
	 * Initialise this event with the given month of occurrence and all other attributes set 
	 */
	Event(int month) {
		setGfMoodImpact(0);
		guyScoreImpact = 0;
		gfBudgetImpact = 0;
		guyBudgetImpact = 0;
		setGfComment(" ");
		monthOfOccurence=month;
	}

	/**
	 * set the impact on the mood of the girlfriend for this event to the given value
	 * @param 	moodImpact
	 * 			the moodimpact to be set
	 */
	private void setGfMoodImpact(int moodImpact) {
		gfMoodImpact = moodImpact;
		
	}

	/**
	 * get the impact on the girlfriend for this event
	 */
	public int getGfMoodImpact() {
		return gfMoodImpact;
	}
	
	/**
	 * increase the impact on the mood of the girlfriend for this event by the given amount
	 * @param gfMoodImpact the gfMoodImpact to set
	 */
	public void addGfMoodImpact(int amount) {
		this.gfMoodImpact += amount;
	}
	
	
	/**
	 * get the impact on the score of the guy player for this event
	 */
	public int getGuyScoreImpact() {
		return guyScoreImpact;
	}

	/**
	 * get the impact on the budget of the guy player for this event
	 */
	public int getGuyBudgetImpact() {
		return guyBudgetImpact;
	}
	

	/**
	 * increase the impact on the budget of the guy for this event with the given amount
	 * @param amount
	 * 			the amount to be added
	 */
	public void addGuyBudgetImpact(int amount) {
		this.guyBudgetImpact += amount;
	}


	
	/**
	 * get the month of occurence of this event
	 */
	public int getMonthOfOccurence() {
		return monthOfOccurence;
	}



	/**
	 * increase the impact on the score of the guy for this event with the given amount
	 * @param	amount
	 * 			the amount to be added
	 */
	public void addGuyScoreImpact(int amount) {
		this.guyScoreImpact += amount;
	}
	
	/**
	 * get the impact on the budget of the girlfriend for this event
	 */
	public int getGfBudgetImpact() {
		return gfBudgetImpact;
	}

	/**
	 * increase the impact on the budget of the girlfried for this event with the given amount
	 * @param amount
	 * 			the amount to be added
	 */
	public void addGfBudgetImpact(int amount) {
		this.gfBudgetImpact += amount;
	}

	/**
	 * get the comment of the girlfriend on this event
	 */
	public String getGfComment() {
		return gfComment;
	}

	/**
	 * set the comment of the girlfriend on this event to the given string
	 * @param gfComment
	 *            the gfComment to set
	 */
	public void setGfComment(String gfComment) {
		this.gfComment = gfComment;
	}



	@Override
	public String toString() {
		if (this instanceof BuyEvent) {System.out.print("BUY");}
		if (this instanceof BadEvent) {System.out.print("BAD");}
		if (this instanceof PutEvent) {System.out.print("PUT");}
		return  "Event [gfMoodImpact=" + gfMoodImpact + ", guyScoreImpact=" + guyScoreImpact + ", gfBudgetImpact="
				+ gfBudgetImpact + ", guyBudgetImpact=" + guyBudgetImpact + ", gfComment=" + gfComment
				+ ", monthOfOccurence=" + monthOfOccurence + "]";
		
	}

}
