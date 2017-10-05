package DecoratorSimulator;

/**
 * a class of shop items
 * 
 * @author wim thiels
 *
 */
public enum ShopItem {
	STANDING_LAMP        (200 , 5,  5, 3, 1, 1, 0, '0',"La"), 
	PAINTING    (2000 , 15,  15, 4, 1, 1, 1, '1',"Pa"), 
	DINNER_TABLE       (650 , 0,  5, 50, 1, 1, 5, '0',"DT"), 
	FUTON(700, 10, -5,  20, 1, 1, 2, '0',"Fu"),
	BOOKSHELF(60, -10, 10, 5, 1, 1, 1, '1',"BS"),
	COUCH(400, 10, 5, 60, 1, 1, 7, '0',"Co"),
	VASE(200, -5, 10,  9, 1, 1, 2, '0',"Va"),
	LOUNGE_CHAIR(300, 10, 5,  20, 1, 1, 3, '0',"LC"),
	COFFEE_TABLE(450, 0, 10,  40, 1, 1, 2, '0',"CT"),
	
	FENGSHUI_CANDLE(70, -25, 25,  2, 1, 1, 1, '0',"FC"),
	CHAKRA_LAMP(200, -25, 25,  4, 1, 1, 2, '0',"CL"),
	BUDDHA_STATUE(250, -25, 20,  20, 1, 1, 1, '0',"Bu"),
	POTPOURRI_DISH(80, -25, 15,  3, 1, 1, 3, '0',"Po"),
	INCENSE_HOLDER(90,-25, 20,  3, 1, 1, 2, '0',"In"),
	
	STEREOSYSTEM(1100, 25, -25,  20, 1, 1, 4, '0',"SS"),
	MINIBAR(300, 25, -20,  60, 1, 1, 10, '0',"MB"),
	MINIREFRIGERATOR(200, 15, -15,  20, 1, 1, 7, '0',"MR"),
	BOXINGSACK(150, 25, -20,  40, 1, 1, 1, '0',"Bo"),
	LAVALAMP(30, 15, -5,  2, 1, 1, 1, '0',"LL"),
	XBOX_ONE(250, 20,-20,  4, 1, 1, 2, '0',"X1"),
	PINBALL_MACHINE(1500, 25, -25,  55, 1, 1, 2, '0',"PM");
	
	

	private final int price;
	private final int guyLikes; 
	private final int gfLikes;
	private final double weight;
	private final int length;  // in the current implementation of the game , this parameter is not used in the gameplay
	private final int width;   // in the current implementation of the game , this parameter is not used in the gameplay
	private final int monthlyMaintCost;
	private final char wallObjectIC; // in the current implementation of the game , this parameter is not used in the gameplay
	private final String itemCode;


	/**
	 * initialise this shopitem with the given values
	 * @param price
	 * @param guyLikes
	 * @param gfLikes
	 * @param weight
	 * @param length
	 * @param width
	 * @param monthlyMaintCost
	 * @param wallObjectIC
	 * @param paintRep
	 */
	private ShopItem(int price, int guyLikes, int gfLikes, double weight, int length, int width, int monthlyMaintCost,
			char wallObjectIC, String paintRep) {
		this.price = price;
		this.length = length;
		this.width = width;
		this.guyLikes = guyLikes;
		this.gfLikes = gfLikes;
		this.weight = weight;

		this.monthlyMaintCost = monthlyMaintCost;
		this.wallObjectIC = wallObjectIC;
		this.itemCode = paintRep;
	}



	/**
	 * get the price for this shop item
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * get the length for this shop item
	 */
	public int getLength() {
		return length;
	}

	/**
	 * get the guy likes for this shop item
	 */
	public int getGuyLikes() {
		return guyLikes;
	}

	/**
	 * get the girlfriend likes for this shop item
	 */
	public int getGfLikes() {
		return gfLikes;
	}

	/**
	 * get the weight for this shop item
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * get the monthly maintenance cost for this shop item
	 */
	public int getMonthlyMaintCost() {
		return monthlyMaintCost;
	}

	/**
	 * get the wall object indicator for this shop item
	 */
	public char getwallObjectIC() {
		return wallObjectIC;
	}

	/**
	 * get the itemcode for this shop item
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * get the width for this shop item
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * get the shop item for the given item code
	 * @param itemCode
	 * 			the itemcode for which the corresponding shop item must be retrieved
	 */
	public static ShopItem getValue(String itemCode) {
		ShopItem p = null;
		for (ShopItem x : values()) {
			if (x.itemCode.equalsIgnoreCase(itemCode)) {
				p = x;
			}
		}
		return p;
	}

	/**
	 * get a list of all the itemcodes of all the shop items
	 */
	public static String[] getShopItemList() {
		String[] optionArray = new String[ShopItem.values().length];
		int i = 0;
		for (ShopItem item : ShopItem.values()) {
			optionArray[i] = item.getItemCode();
			i++;
		}

		return optionArray;

	}

}
