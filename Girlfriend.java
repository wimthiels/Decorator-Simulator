package DecoratorSimulator;

import java.util.Random;

/**
 * a class of girlfriends (extending Person) with 
	-> color preferences + a preferred spaciness :  together these will be used to construct the 'ideal room' according to the girlfriend
	-> roommatchpoints :  the points that are based on the degree of match between the 'ideal room' and the actual room
	
 * @author Wim Thiels
 */
public class Girlfriend extends Person {
	// the mood of the girlfriend can never be higher than MOODMAX.  it is also the startvalue
	private static final int MOODMAX = 100;
	// the mood of the girlfriend must not sink below this level. If so, game over
	private static final int MOODMIN = -20;
	// color preference for every color. every color is represented as a int[2] with the first number being the ordinal of the color,
	// and the second number a weight (= a number between 1 and 100)
	private int[][] colorPref;
	// the girlfriends preference for spaciness of a room (50 = 50% of room should be empty)
	private final int prefSpaciness;
	// recalculated every month based upon the match in color between the ideal room and the actual room
	private int roomMatchPoints;

	/**
	 * Initialise this girlfriend with the given budget and given monthly salary
	 * @param budget
	 * @param monthlySal
	 */
	public Girlfriend(int budget, int monthlySal) {
		super(budget, monthlySal, getMoodmax());
		this.prefSpaciness = 70;
		setRandomColorPreference();
		setRoomMatchPoints(0);
	}

	/**
	 * get the maximum mood for this girlfriend
	 */
	public static int getMoodmax() {
		return MOODMAX;
	}

	/**
	 * get the minimum mood for this girlfriend
	 */
	public static int getMoodmin() {
		return MOODMIN;
	}
	

	/**
	 * get the color preferences of this girlfriend
	 */
	public int[][] getColorPref() {
		return colorPref;
	}
	
	/**
	 * get the favorite color for this girlfriend
	 */
	public Color getFavoriteColor() {
		int max = 0;
		int maxIndex = 0;
		int[][] colorPref = getColorPref();
		for (int i = 0; i < getColorPref().length; i++) {
			if (colorPref[i][1] > max) {
				max = colorPref[i][1];
				maxIndex = colorPref[i][0];
			}
		}

		return Color.values()[maxIndex];
	}
	
	/**
	 * initialise the color preferences for this girlfriend in a random way
	 */
	private void setRandomColorPreference() {
		
		this.colorPref = new int[Color.values().length][2];
		
		Random Rand = new Random();
		for (Color c : Color.values()) {
			colorPref[c.ordinal()][0] = c.ordinal();
			colorPref[c.ordinal()][1] = Rand.nextInt(100);
		}
	}
	

	/**
	 * get the preferred spaciness of this girlfriend
	 */
	public int getPrefSpaciness() {
		return prefSpaciness;
	}

	/**
	 * get the room matchpoints for this girlfriend
	 */
	public int getRoomMatchPoints() {
		return roomMatchPoints;
	}

	/**
	 * set the room matchpoints for this girlfriend to the given value
	 */
	public void setRoomMatchPoints(int roomMatchPoints) {
		this.roomMatchPoints = roomMatchPoints;
	}

	
	/**
	 * calculate the mood of this girlfriend
	 */
	public int getMood() {
		int mood = (100 + getRoomLikePoints() + getRoomMatchPoints() + getEventPoints());
		if (mood > getMoodmax())
			mood = getMoodmax();
		return mood;
	}
	
	/**
	 * initialises the 'ideal room' based upon the colour preferences of this girlfriend and the room given.
	 * Constructing the ideal room means putting certain colours at certain positions in colorIdealGrid[] (cfr Room)
	 * this is done in a stochastic way, based upon the probability distribution given in the color preferences of this girlfriend.
	 * 
	 * @param	room
	 * 			the room for which the 'ideal room' will be initialised
	 */
	public void setIdealColorsForRoom(Room room) {

		int sumWeights = 0;
		for (int i = 0; i < Color.values().length; i++) {
			sumWeights += getColorPref()[i][1];
		}

		Random rand = new Random();
		for (int i = 0; i < room.getNumOfRows(); i++) {
			for (int j = 0; j < room.getNumOfCols(); j++) {
				if (rand.nextDouble() > ((double) getPrefSpaciness() / 100)) {
					int randomInt = rand.nextInt(sumWeights);
					int k = 0;
					while (randomInt > getColorPref()[k][1]) {
						randomInt -= getColorPref()[k][1];
						k++;
					}
					try {
						room.setIdealColorAt(i, j, Color.values()[k]);
					} catch (NotValidRoomLocationException e) {
						assert false;
						e.printStackTrace();
					}
				}

			}
		}
	}
	

	/**
	 * check whether this girlfriend wants to break up.
	 * 
	 * @return 	true if the mood of this girlfriend is lower than the minimum mood
	 * 			otherwise false
	 */
	public boolean wantsToBreakUp() {
		return (getMood() < getMoodmin());
	}
	@Override
	public String toString() {
		return "your girlfriend";
	}
}
