package DecoratorSimulator;
/**
 * This class is the main entry point for the decorator simulator game.
 * 
 * @author Wim Thiels
 */

public class DecoratorSimulator {
	// if true than keep playing the game
	private static boolean keepPlaying = true;
	
	// if true than a new step will be executed (within a game)
	private static boolean newStep;

	public static void main(String[] args) {

		while (keepPlaying) {
			newStep = true;
			GamePlay currentGame = new GamePlay();
			while (newStep) {
				switch (currentGame.nextStep()) {
				case "Q": {   //end the game, and quit
					keepPlaying = false;
					newStep = false;
					break;
				}
				case "Y": {  //end the game, and start a new one
					newStep = false;
					break;
				}
				}
			}
		}
		System.out.println("Thanks for playing ! Bye.");
	}
}
