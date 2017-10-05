/**
 * 
 */
package DecoratorSimulator;

/**
 * an enumclass with the colors that can be used during gameplay.  
 * Every color has a color code 
 * @author wim thiels
 *
 */
public enum Color {
	BLACK("B"), RED("R"), GREEN("G");

	private final String colorCode;

	/**
	 * initialise this color
	 */
	private Color(String code) {
		this.colorCode = code;
	}

	/**
	 * get the colour code for this colour
	 */
	public String getColorCode() {
		return colorCode;
	}
	
	
	/**
	 * get a list of all the color codes 
	 * @return	a string list of all the color codes
	 */
	public static String[] getColorsList() {
		String[] optionArray = new String[Color.values().length];
		int i = 0;
		for (Color col : Color.values()) {
			optionArray[i] = String.valueOf(col.getColorCode());
			i++;
		}

		return optionArray;

	}

	/**
	 * get the color corresponding to the given color code
	 * @param 	colorCode
	 * @return	the colour corresponding to the given color code		
	 */
	public static Color getValue(String colorCode) {
		Color p = null;
		for (Color x : values()) {
			if (String.valueOf(x.colorCode).equalsIgnoreCase(colorCode)) {
				p = x;
				break;
			}
		}
		return p;
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}



}
