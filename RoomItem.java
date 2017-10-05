/**
 * 
 */
package DecoratorSimulator;

/**
 * a class of room items.  A room item is a purchase that can be put inside a room.
 * It has a certain colour
 * @author wim thiels
 *
 */
public class RoomItem extends Purchase {

	private Color color;

	/**
	 * Initialise this room item with the given shopItem, given month of purchase, given buyer and given color
	 * 
	 * @param shopItem
	 * @param monthOfPurchase
	 * @param buyer
	 * @param col
	 */
	public RoomItem(ShopItem shopItem, int monthOfPurchase, Person buyer, Color col) {
		super(shopItem, monthOfPurchase, buyer);
		color = col;
	}

	/**
	 * get the visual representation of this room item 
	 */
	public String getRoomRep() {
		return (getColorCode().toLowerCase() + getItemCode());
	}

	/**
	 * get the color code of this room item
	 */
	public String getColorCode() {
		return (color.getColorCode());
	}

	/**
	 * get the color of this room item
	 */
	public Color getColor() {
		return color;
	}

}
