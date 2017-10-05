/**
 * 
 */
package DecoratorSimulator;

import java.util.Random;

/**
 * a class of put events (extending Event).  Whenever an item is put in a certain room location for the first time, a put event is created.   
 * This is the case when first buying an item and putting it in the room. or when moving an item in the room.
 * 
 * @author Wim Thiels
 *
 */
public class PutEvent extends Event {
	// the roomlocation
	final int row;
	final int col;
	final Room room;
	
	// the person that put the item there
	final Person mover;
	// the item that has been moved
	final RoomItem movedItem;
	

	public PutEvent(int row, int col, Person mover, RoomItem roomItem, Room room, int month) {
		super(month);
		this.row = row;
		this.col = col;
		this.room = room;
		this.mover = mover;
		this.movedItem = roomItem;
		roomItem.addEventToLog(this);

	}
	/**
	 * get the row number of this put event
	 */
	public int getRow() {
		return row;
	}

	/**
	 * get the col number of this put event
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * get the room of this put event
	 */
	public Room getRoom() {
		return room;
	}
	

	/**
	 * get the mover for this put event
	 */
	public Person getMover() {
		return mover;
	}

	/**
	 * get the moved item for this put event
	 */
	public RoomItem getMovedItem() {
		return movedItem;
	}


	
	
	@Override
	public void generateImpactOn(Guy guy) {
		if (getMover() == guy) {
			// 0.1 point cost per kg of weight
			int eventPoints = (int) ((-0.05 ) * (getMovedItem().getWeight()));
			//change eventpoints of guy, and also the item and event impact
			guy.addEventPoints(eventPoints);
			getMovedItem().addPointsGainedforGuy(eventPoints);
			addGuyScoreImpact(eventPoints);
		}

	}

	@Override
	public void generateImpactOn(Girlfriend gf) {
		// putevents will invoke comments of the girlfriend that can be used to
		// figure out the ideal colorplacement of the items
		try {
			Color idealColor = getRoom().getIdealColorAt(row, col);
			// if no object should be there, search if that color would be ideal
			// 1 position away. if so, comment on that
			if (idealColor == null) {
				Color movedItemColor = getMovedItem().getColor();
				outerloop:
				for (int rowItr = row - 1; rowItr <= row + 1; rowItr++) {
					for (int colItr = col - 1; colItr <= col + 1; colItr++) {
						try {
							idealColor = getRoom().getIdealColorAt(rowItr, colItr);
							
							if (idealColor == movedItemColor) {
								Random rand = new Random();
								int randomInt = rand.nextInt(4);
								switch (randomInt) {
								case 1:
									setGfComment("I like the color of that " + movedItem + " over there.  "
											+ "It's just not completely in the right spot ...");
									break;
								case 2:
								case 3:
									String direction = null;
									if (rowItr == row && colItr < col)
										direction = "left";
									if (rowItr == row && colItr > col)
										direction = "right";
									if (colItr == col && rowItr > row)
										direction = "down";
									if (colItr == col && rowItr < row)
										direction = "up";
									if (direction == null)
										direction = "diagonally";
									setGfComment("Try moving that " + movedItem + " " + direction + ".");
									break;
								case 4:
									setGfComment("That " + movedItem + " over there. " + "Try moving it a bit.");
									break;
								}
								break outerloop;
							}
						} catch (NotValidRoomLocationException e) {}
					}}
				

			} else {
				// if object is perfectly placed (location + color)
				if (idealColor == movedItem.getColor()) {
					setGfComment("That " + movedItem + " looks perfect in that spot !");
				}
				// if object is perfectly placed but color is wrong
				else {
					setGfComment("mmm, the color of that " + movedItem + ". it's like...not working for me. ");
				}

			}

		} catch (NotValidRoomLocationException e) {
			assert false;
			e.printStackTrace();

		}

	}



}
