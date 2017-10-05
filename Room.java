package DecoratorSimulator;

/**
 * a class of rooms consisting of 
 * -> the dimensions (number of rows and columns), 
 * -> the location of the room items(=itemGrid), and
 * -> the ideal location of the colors according to the girlfriend (=colorIdealGrid)
 * 
 * @author Wim Thiels
 *
 */
public class Room {

	// the dimensions of the room
	private int numOfCols;
	private int numOfRows;
	
	// the location of the room items (=the actual room you could say)
	private RoomItem[][] itemGrid;
	
	//the ideal location of the colors according to the girlfriend
	private Color[][] colorIdealGrid;

	/**
	 * Initialise this room with the given rows and given columns
	 * @param rows
	 * @param cols
	 */
	public Room(int rows, int cols) {
		setNumOfCols(cols);
		setNumOfRows(rows);
		itemGrid = new RoomItem[rows][cols];
		colorIdealGrid = new Color[rows][cols];
	}

	
	/**
	 * get the number of columns for this room
	 */
	public int getNumOfCols() {
		return numOfCols;
	}
	
	/**
	 * set the number of columns of this room to the given value
	 * @param	numOfCols
	 */
	public void setNumOfCols(int numOfCols) {
		this.numOfCols = numOfCols;
	}

	/**
	 * get the number of rows of this room
	 */
	public int getNumOfRows() {
		return numOfRows;
	}

	/**
	 * set the number of rows of this room to the given value
	 * @param numOfRows
	 */
	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}


	/**
	 * get the placement of room items for this room
	 * @return the itemGrid
	 */
	public RoomItem[][] getItemGrid() {
		return itemGrid;
	}

	/**
	 * get the ideal placement of colors according to the girlfriend for this room
	 * @return the colorIdealGrid
	 */
	public Color[][] getColorIdealGrid() {
		return colorIdealGrid;
	}


	
	/**
	 * check if a roomitem with the given itemcode is present in the room
	 * @param 	itemCode
	 * @return	true, if a roomitem with the given itemcode is present in the room 
	 * 			otherwise false
	 */
	public boolean hasItem(String itemCode) {
		for (int row = 0; row < getNumOfRows(); row++) {
			for (int col = 0; col < getNumOfCols(); col++) {
				try {
					if (getItemAt(row, col) != null && getItemAt(row, col).getItemCode().equalsIgnoreCase(itemCode))
						return true;
				} catch (NotValidRoomLocationException e) {
					assert (false);
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * check that this room can have the given room item at the given location
	 * @param 	row
	 * @param 	col
	 * @param 	object
	 * @return	true if the room can have the given room item at the given location
	 * 			otherwise false
	 */
	public boolean canHaveItemAt(int row, int col, RoomItem object) {
		if (!isValidLocation(row, col))
			return false;
		else if (itemGrid[row][col] != null)
			return false;
		return true;
	}

	
	/**
	 * check if the given location is a valid location for this room
	 * @param 	row
	 * @param 	col
	 * @return	true if the given row number and given column number both fall within the
	 * 			dimensions of this room
	 * 			otherwise false
	 */
	public boolean isValidLocation(int row, int col) {
		if (row >= getNumOfRows())
			return false;
		if (col >= getNumOfCols())
			return false;
		if (row < 0)
			return false;
		if (col < 0)
			return false;
		return true;
	}

	
	/**
	 * put the given room item in the given location of this room
	 * @param row
	 * @param col
	 * @param roomItem
	 * @throws NotValidRoomLocationException
	 */
	public void putItemAt(int row, int col, RoomItem roomItem) throws NotValidRoomLocationException {
		if (!isValidLocation(row, col))
			throw new NotValidRoomLocationException();
		if (roomItem == null)
			throw new IllegalArgumentException("RoomItem is null");

		itemGrid[row][col] = roomItem;
	}

	/**
	 * remove the given roomitem from this room
	 * @param 	roomItem
	 * 			the room item that will be removed from this room
	 */
	public void removeItemFromRoom(RoomItem roomItem) {
		for (int row = 0; row < getNumOfRows(); row++) {
			for (int col = 0; col < getNumOfCols(); col++) {
				try {
					if (getItemAt(row, col) == roomItem)
						removeItemAt(row, col);
					;
				} catch (NotValidRoomLocationException e) {
					assert false;
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * get the room item at the given location in this room
	 * @param 	row
	 * @param 	col
	 * @return	the room item in the given location. 
	 * 			If no room item is present then null will be returned
	 * @throws NotValidRoomLocationException
	 */
	public RoomItem getItemAt(int row, int col) throws NotValidRoomLocationException {
		if (!isValidLocation(row, col))
			throw new NotValidRoomLocationException();
		return itemGrid[row][col];
	}

	/**
	 * remove a room item from the given location in this room.
	 * if no room item is present then this method will have no effect (no error given)
	 * @param row
	 * @param col
	 * @throws NotValidRoomLocationException
	 */
	public void removeItemAt(int row, int col) throws NotValidRoomLocationException {
		if (!isValidLocation(row, col))
			throw new NotValidRoomLocationException();
		itemGrid[row][col] = null;
	}

	/**
	 * get the ideal color at the given location in this room
	 * @param 	row
	 * @param 	col
	 * @return	the ideal color in the given location.  if no color is present at that location,
	 * 			then null will be returned
	 * @throws NotValidRoomLocationException
	 */
	public Color getIdealColorAt(int row, int col) throws NotValidRoomLocationException {
		if (!isValidLocation(row, col))
			throw new NotValidRoomLocationException();
		return colorIdealGrid[row][col];
	}
	
	
	/**
	 * set the ideal color at the given location in this room
	 * @param 	row
	 * @param 	col
	 * @param 	color
	 * @throws 	NotValidRoomLocationException
	 */
	public void setIdealColorAt(int row, int col, Color color) throws NotValidRoomLocationException {
		if (!isValidLocation(row, col))
			throw new NotValidRoomLocationException();
		colorIdealGrid[row][col] = color;
	}



	/**
	 * get the location of the given room item in this room
	 * @param 	roomitem
	 * @return	the location of the given room item given as an int array (e.g. [4, 2])
	 * @throws 	NoRoomItemFoundException
	 */
	public int[] getLocationOfItem(RoomItem roomitem) throws NoRoomItemFoundException {

		int[] location = new int[2];
		boolean itemFound = false;
		outerloop: for (int row = 0; row < getNumOfRows(); row++) {
			for (int col = 0; col < getNumOfCols(); col++) {
				try {
					if (getItemAt(row, col) == roomitem) {
						location[0] = row;
						location[1] = col;
						itemFound = true;
						break outerloop;
					}
				} catch (NotValidRoomLocationException e) {
					assert false;
					e.printStackTrace();
				}
			}
		}

		if (!itemFound)
			throw new NoRoomItemFoundException();

		return location;
	}



	/**
	 * calculate the likepoints for the given person for this room.  
	 * @param person
	 */
	public int calculateLikePoints(Person person) {
		int roomLikePoints = 0;
		int sumLikePoints=0;
		
		
		// go over every object in room and update Person and RoomItem points 
		for (int row = 0; row < getNumOfRows(); row++) {
			for (int col = 0; col < getNumOfCols(); col++) {
				try {
					RoomItem item = getItemAt(row, col);
					if (item != null) {
						if (person.getClass() == Guy.class) {
							roomLikePoints = item.getGuyLikes();
						}

						if (person.getClass() == Girlfriend.class) {
							roomLikePoints = item.getGfLikes();

						}
						//update the person
						sumLikePoints += roomLikePoints;
					}
				} catch (NotValidRoomLocationException e) {
					assert false;
					e.printStackTrace();
				}
			}
		}
		return sumLikePoints;
	}

	
	/**
	 * add the monthly maintenance cost to the room items of this room
	 * The total sum will be returned
	 * 
	 */
	public int addMaintenanceCostToRoomItems() {
		int sumMonth =0;
		RoomItem roomItem = null;
		// go over every object in room and update incurred cost by maintenance
		// cost
		for (int row = 0; row < getNumOfRows(); row++) {
			for (int col = 0; col < getNumOfCols(); col++) {
				try {
					roomItem = getItemAt(row, col);
				} catch (NotValidRoomLocationException e) {
					assert false;
					e.printStackTrace();
				}
				if (roomItem != null) {	
					roomItem.addIncurredCost(roomItem.getMonthlyMaintenanceCost());
					sumMonth+=roomItem.getMonthlyMaintenanceCost();
				
				}
			}
		}
		return sumMonth;
	}


	/**
	 * calculate the matchpoints for this room
	 * the matchpoints are calculated by comparing the colors of the roomitems to the ideal room
	 * @return	total matchpoints for this room
	 */
	public int calculateMatchPoints() {

		int matchPoints = 0;
		// paint room
		for (int row = 0; row < getNumOfRows(); row++) {
			for (int col = 0; col < getNumOfCols(); col++) {
				try {
					if (getItemAt(row, col) != null) {

						if (getIdealColorAt(row, col) == null)
							matchPoints -= 10;
						else {
							if (getIdealColorAt(row, col).getColorCode()
									.equalsIgnoreCase(getItemAt(row, col).getColorCode()))
								matchPoints += 5;
							else
								matchPoints += 1;
						}
					}
				} catch (NotValidRoomLocationException e) {
					assert false;
					e.printStackTrace();

				}
			}
		}

		return matchPoints;
	}

	/**
	 * check if this room is full
	 * @return	true if every location in this room is taken up by a room item
	 * 			otherwise false
	 */
	public boolean isFull() {
		boolean isFull = true;
		for (int row = 0; row < getNumOfRows(); row++) {
			for (int col = 0; col < getNumOfCols(); col++) {
				try {
					if (getItemAt(row, col) == null)
						return false;
				} catch (NotValidRoomLocationException e) {
					assert false;
					e.printStackTrace();
				}
			}
		}
		return isFull;
	}

	/**
	 * check if this room is empty
	 * @return	true if no location in this room is taken up by a room item
	 * 			otherwise false
	 */
	public boolean isEmpty() {
		boolean isEmpty = true;
		for (int row = 0; row < getNumOfRows(); row++) {
			for (int col = 0; col < getNumOfCols(); col++) {
				try {
					if (getItemAt(row, col) != null)
						return false;
				} catch (NotValidRoomLocationException e) {
					assert false;
					e.printStackTrace();
				}
			}
		}
		return isEmpty;

	}
}
