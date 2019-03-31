package core;
import java.util.Arrays;

/**
 * A single one of the 'cubies' making up the whole Rubik's cube.
 */
public class Cubie {

	private char[] state_; // up/down, front/back, left/right
	private int id_;

	/**
	 * Create a cubie showing the specified symbols.
	 * 
	 * @param updown
	 *          the symbol facing up or down
	 * @param frontback
	 *          the symbol facing front or back
	 * @param leftright
	 *          the symbol facing left or right
	 */
	public Cubie ( char updown, char frontback, char leftright, int id ) {
		state_ = new char[3];
		state_[0] = updown;
		state_[1] = frontback;
		state_[2] = leftright;
		id_ = id;
	}

	@Override
	public String toString () {
		return "[" + id_ + "] " + state_[0] + state_[1] + state_[2];
	}

	public char updown () {
		return state_[0];
	}

	public char frontback () {
		return state_[1];
	}

	public char leftright () {
		return state_[2];
	}

	public int id () {
		return id_;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_;
		result = prime * result + Arrays.hashCode(state_);
		return result;
	}

	@Override
	public boolean equals ( Object obj ) {
		if ( this == obj ) return true;
		if ( obj == null ) return false;
		if ( getClass() != obj.getClass() ) return false;
		Cubie other = (Cubie) obj;
		if ( id_ != other.id_ ) return false;
		if ( !Arrays.equals(state_,other.state_) ) return false;
		return true;
	}

}