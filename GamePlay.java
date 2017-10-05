
package DecoratorSimulator;

import java.io.FilterInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * This class represents gameplay-objects.  Each time the user enters a new game, a new gameplayobject will be created.  
 * It directs the flow, and keeps track of all data related to current game.  It also takes care off all user-interaction.
 * It does not take care of (non-trivial) console-output (that is handled by ScreenPainter)
 * The key method of the class is nextStep(), which forces the gameplay to proceed.
 * 
 * @author Wim Thiels
 *
 */
public class GamePlay {
	// all options the user can pick on the option menu
	private static final String[] MAINOPTIONS = { "SH", "B", "IB", "M", "I", "S", "L", "N", "E", "Q" }; 
	//the highest score over multiple games
	private static int highScore = 0;
	
	// the current player,girlfriend, room and month
	private Guy currentGuy;
	private Girlfriend currentGf;
	private Room currentRoom;
	private int currentMonth;
	
	// this variable steeres the gameflow (used in nextStep())
	private String nextStepKey;
	
	//a log of all the events that happened so far. (buy, move item etc)
	private ArrayList<Event> eventLog;
	

	////////////////////////////////////////////////////////////////////constructor
	/**
	 * creates a new gameplay with all variables initialised
	 */
	public GamePlay() {
		setCurrentGuy(new Guy(1000,2500));
		setCurrentGf(new Girlfriend(0,1000));
		setCurrentRoom(null);
		setNextStepKey(" ");
		this.eventLog = new ArrayList<Event>();
		setCurrentMonth(1);
	}

	////////////////////////////////////////////////////////////////////gamesteps

	/**
	 * this method executes the next step of this game based upon the nextStep variable.
	 * @return	the nextstep variable that will be used in the next iteration of this method
	 */
	public String nextStep() {

		switch (nextStepKey) {
		case " ":
			execWelcome();
			break;
		case "O":
			execOptionMain();
			break;
		case "B":
			execBuyRoomItem();
			break;
		case "IB":
			execImpulseBuyRoomItem(getCurrentGuy(),10,-100,null);
			break;
		case "SH":
			execShopList();
			break;
		case "M":
			execMoveRoomItem();
			break;
		case "I":
			execInspectItem();
			break;
		case "N":
			execNextMonth();
			break;
		case "E":
			execEndGame();
			break;
		case "L":
			execPaintEventLog();
			break;
		case "S":
			execPaintScoreLog();
			break;
		case "A1":
			execAbortGfBreakUp();
			break;
		case "A2":
			execAbortRoomFull();
			break;
		case "Q":
			break;
		}
		return nextStepKey;
	}
	/**
	 * Execute the intro part of the game. (the gamelogo, userinput for the roomcoordinates, initialise the 'ideal room')
	 */
	private void execWelcome() {
		// show game logo
		ScreenPainter.paintWelcomeScreen();

		//userinput: get the roomsize
		System.out.println("Give the roomsize please :  (e.g. '7 10') ");
		boolean roomSizeOk = false;
		while (!roomSizeOk) {
			int[] sizeRoom = askUserTwoIntegers();
			if (sizeRoom[0] < 2 || sizeRoom[1] < 2)
				System.out.println("That's too small. Each dimension must be at least 2.  Try again please ");
			else {
				roomSizeOk = true;
				setCurrentRoom(new Room(sizeRoom[0], sizeRoom[1]));
			}
		}

		// compose the perfect colors of the room based on the preferences of the girlfriend
		getCurrentGf().setIdealColorsForRoom(getCurrentRoom());

		//proceed with the main menu
		System.out.println("Let's start the game !");
		setNextStepKey("O");

	}
	/**
	 * Execute the main panel of the game.  This panel shows 
	 * 	-the room
	 *  -the score board 
	 *  -an overview of the possible user options
	 *  
	 *  the user will have to choose one of the options given
	 */
	private void execOptionMain() {

		ScreenPainter.paintRoom(getCurrentRoom(), '0');
		ScreenPainter.paintScoreBoard(getCurrentGuy(), getCurrentGf(), getCurrentMonth());
		ScreenPainter.paintOptionMenu(MAINOPTIONS);
		setNextStepKey(askUserInputOption(MAINOPTIONS));

	}
	/**
	 * Execute the scorelog.  
	 * this is a chronological overview of the scores
	 */
	private void execPaintScoreLog() {
		ScreenPainter.paintMonthSummary(getCurrentMonth(), getCurrentGuy(), getCurrentGf());
		askUserToContinueWithMessage();
		setNextStepKey("O");
	}

	/**
	 * Execute the 'move item' option.  
	 * The user can choose to move an existing roomitem from one location to the next
	 */
	private void execMoveRoomItem() {

		// get the roomitem that the user wants to move
		RoomItem chosenItem = null;
		while (chosenItem == null) {
			try {
				chosenItem = askUserPickRoomItem();
			} catch (RoomIsEmptyException e) {
				System.out.println("The room is empty ! nothing to move.  Choose another option ...");
				return;
			}
		}
		// free the old location
		getCurrentRoom().removeItemFromRoom(chosenItem);

		// move the item to the new location the user chooses
		putItemInRoomLocation(chosenItem);

		// paint mainmenu
		setNextStepKey("O");
	}
	
	/**
	 * put an item in a certain position in the room, based on the input from the user
	 * this will trigger a put event
	 * @param  item
	 * 		the item that will be put somewhere in the room
	 */
	private void putItemInRoomLocation(RoomItem item) {
		int [] chosenLocation = new int[2];
		// ask the user for a location
		boolean locationFound = false;
		try {
		while (!locationFound) {
			System.out.println("Where do you want to place the " + item + " ? [row col]  >>");
			chosenLocation = askUserTwoIntegers();
			if (!getCurrentRoom().canHaveItemAt(chosenLocation[0], chosenLocation[1], item)) {
				System.out.println("You cannot put the " + item + " in that spot ! Try again..>>");
			} else
				locationFound = true;
		}

		// put the object in the location
	
			getCurrentRoom().putItemAt(chosenLocation[0], chosenLocation[1], item);
		} catch (NotValidRoomLocationException e) {
			assert false;
			e.printStackTrace();
		}

		// add a PUT event to the log
		eventLog.add(new PutEvent(chosenLocation[0], chosenLocation[1], getCurrentGuy(), item, getCurrentRoom(), getCurrentMonth()));

		//give feedback
		System.out.println(">>The " + item + " is moved to location [" + chosenLocation[0] + " " + chosenLocation[1]+ "]");
	}

	/**
	 * shows the event log
	 * this is a reverse chronological list of all the events that happened so far
	 */
	private void execPaintEventLog() {
		ScreenPainter.paintEventLog(eventLog);
		askUserToContinueWithMessage();
		setNextStepKey("O");
	}

	/**
	 * shows the shoplist
	 * this is an overview of all the items that can be bought (with all their specifications)
	 */
	private void execShopList() {
		// same layout as item specs , but now scroll through all items
		ScreenPainter.paintShopList();
		setNextStepKey("O");
	}

	/**
	 * proceeds the game to the next month.
	 * This method will update the points and budget for guy and girlfriend. this involves
	 * - calculating the roompoints (based on the likepoints of the items in the room)
	 * - calculating the matchpoints(based on the match between the colors in the room and the ideal colors of the room (according to the girlfriend))
	 * - processing the events for that month
	 * - adding the monthly salary to the budget
	 * - calculating the maintenancecosts of all the items in the room
	 * - logging of the scores (for the scorelog overview)
	 * - possibly ending the game if the mood of girl has sunken too far
	 */
	private void execNextMonth() {
		// intro
		System.out.println(">>Processing next month...");
		
		// if the girlfriend's budget is big enough she will buy something she likes in her favorite color (1/3 chance)
		// if she is in a good mood she will buy something that the guy likes, otherwise is is strictly for herself
		if (getCurrentGf().getBudget() > 500) {
			Random rand = new Random();
			if (rand.nextInt(100) > 66 ){
				if (getCurrentGf().getMood() > 75)
					execImpulseBuyRoomItem(getCurrentGf(),10, 0, getCurrentGf().getFavoriteColor());
				else 
					execImpulseBuyRoomItem(getCurrentGf(),-100, 10, getCurrentGf().getFavoriteColor());
			}
			
		}

		// calculate roompoints guy + girlfriend
		int roomLikePoints=getCurrentRoom().calculateLikePoints(getCurrentGuy());
		getCurrentGuy().setRoomLikePoints(roomLikePoints);
		
		roomLikePoints= getCurrentRoom().calculateLikePoints(getCurrentGf());
		getCurrentGf().setRoomLikePoints(roomLikePoints);
		

		// calculate room match points for girlfriend
		int matchPoints = getCurrentRoom().calculateMatchPoints();
		getCurrentGf().setRoomMatchPoints(matchPoints);

		//Bad event : every 5 months the girlfriend can get impatient
		if (getCurrentMonth()%5==0){
			BadEvent badEvent = new BadEvent(BadEventType.takingTooLong, getCurrentGuy(), getCurrentMonth());
			eventLog.add(badEvent);
		}
		


		// process eventlog previous month
		// I could go backwards in time, but always better to process
		// chronological. if I let events throw events then I must go chronological !
		ListIterator<Event> itr = eventLog.listIterator(eventLog.size());
		while (itr.hasPrevious()) {
			if (itr.previous().getMonthOfOccurence() != getCurrentMonth()) {
				itr.next(); // move back in position (first event of current month)
				break;
			}
		}
		int eventsProcessed = 0;
		while (itr.hasNext()) {
			Event currentEvent = itr.next();

			currentEvent.generateImpactOn(getCurrentGuy());
			currentEvent.generateImpactOn(getCurrentGf());
			eventsProcessed++;
	//		System.out.println("processed event " + currentEvent);
		}
		
		// if no events : bad event : lazy !
		if (eventsProcessed == 0) {
			BadEvent badEvent = new BadEvent(BadEventType.noPurchase, getCurrentGuy(), getCurrentMonth());
			eventLog.add(badEvent);
			badEvent.generateImpactOn(getCurrentGuy());
			badEvent.generateImpactOn(getCurrentGf());
		}
		
		// add monthly maintenance cost to incurred cost items, and decrease the budget with the total
		int maintenanceCost = getCurrentRoom().addMaintenanceCostToRoomItems();
		getCurrentGuy().decreaseBudget(maintenanceCost);
		System.out.println(">>You got charged " + maintenanceCost +  " EUR in maintenance costs");

		// adjust budget with salary
		int budgetIncrease = getCurrentGuy().addMonthlySalaryToBudget();
		System.out.println(">>Your paycheck has arrived.  Your budget is increased by " + budgetIncrease +  " EUR.");
		getCurrentGf().addMonthlySalaryToBudget();
		

		// write month scores to history log
		getCurrentGuy().setHistScoreMonth(getCurrentMonth(), getCurrentGuy().getRoomLikePoints(), getCurrentGuy().getEventPoints(),
				getCurrentGuy().getBudget(), getCurrentGuy().getTotalPoints(), 0);
		getCurrentGf().setHistScoreMonth(getCurrentMonth(), getCurrentGf().getRoomLikePoints(), getCurrentGf().getEventPoints(),
				getCurrentGf().getBudget(), getCurrentGf().getMood(), getCurrentGf().getRoomMatchPoints());
		
		
		// switch to next month
		setCurrentMonth(getCurrentMonth() + 1);


		// check budget below zero : bad event
		if (getCurrentGuy().getBudget() < 0) {
			if (getCurrentGuy().getBudget() < -1000) {
				BadEvent badEvent = new BadEvent(BadEventType.deepInDebt, getCurrentGuy(), getCurrentMonth());
				eventLog.add(badEvent);
			} else {
				BadEvent badEvent = new BadEvent(BadEventType.inDebt, getCurrentGuy(), getCurrentMonth());
				eventLog.add(badEvent);
			}
		}

		// outro
		if (getCurrentGf().wantsToBreakUp())
			setNextStepKey("A1");
		else
			setNextStepKey("O");
		
		//wait a bit
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

	}



	/**
	 * Execute the 'buy item' option
	 * the player gets to choose an item and put is somewhere in the room
	 * this will trigger a buy-event, and also a put-event
	 */
	private void execBuyRoomItem() {
		// check room is full
		if (getCurrentRoom().isFull()) {
			setNextStepKey("A1");
			return;
		}
		// user chooses the item
		System.out.println("Which item do you want to buy ? >>");
		String itemChosen = askUserInputOption(ShopItem.getShopItemList());

		if (getCurrentRoom().hasItem(itemChosen)) {
			System.out.println(">>You already have a " + ShopItem.getValue(itemChosen)
					+ " in the room.  You might want to reconsider...");
			askUserToContinueWithMessage();
			setNextStepKey("O");
			return;
		}

		System.out.println("What is your preferred color for the item ? >>");
		String colorChosen = askUserInputOption((Color.getColorsList()));

		// buy the item
		RoomItem itemBought = new RoomItem(ShopItem.getValue(itemChosen), getCurrentMonth(), getCurrentGuy(),
				Color.getValue(colorChosen));
		// add buyevent to the eventlog
		eventLog.add(new BuyEvent(itemBought, getCurrentGuy(), getCurrentMonth()));
				
		//give feedback
		System.out.println(">>" + getCurrentGuy() + " just bought a " + Color.getValue(colorChosen) + " " + itemBought);

		// put the item in the location the user chooses
		putItemInRoomLocation(itemBought);


		// if room is full, then game over, abort the game
		if (getCurrentRoom().isFull()) {
			setNextStepKey("A2");
			return;
		}

		//proceed with the main menu
		setNextStepKey("O");
	}

	/**
	 * Execute the 'impulse buy' option
	 * this in in effect the same as the 'buy' option, but here a random room item is bought
	 * and this room item is put somewhere random in the room.  So no user input is needed
	 * The only difference is that here duplicate items are allowed
	 */
	private void execImpulseBuyRoomItem(Person buyer, int guyLikeMin, int gfLikeMin, Color favColor) {
		// get randomItem
		Random rand = new Random();
		int randomRoomItemNr = rand.nextInt(ShopItem.values().length);
		while (ShopItem.values()[randomRoomItemNr].getGfLikes()<gfLikeMin || ShopItem.values()[randomRoomItemNr].getGuyLikes()<guyLikeMin)
			randomRoomItemNr = rand.nextInt(ShopItem.values().length);
		// get randomColor
		int randomColorNr = rand.nextInt(Color.values().length);

		// buy the item
		Color colorChosen;
		if (favColor==null) 
		colorChosen = Color.values()[randomColorNr];
		else 
			colorChosen = favColor;

		RoomItem itemBought = new RoomItem(ShopItem.values()[randomRoomItemNr], getCurrentMonth(), buyer,
				colorChosen);
				
		// add buyevent to the eventlog
		eventLog.add(new BuyEvent(itemBought, buyer, getCurrentMonth()));

		// get random location in room
		boolean locationFound = false;
		int randomRowNr = 0;
		int randomColNr = 0;
		while (!locationFound) {
			randomRowNr = rand.nextInt(getCurrentRoom().getNumOfRows());
			randomColNr = rand.nextInt(getCurrentRoom().getNumOfCols());
			if (getCurrentRoom().canHaveItemAt(randomRowNr, randomColNr, itemBought)) {
				locationFound = true;
				// put the object in the location
				try {
					getCurrentRoom().putItemAt(randomRowNr, randomColNr, itemBought);
				} catch (NotValidRoomLocationException e) {
					assert false;
					e.printStackTrace();
				}
				System.out.println(">>" + buyer + " just bought a " + colorChosen + " " + itemBought
						+ ".  And put it in position (" + randomRowNr + "," + randomColNr + ")");
			}
		}

		// add putevent to the eventlog
		eventLog.add(new PutEvent(randomRowNr, randomColNr, getCurrentGuy(), itemBought, getCurrentRoom(), getCurrentMonth()));

		if (getCurrentRoom().isFull()) {
			setNextStepKey("A2");
			return;
		}
		setNextStepKey("O");
	}

	/**
	 * Executes the 'inspect item' option
	 * the user can pick a roomitem and will get a detailed overview off all the info related to this roomitem
	 * this comprises of 
	 * -  the effect of that roomitem on the points
	 * -  the specs of the item
	 * -  the events involving that item
	 */
	private void execInspectItem() {
		// let the user choose a roomitem
		RoomItem chosenItem;
		try {
			chosenItem = askUserPickRoomItem();
		} catch (RoomIsEmptyException e) {
			System.out.println("The room is empty ! Nothing to inspect, choose another option ...");
			return;
		}

		// OUTPUT
		// 1 line summary + points gained and gf comment
		ScreenPainter.paintInspectHeader(chosenItem, chosenItem.getMonthOfPurchase(), getCurrentMonth());

		// RoomItemcharacteristics = enum
		ScreenPainter.paintInspectItemSpecs(chosenItem.getShopItem());

		// events linked to that object 
		ScreenPainter.paintEventLog(chosenItem.getEventLog());

		// ask user to continue to main
		askUserToContinueWithMessage();

		//proceed with main
		setNextStepKey("O");
	}


	/**
	 * Executes the 'end game' option
	 * this will end the current game and start a new game
	 * if the total score is a high score this will be recorded
	 * the player get's to see the colors of the ideal room according to the girlfriend
	 */
	private void execEndGame() {
		
		// record the high score
		System.out.println("OK. ");
		int totalPoints = getCurrentGuy().getTotalPoints();
		System.out.println("Your end score is " + totalPoints + " points");
		if (totalPoints > highScore) {
			System.out.println("That's a high score !");
			setHighScore(totalPoints);
		}
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		// show the ideal colors according to the girlfriend
		System.out.println("By the way, these were the colors your girlfriend had in mind :");
		ScreenPainter.paintRoom(getCurrentRoom(), '1');
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//proceed with a new game
		setNextStepKey("Y");
	}

	
	/**
	 * execute the 'abort game' option, specifically for the scenario where the girlfriend
	 * decides to leave
	 * this is not a user picked option, but an option that is enforced by the gameplay
	 * (namely, when the mood of the girlfriend has sunken to far below zero)
	 */
	private void execAbortGfBreakUp() {
		System.out.println("Your girlfriend can't take it anymore...");
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("her mood is sunken to an historical low...");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("she's leaving you ..  :-( ");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("your points are set to zero :-( ");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("You can decorate the room anyway you like now !");
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			setNextStepKey("Q");
		}
	}

	/**
	 * execute the 'abort game' option, specifically for the scenario where the room
	 * is completely filled with items
	 * this is not a user picked option, but an option that is enforced by the gameplay
	 * (namely, when no space is left in the room)
	 */
	private void execAbortRoomFull() {
		System.out.println("...");
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("You got to be kidding...");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("you filled up the entire room with stuff .. ");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("You know this is an apartment, not a shipcontainer, right ? ");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("sorry, man.  disqualified");
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			setNextStepKey("Q");
		}
	}
	////////////////////////////////////////////////////////////////////capture user input
	/**
	 * ask the user to pick an item in the room by giving the roomcoordinates of that item.
	 * @return the RoomItem object that was picked by the user
	 * @throws RoomIsEmptyException
	 */
	private RoomItem askUserPickRoomItem() throws RoomIsEmptyException {

		// first check if room is empty
		if (getCurrentRoom().isEmpty())
			throw new RoomIsEmptyException();

		// user input location of item
		RoomItem chosenItem = null;
		while (chosenItem == null) {
			System.out.println("Give the roomcoordinates of the item please. (e.g. 5 10)  >>");
			int [] chosenLocation = askUserTwoIntegers();

			// get object from location and check if ok
			try {
				chosenItem = getCurrentRoom().getItemAt(chosenLocation[0], chosenLocation[1]);
			} catch (NotValidRoomLocationException e) {
//				System.out.println("That is not a valid roomlocation ! ... try again ");
			}

			if (chosenItem == null) {
				System.out.println("There is no object in that location ... try again ");
			}
		}
		return chosenItem;
	}

	/**
	 * ask the user for input.   the input the user is allowed to give, is limited to the set of values 
	 * given in the options-parameter.  
	 * @param 	options
	 * 			the string array that contains all the allowed values the user can choose from (case-independent)
	 * @return	the option chosen by the user
	 */
	private String askUserInputOption(String[] options) {
		String choice = "";
		String regEx = "";
		for (int i = 0; i < options.length; i++) {
			if (i != 0)
				regEx += "|";
			regEx += options[i];
		}

		try (Scanner sc = new Scanner(new FilterInputStream(System.in) {
			@Override
			public void close() throws IOException {
				// this override is necessary, otherwise system.in will be closed !
			}
		});) {
			sc.useDelimiter(Pattern.compile("(\r\n)"));
			Pattern regPattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			while (!sc.hasNext(regPattern)) {
				System.out.println(
						"That's not a valid option, please try again (use " + Arrays.toString(options) + ")>>");
				sc.nextLine();

			}
			choice = sc.next().toUpperCase();
		}

		return choice;
	}

	/**
	 * ask the user for 2 integers
	 * this is needed to get a room location, or to get the roomsize
	 * @return	the two integers as a integer-array
	 */
	private int[] askUserTwoIntegers() {

		int[] loc = new int[2];
		try (Scanner sc = new Scanner(new FilterInputStream(System.in) {
			@Override
			public void close() throws IOException {
				// this override is necessary, otherwise system.in will be closed !
			}
		});) {
			// sc.useDelimiter(Pattern.compile("(\r\n)"));
			int x = 0;
			while (x < 2) {
				if (sc.hasNext()) {
					if (sc.hasNextInt()) {
						loc[x] = sc.nextInt();
						x++;
					} else
						sc.next();
				}
			}
		}
		return loc;
	}

	/**
	 * ask the user to "press enter to continue"
	 */
	public static void askUserToContinueWithMessage() {
		askUserToContinueWithMessage("Press ENTER to continue");
		return;
	}

	/**
	 * ask the user the continue where the message is given as a parameter
	 * @param message
	 */
	public static void askUserToContinueWithMessage(String message) {
		//  static public function : so screenpainter can use it
		System.out.println("   <<<<<<<<<<" + message + ">>>>>>>>>");

		try (Scanner sc = new Scanner(new FilterInputStream(System.in) {
			@Override
			public void close() throws IOException {
				// this override is necessary, otherwise system.in will be closed !
			}
		});) {

			if (sc.hasNextLine()) {
				return;
			}

		}
		return;
	}

	
	////////////////////////////////////////////////////////////////////getters and setters
	
	/**
	 * Return the current guy of this game
	 */
	private Guy getCurrentGuy() {
		return currentGuy;
	}
	/**
	 * Setter for the current guy of this game
	 */
	private void setCurrentGuy(Guy guy) {
		currentGuy = guy;
	}
	
	
	
	/**
	 * Return the current girlfiend of this game
	 */
	private Girlfriend getCurrentGf() {
		return currentGf;
	}
	/**
	 * Setter for the current girlfiend of this game
	 */
	private void setCurrentGf(Girlfriend gf) {
		currentGf = gf;
	}
	
	
	
	/**
	 * return the current room of this game
	 */
	private Room getCurrentRoom() {
		return currentRoom;
	}
	/**
	 * Setter for the current room of this game
	 */
	private void setCurrentRoom(Room Room) {
		this.currentRoom = Room;
	}
	
	
	
	/**
	 * Return the current month of this game
	 */
	private int getCurrentMonth() {
		return currentMonth;
	} 
	/**
	 * Setter for the current month of this game
	 */
	private void setCurrentMonth(int month) {
		currentMonth =  month;
	} 
	
	
	/**
	 * Setter for the next step of this game
	 */
	private void setNextStepKey(String next) {
		nextStepKey = next;
	}


	/**
	 * return the high score (over multiple games)
	 */
	public static int getHighScore() {
		return highScore;
	}

	/**
	 * set the high score
	 */
	private static void setHighScore(int highScore) {
		GamePlay.highScore = highScore;
	}

}
