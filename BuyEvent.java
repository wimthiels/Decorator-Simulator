
package DecoratorSimulator;

import java.util.Random;

/**
 * A class of buy events (extending Events) involving a purchase and a buyer
 * these will be created during gameplay whenever a purchase is bought.
 * @author wim thiels
 *
 */
public class BuyEvent extends Event {
	private Purchase purchase;
	private Person buyer;

	/**
	 * initialise this buyevent with the given purchase, given buyer and given month
	 */
	public BuyEvent(Purchase purchase, Person buyer, int month) {
		super(month);
		this.buyer = buyer;
		setPurchaseRelation(purchase);
	}

	/**
	 * set the purchase for this buy event.
	 * this buyevent is added to the eventlog of the given purchase
	 * @param purchase
	 */
	private void setPurchaseRelation(Purchase purchase) {
		this.purchase = purchase;
		purchase.addEventToLog(this);
	}
	
	/**
	 * get the buyer for this buy event
	 */
	public Person getBuyer() {
		return buyer;
	}


	/**
	 * get the purchase for this buy event
	 */
	public Purchase getPurchase() {
		return purchase;
	}
		
	@Override
	public void generateImpactOn(Guy guy) {
		// generate impact on the budget
		if (getBuyer() == guy) {
			guy.decreaseBudget(getPurchase().getPrice());
			addGuyBudgetImpact((-1) * getPurchase().getPrice());

		}
	}

	@Override
	public void generateImpactOn(Girlfriend gf) {
		// impact on budget if she bought something
		if (getBuyer() == gf) {
			gf.decreaseBudget(getPurchase().getPrice());
			addGfBudgetImpact((-1) * getPurchase().getPrice());
		}

		// if you bought her something that is really nice for her, this will
		// maybe lift her mood
		if (getPurchase().getGfLikes() > 2 && getBuyer() != gf) {
			Random rand = new Random();
			if (rand.nextDouble() > 0.5) {
				gf.addEventPoints(5);
				getPurchase().addPointsGainedforGf(5);
				addGfMoodImpact(5);
				setGfComment("You bought this for me ?  oh soooo sweet, thx !! : ) ");
			}
		}
		// see may reveal her favorite colour
		if (purchase instanceof RoomItem) {
			Color itemColor = ((RoomItem) getPurchase()).getColor();
			if (itemColor == gf.getFavoriteColor() && getBuyer() != gf) {
				Random rand = new Random();
				if (rand.nextDouble() > 0.6) {
					setGfComment(itemColor + "!  That's my favorite colour !");
				}
			}
		}
	}

}
