package core;
/**
 * The allowed moves for a Rubik's cube.
 */

public enum Move {

	LEFTUP("left-up"), LEFTDOWN("left-down"), RIGHTUP("right-up"),
	RIGHTDOWN("right-down"), TOPLEFT("top-left"), TOPRIGHT("top-right"),
	BOTTOMLEFT("bottom-left"), BOTTOMRIGHT("bottom-right"),
	LEFTHALF("left-half"), RIGHTHALF("right-half"), TOPHALF("top-half"),
	BOTTOMHALF("bottom-half");

	private String label_;

	private Move ( String label ) {
		label_ = label;
	}

	@Override
	public String toString () {
		return label_;
	}

}