package DecoratorSimulator;

import java.util.ArrayList;

/**
 * an abstract class of purchases involving a shopitem, month of purchase, eventlog, a buyer
 * and points gained or incurred cost
 * 
 * @author Wim Thiels
 *
 */
abstract class Purchase {

	// the shopItem (enum) that corresponds to this purchase
	private final ShopItem shopItem;
	
	private final int monthOfPurchase;
	
	// the person who bought this purchase
	private final Person buyer;
	
	
	// a logging of all the events that involve this purchase
	private final ArrayList<Event> eventLog;


	// to track for each purchase how many points it has earned throughout the
	// game. used for statistics
	private int pointsGainedForGuy; 
	private int pointsGainedForGf;
	private int costIncurredForGuy; //the money spent on this item



	/**
	 * Initialise this purchase with the given shopitem, given month of purchase
	 * and given buyer
	 * the points gained are set to the corresponding guy or girlfriend likes
	 * @param shopItem
	 * @param monthOfPurchase
	 * @param buyer
	 */
	public Purchase(ShopItem shopItem, int monthOfPurchase, Person buyer) {
		
		this.shopItem = shopItem;
		this.monthOfPurchase = monthOfPurchase;
		this.buyer=buyer;
		this.eventLog = new ArrayList<Event>();
		
		setPointsGainedForGuy(getGuyLikes());
		setPointsGainedForGf(getGfLikes());

		setIncurredCost(shopItem.getPrice());
	}
	/**
	 * get the shopitem for this purchase
	 */
	public ShopItem getShopItem() {
		return shopItem;
	}
	
	
	/**
	 * get the month of purchase for this purchase
	 */
	public int getMonthOfPurchase() {
		return monthOfPurchase;
	}
	
	
	/**
	 * get the buyer of this purchase
	 */
	public Person getBuyer() {
		return buyer;
	}

	
	/**
	 * get the event log for this purchase
	 */
	public ArrayList<Event> getEventLog() {
		return eventLog;
	}

	/**
	 * Add an event to the event log for this purchase
	 * the event is appended to the end of the list
	 */
	public void addEventToLog(Event event) {
		eventLog.add(event);
	}

	

	/**
	 * get the points gained for the guy player for this purchase
	 */
	public int getPointsGainedforGuy() {
		return pointsGainedForGuy;
	}

	/**
	 * add points gained for the guy player for this purchase
	 * @param pointsGainedForGuy
	 *        the points to be added 
	 */
	public void addPointsGainedforGuy(int amount) {

		this.pointsGainedForGuy += amount;
	}
	
	/**
	 * get the points gained for the girlfriend for this purchase
	 */
	public int getPointsGainedforGf() {
		return pointsGainedForGf;
	}

	/**
	 * add points gained for the girlfriend for this purchase
	 * @param pointsGainedForGf
	 *            the pointsGainedforGf to set
	 */
	public void addPointsGainedforGf(int amount) {

		this.pointsGainedForGf += amount;
	}

	/**
	 * get the total incurred cost for this purchase
	 */
	public int getIncurredCost() {
		return costIncurredForGuy;
	}

	/**
	 * set the total incurred cost for this purchase
	 * @param incurredCost
	 *        the incurredCost to set
	 */
	private void setIncurredCost(int incurredCost) {
		this.costIncurredForGuy = incurredCost;
	}

	/**
	 * add the given amount to the incurred cost for this purchase
	 * @param amount
	 *			the amount to be added
	 */
	public void addIncurredCost(int amount) {
		setIncurredCost(getIncurredCost() + amount);
	}
	


	/**
	 * get the points gained for the guy
	 */
	public int getPointsGainedForGuy() {
		return pointsGainedForGuy;
	}

	/**
	 * set the points gained for the guy
	 * @param 	pointsGainedForGuy 
	 * 			the pointsGainedForGuy to set
	 */
	public void setPointsGainedForGuy(int pointsGainedForGuy) {
		this.pointsGainedForGuy = pointsGainedForGuy;
	}

	/**
	 * get the points gained for the girlfriend
	 */
	public int getPointsGainedForGf() {
		return pointsGainedForGf;
	}

	/**
	 * Set the points gained for the girlfriend
	 * @param 	pointsGainedForGf 
	 * 			the pointsGainedForGf to set
	 */
	public void setPointsGainedForGf(int pointsGainedForGf) {
		this.pointsGainedForGf = pointsGainedForGf;
	}

	/**
	 * return the cost incurred for the guy 
	 */
	public int getCostIncurredForGuy() {
		return costIncurredForGuy;
	}

	/**
	 * set the cost incurred for the guy
	 * @param 	costIncurredForGuy 7
	 * 			the costIncurredForGuy to set
	 */
	public void setCostIncurredForGuy(int costIncurredForGuy) {
		this.costIncurredForGuy = costIncurredForGuy;
	}
	
	

	/**
	 *get the itemcode for this purchase (retrieved via the ShopItem)
	 */
	public String getItemCode() {
		return (getShopItem().getItemCode());
	}

	/**
	 *get the guy likes for this purchase (retrieved via the ShopItem)
	 */
	public int getGuyLikes() {
		return (getShopItem().getGuyLikes());
	}

	/**
	 *get the girlfriend likes for this purchase (retrieved via the ShopItem)
	 */
	public int getGfLikes() {
		return (getShopItem().getGfLikes());
	}

	/**
	 *get the price for this purchase (retrieved via the ShopItem)
	 */
	public int getPrice() {
		return (getShopItem().getPrice());
	}

	/**
	 *get the weight for this purchase (retrieved via the ShopItem)
	 */
	public double getWeight() {
		return (getShopItem().getWeight());
	}

	/**
	 *get the length for this purchase (retrieved via the ShopItem)
	 */
	public int getLength() {
		return (getShopItem().getLength());
	}

	/**
	 *get the width for this purchase (retrieved via the ShopItem)
	 */
	public int getWidth() {
		return (getShopItem().getWidth());
	}
	

	/**
	 * get the monthly maintenance cost for this room item 
	 */
	public int getMonthlyMaintenanceCost() {
		return (getShopItem().getMonthlyMaintCost());
	}

	
	public String toString() {
		return (getShopItem().name()).toLowerCase().replace('_', ' ');
	}



}
