package DecoratorSimulator;

import java.util.Random;

/**
 * a class of bad events.   a bad event is can be triggered in gameplay due to a bad situation (in debt,.. etc)
 * it has a person associated with it that caused this event (guilty person).
 * 
 * @author wim thiels
 *
 */
public class BadEvent extends Event {
	BadEventType badEventType;

	Person guiltyPerson;
	
	/**
	 * initialise this bad event with the given bad event type, given person, given month
	 * @param type
	 * 			the type of badevent (cfr enum)
	 * @param person
	 * 			the person that caused the badevent
	 * @param month
	 * 			the month of occurence of the event
	 */
	public BadEvent(BadEventType type, Person person, int month) {
		super(month);
		setBadEventType(type);
		this.guiltyPerson=person;
	}


	@Override
	public void generateImpactOn(Guy guy) {
		switch (getBadEventType()) {
		case noPurchase:
			// well rested, that deserves 2 points
			guy.addEventPoints(2);
			addGuyScoreImpact(2);
			break;
		case inDebt:
			guy.addEventPoints(-2);
			addGuyScoreImpact(-2);
			break;
		case deepInDebt:
			guy.addEventPoints(-4);
			addGuyScoreImpact(-4);
			break;
		case takingTooLong:
			break;
		default:
			break;
		}
	}

	@Override
	public void generateImpactOn(Girlfriend gf) {
		switch (getBadEventType()) {
		case noPurchase:
			if (guiltyPerson != gf) {
				Random rand = new Random();
				if (rand.nextDouble() > 0.3) {
					gf.addEventPoints(getBadnessLevel()*(-1));
					addGfMoodImpact(getBadnessLevel()*(-1));
					setGfComment("You didn't buy anything this month !? completely nothing ?");
				}
				break;
			}
		case inDebt:
			if (guiltyPerson != gf) {
				Random rand = new Random();
				if (rand.nextDouble() > 0.2) {
					gf.addEventPoints(getBadnessLevel()*(-1));
					addGfMoodImpact(getBadnessLevel()*(-1));
					setGfComment("hey big spender you.  Watch your expenses.");
				}
			}
			
			break;
		case deepInDebt:
			if (guiltyPerson != gf) {
				Random rand = new Random();
				if (rand.nextDouble() > 0.1) {
					gf.addEventPoints(getBadnessLevel()*(-1));
					addGfMoodImpact(getBadnessLevel()*(-1));
					setGfComment("Are you crazy ?! We're broke ! Stop spending that cash !!");
				}
				
			}
			break;
		case takingTooLong:
			if (guiltyPerson != gf) {
				Random rand = new Random();
				if (rand.nextDouble() > 0.5) {
					gf.addEventPoints(getBadnessLevel()*(-1) * (getMonthOfOccurence()/5) );
					addGfMoodImpact(getBadnessLevel()*(-1)* (getMonthOfOccurence()/5));
					setGfComment("Hurry up a little. This decorating thing is taking too long.");
				}
				
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * get the badness level for this bad event
	 */
	public int getBadnessLevel (){
		return (getBadEventType().getBadnessLevel());
	}
	/**
	 * get the type of bad event for this bad event
	 */
	public BadEventType getBadEventType() {
		return badEventType;
	}

	/**
	 * set the type of bad event event for this bad event
	 */
	public void setBadEventType(BadEventType badEventType) {
		this.badEventType = badEventType;
	}

	/**
	 * get the guilty person of this bad event
	 */
	public Person getGuiltyPerson() {
		return guiltyPerson;
	}

}
