
package DecoratorSimulator;

import java.util.ArrayList;

/**
 * a class of screen painters.  A screen painter takes care of printing non-trivial output to the console
 * 
 * @author Wim Thiels
 *
 */
public final class ScreenPainter {
	

	/**
	 * paint the welcome screen
	 */
	public static void paintWelcomeScreen() {
		System.out.println("***************************************************************************");
		System.out.println("**************                                               **************");
		System.out.println("**************              ------------------               **************");
		System.out.println("**************              DECORATOR-SIMULATOR              **************");
		System.out.println("**************              -------------------              **************");
		System.out.println("**************                                               **************");
		System.out.println("***************************************************************************");
		System.out.println("***************************************************************************");
		System.out.println("");
	}

	/**
	 * paint the given room
	 * if the ideal room indicator is set to on, the ideal colors for that room are visualised
	 * @param room
	 * @param idealRoomIc
	 */
	public static void paintRoom(Room room, char idealRoomIc) {
		// separation topline
		System.out.println(
				"-------------------------------------------------------------------------------------------------------->>");
		// paint top row with columnnumbers
		System.out.print("    ");
		for (int col = 0; col < room.getNumOfCols(); col++) {
			System.out.print(" ");
			System.out.printf(" %02d ", col);
			// System.out.print(" ");
		}
		// paint top row with ##
		System.out.println();
		System.out.print("   #");
		for (int col = 0; col < room.getNumOfCols(); col++) {
			System.out.print("=###=");
		}
		System.out.println();

		// paint room
		for (int row = 0; row < room.getNumOfRows(); row++) {
			for (int col = 0; col < room.getNumOfCols(); col++) {
				// paint rownumbers
				if (col == 0) {
					System.out.printf("%02d", row);
					System.out.print(" #");
				}
				// paint room
				try {
					if (room.getItemAt(row, col) == null) {
						System.out.print("|");
						if (idealRoomIc == '1' && room.getIdealColorAt(row, col) != null) {
							System.out.printf("%4s", "->" + room.getIdealColorAt(row, col).getColorCode());
						} else {
							System.out.print("   ");
							System.out.print("|");
						}

					} else {
						if (idealRoomIc == '1' && room.getIdealColorAt(row, col) != null) {
							System.out.printf("%5s", "[" + room.getItemAt(row, col).getRoomRep().substring(0, 1) + "->"
									+ room.getIdealColorAt(row, col).getColorCode());
						} else {
							System.out.printf("%5s", "[" + room.getItemAt(row, col).getRoomRep() + "]");
						}
					}
				} catch (NotValidRoomLocationException e) {
					assert false;
					e.printStackTrace();
				}
			}

			System.out.print("#");
			System.out.println();
		}

		// paint bottom row with ##
		System.out.print("   #");
		for (int col = 0; col < room.getNumOfCols(); col++) {
			System.out.print("=###=");
		}
		System.out.println();
	}

	/**
	 * paint the scoreboard for the given guy, the given girlfriend and the given month
	 * @param guy
	 * @param gf
	 * @param currentMonth
	 */
	public static void paintScoreBoard(Guy guy, Girlfriend gf, int currentMonth) {
		System.out.println("   *****************************SCOREBOARD**************************************");

		System.out.printf("%-5s", "   * ");
		System.out.printf("%-15s", "CURRENT SCORE");
		System.out.printf("%-10s", ": " + guy.getTotalPoints());
		System.out.printf("%-10s", " ");
		System.out.printf("%-25s", "Girlfriends Mood ");
		System.out.printf("%-10s", ": " + gf.getMood());
		System.out.printf("%5s", "*");
		System.out.println();

		System.out.printf("%-5s", "   * ");
		System.out.printf("%-15s", "Budget");
		System.out.printf("%-10s", ": " + guy.getBudget());
		System.out.printf("%-10s", " ");
		System.out.printf("%-25s", "Current month");
		System.out.printf("%-10s", ": " + currentMonth);
		System.out.printf("%5s", "*");
		System.out.println();

		System.out.printf("%-5s", "   * ");
		System.out.printf("%-15s", "High Score");
		System.out.printf("%-10s", ": " + GamePlay.getHighScore());
		System.out.printf("%-10s", " ");
		System.out.printf("%-25s", "");
		System.out.printf("%-10s", ": ");
		System.out.printf("%5s", "*");
		System.out.println();

		System.out.println("   *****************************************************************************");
	}

	/**
	 * paint the option menu for the given options
	 * @param optionString
	 * 			the options that need to be visualised
	 */
	public static void paintOptionMenu(String[] optionString) {
		System.out.printf("%-77s", "   | Your options : ");
		for (int i = 0; i < optionString.length; i++) {
			// end the line, and open a new line
			if (i % 3 == 0) {
				System.out.printf("%3s", "|");
				System.out.println();
				System.out.print("   | ");
			}
			String option = optionString[i];
			switch (option) {
			case "B":
				System.out.printf("%-24s", "B=Buy Item");
				break;
			case "M":
				System.out.printf("%-24s", "M=Move Item");
				break;
			case "I":
				System.out.printf("%-24s", "I=Inspect Item");
				break;
			case "S":
				System.out.printf("%-24s", "S=Score History");
				break;
			case "N":
				System.out.printf("%-24s", "N=NEXT MONTH");
				break;
			case "E":
				System.out.printf("%-24s", "E=End game");
				break;
			case "Q":
				System.out.printf("%-24s", "Q=Quit");
				break;
			case "IB":
				System.out.printf("%-24s", "IB=Impulse Buy");
				break;
			case "SH":
				System.out.printf("%-24s", "SH=Shop");
				break;
			case "L":
				System.out.printf("%-24s", "L=Log");
				break;
			default:
				System.out.printf("%-24s", option + "=???");
				break;
			}
			// closing the optionbox nicely at the last line
			if (i == (optionString.length - 1)) {
				while (i % 3 != 2) {
					System.out.printf("%-24s", " ");
					i++;
				}
				System.out.printf("%3s", "|");
			}
		}

		System.out.println();
		System.out.println("   -----------------------------------------------------------------------------");
		System.out.print("Make your choice >>");
	}


	/**
	 * paint the header of the inspect panel for the given roomitem, given month and given current month
	 * @param item
	 * @param month
	 * @param currMonth
	 */
	public static void paintInspectHeader(RoomItem item, int month, int currMonth) {
		System.out.println("   *****************************INSPECT ITEM************************************");
		// line contains 80 characters
		System.out.printf("%-5s", "   * ");
		System.out.printf("%-70s", "This is a " + item.getColor() + " " + item + " " + item.getBuyer()
				+ " bought in month " + month);
		System.out.printf("%5s", "*");
		System.out.println();

		System.out.printf("%-5s", "   * ");
		System.out.printf("%-35s", "Points earned for you");
		System.out.printf("%-10s", ": " + item.getPointsGainedforGuy());
		System.out.printf("%30s", "*");
		System.out.println();
		System.out.printf("%-5s", "   * ");
		System.out.printf("%-35s", "Effect on your girlfriend's mood");
		System.out.printf("%-10s", ": " + item.getPointsGainedforGf());
		System.out.printf("%30s", "*");
		System.out.println();
		System.out.printf("%-5s", "   * ");
		System.out.printf("%-35s", "Total incurred cost");
		System.out.printf("%-10s", ": " + item.getIncurredCost() + " EUR");
		System.out.printf("%30s", "*");
		System.out.println();

		System.out.println("   *****************************************************************************");

	}


	/**
	 * paint the item specification for the given shop item
	 * @param shopItem
	 */
	public static void paintInspectItemSpecs(ShopItem shopItem) {
		System.out.printf("%-77s", "   | Item specs : " + shopItem.name());
		for (int i = 0; i < 9; i++) {
			// end the line, and open a new line
			if (i % 3 == 0) {
				System.out.printf("%3s", "|");
				System.out.println();
				System.out.print("   | ");
			}

			switch (i) {
			case 0:
				System.out.printf("%-12s", "Price");
				System.out.printf("%-12s", shopItem.getPrice() + " EUR");
				break;
			case 1:
				System.out.printf("%-12s", "GuyLikes");
				System.out.printf("%-12s", shopItem.getGuyLikes());
				break;
			case 2:
				System.out.printf("%-12s", "GfLikes");
				System.out.printf("%-12s", shopItem.getGfLikes());
				break;
			case 3:
				System.out.printf("%-12s", "Weight");
				System.out.printf("%-12s", shopItem.getWeight() + " kg");
				break;
			case 4:
				System.out.printf("%-12s", "Length");
				System.out.printf("%-12s", shopItem.getLength() + " unit");
				break;
			case 5:
				System.out.printf("%-12s", "Width");
				System.out.printf("%-12s", shopItem.getWidth() + " unit");
				break;
			case 6:
				System.out.printf("%-12s", "Itemcode");
				System.out.printf("%-12s", shopItem.getItemCode());
				break;
			case 7:
				System.out.printf("%-12s", "Cost/Month");
				System.out.printf("%-12s", shopItem.getMonthlyMaintCost() + " EUR");
				break;
			default:
				System.out.printf("%-24s", "");
				break;
			}
			// closing the optionbox nicely at the last line
			if (i == 8) {
				while (i % 3 != 2) {
					System.out.printf("%-24s", " ");
					i++;
				}
				System.out.printf("%3s", "|");
			}
		}

		System.out.println();
		System.out.println("   -----------------------------------------------------------------------------");
	}


	/**
	 * paint the given eventlog
	 */
	public static void paintEventLog(ArrayList<Event> eventLog) {
		// top line
		System.out.println("   *****************************EVENT LOG***********************************");

		// top events listed by month, reverse chronological
		int monthItr = 0;
		for (int i = eventLog.size() - 1; i >= 0; i--) {

			// First line with general explanation of event
			System.out.printf("%-5s", "   * ");
			Event currEvent = eventLog.get(i);

			if (monthItr != currEvent.getMonthOfOccurence()) {
				if (monthItr != 0) {
					GamePlay.askUserToContinueWithMessage();
					System.out.printf("%-5s", "   * ");
				}
				monthItr = currEvent.getMonthOfOccurence();
			}
			if (currEvent instanceof BuyEvent) {
				System.out.printf("%-70s",
						"[BUY,MONTH=" + monthItr + "]->" + ((BuyEvent) currEvent).getBuyer() + " bought a "
								+ ((RoomItem) ((BuyEvent) currEvent).getPurchase()).getColor() + " "
								+ ((BuyEvent) currEvent).getPurchase() + ".");
				System.out.println();
			}
			if (currEvent instanceof BadEvent) {
				System.out.printf("%15s", "[BAD,MONTH=" + monthItr + "]->");

				switch (((BadEvent) currEvent).getBadEventType()) {
				case noPurchase:
					System.out.print(((BadEvent) currEvent).getGuiltyPerson() + " didn't do anything this month!");
					break;
				case inDebt:
					System.out.print(((BadEvent) currEvent).getGuiltyPerson() + " are in debt!");
					break;
				case deepInDebt:
					System.out.print(((BadEvent) currEvent).getGuiltyPerson() + " are deep in debt !!");
					break;
				case takingTooLong:
					System.out.print(((BadEvent) currEvent).getGuiltyPerson() + " should hurry up");
					break;
				default:
					System.out.print(((BadEvent) currEvent).getGuiltyPerson() + " did something wrong...");
					break;
				}
				System.out.println();
			}
			if (currEvent instanceof PutEvent) {
				System.out.printf("%-70s", "[PUT,MONTH=" + monthItr + "]->" + ((PutEvent) currEvent).getMover()
						+ " moved a " + ((RoomItem) ((PutEvent) currEvent).getMovedItem()).getColor() + " "
						+ ((PutEvent) currEvent).getMovedItem() + " in the room to row "
						+ ((PutEvent) currEvent).getRow() + " and column " + ((PutEvent) currEvent).getCol() + ".");
				System.out.println();
			}

			// detailed lines that give the effect on points

			if (currEvent.getGfComment() != null && currEvent.getGfComment() != " ") {
				System.out.printf("%-5s", "   * ");
				System.out.printf("%-5s", " |-->");
				System.out.printf("%-74s", "Gf says : '" + currEvent.getGfComment() + "'");
				System.out.println();
			}
			if (currEvent.getGuyScoreImpact() != 0) {
				System.out.printf("%-5s", "   * ");
				System.out.printf("%-5s", " |-->");
				System.out.printf("%-74s", "Impact on your score : " + currEvent.getGuyScoreImpact());
				System.out.println();
			}
			if (currEvent.getGfMoodImpact() != 0) {
				System.out.printf("%-5s", "   * ");
				System.out.printf("%-5s", " |-->");
				System.out.printf("%-74s", "Impact on your girlfriends mood : " + currEvent.getGfMoodImpact());
				System.out.println();
			}
			if (currEvent.getGuyBudgetImpact() != 0) {
				System.out.printf("%-5s", "   * ");
				System.out.printf("%-5s", " |-->");
				System.out.printf("%-74s", "Impact on your budget : " + currEvent.getGuyBudgetImpact());
				System.out.println();
			}
			if (currEvent.getGfBudgetImpact() != 0) {
				System.out.printf("%-5s", "   * ");
				System.out.printf("%-5s", " |-->");
				System.out.printf("%-74s", "Impact on your girlfriend's budget : '" + currEvent.getGfBudgetImpact());
				System.out.println();
			}
		}

		System.out.println();
		System.out.println("   -----------------------------------------------------------------------------");
	}

	/**
	 * paint the shoplist
	 * 
	 */
	public static void paintShopList() {
		// iterate over all shopitems in groups of three
		int i = 0;
		for (ShopItem shopItem : ShopItem.values()) {
			System.out.println("   -----------------------------------------------------------------------------");
			paintInspectItemSpecs(shopItem);
			i++;
			if (i % 3 == 0)
				GamePlay.askUserToContinueWithMessage();
		}
		if (i % 3 != 0)
			GamePlay.askUserToContinueWithMessage();
	}

	/**
	 * paint the score history
	 * @param currMonth
	 * @param guy
	 * @param gf
	 */
	public static void paintMonthSummary(int currMonth, Guy guy, Girlfriend gf) {
		System.out.println("   *****************************SCORE HISTORY***********************************");
		// columnheaders
		System.out.printf("%-5s", "   * ");
		System.out.printf("%-10s", "");
		System.out.printf("%-5s", " ");
		System.out.printf("%-1s", "|");
		System.out.printf("%-9s", "LikePts");
		System.out.printf("%-1s", "|");
		System.out.printf("%-9s", "EventPts");
		System.out.printf("%-1s", "|");
		System.out.printf("%-9s", "MatchPts");
		System.out.printf("%-1s", "|");
		System.out.printf("%-9s", "Budget");
		System.out.printf("%-2s", "||");
		System.out.printf("%-17s", "Total Points/Mood");

		System.out.printf("%1s", "*");
		System.out.println();
		// the scorelog for every month
		// line contains 80 characters

		for (int month = currMonth - 1; month >= 0; month--) {
			int[] guyScoreMonth = guy.getHistScore(month);
			int[] gfScoreMonth = gf.getHistScore(month);
			System.out.printf("%-5s", "   * ");
			if (month == 0) {
				System.out.printf("%-5s", "START");
				System.out.printf("%-5s", " GAME");
			} else {
				System.out.printf("%-5s", "MONTH");
				System.out.printf("%-5s", ": " + month);
			}
			System.out.printf("%-5s", "|YOU>");
			System.out.printf("%-1s", "|");
			System.out.printf("%-9s", guyScoreMonth[1]);
			System.out.printf("%-1s", "|");
			System.out.printf("%-9s", guyScoreMonth[2]);
			System.out.printf("%-1s", "|");
			System.out.printf("%-9s", "  ---  ");
			System.out.printf("%-1s", "|");
			System.out.printf("%-9s", guyScoreMonth[3]);
			System.out.printf("%-1s", "||");
			System.out.printf("%-9s", guyScoreMonth[4]);
			System.out.printf("%9s", "*");
			System.out.println();
			System.out.printf("%-5s", "   * ");
			System.out.printf("%15s", "|GF> ");
			System.out.printf("%-1s", "|");
			System.out.printf("%-9s", gfScoreMonth[1]);
			System.out.printf("%-1s", "|");
			System.out.printf("%-9s", gfScoreMonth[2]);
			System.out.printf("%-1s", "|");
			System.out.printf("%-9s", gfScoreMonth[5]);
			System.out.printf("%-1s", "|");
			System.out.printf("%-9s", gfScoreMonth[3]);
			System.out.printf("%-1s", "||");
			System.out.printf("%-9s", gfScoreMonth[4]);
			System.out.printf("%9s", "*");
			System.out.println();
			if (month != 0)
				System.out.println("    --------------------------------------------------------------------------- ");
		}

		System.out.println("   *****************************************************************************");

	}
}


