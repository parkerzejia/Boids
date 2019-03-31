package core;

/**
 * A 2x2x2 "pocket" cube.
 */
public class Cube222 extends Cube {

	// NUMMOVES[targ][src]
	private int[][] NUMMOVES = { { 0, 1, 2, 1, 1, 2, 3, 2 },
	                            { 1, 0, 1, 2, 2, 1, 2, 3 },
	                            { 2, 1, 0, 1, 3, 2, 1, 2 },
	                            { 1, 2, 1, 0, 2, 3, 2, 1 },
	                            { 1, 2, 3, 2, 0, 1, 2, 1 },
	                            { 2, 1, 2, 3, 1, 0, 1, 2 },
	                            { 3, 2, 1, 2, 2, 1, 0, 1 },
	                            { 2, 3, 2, 1, 1, 2, 1, 0 } };

	// 8 cubies, 3 orientations for each
	private Cubie[] state_;

	/**
	 * Create a new cube in the 'solved' state.
	 */
	public Cube222 () {
		state_ = new Cubie[8];
		state_[0] = new Cubie('O','W','G',0); // front, clockwise
		state_[1] = new Cubie('O','W','B',1);
		state_[2] = new Cubie('R','W','B',2);
		state_[3] = new Cubie('R','W','G',3);
		state_[4] = new Cubie('O','Y','G',4); // back, behind front
		state_[5] = new Cubie('O','Y','B',5);
		state_[6] = new Cubie('R','Y','B',6);
		state_[7] = new Cubie('R','Y','G',7);
	}

	@Override
	public String toString () {
		return state_[0] + " " + state_[1] + " " + state_[2] + " " + state_[3]
		    + "  " + state_[4] + " " + state_[5] + " " + state_[6] + " "
		    + state_[7];
	}

	@Override
	public void rotate ( Move move ) {
		switch ( move ) {
		case LEFTUP:
			rotateLeftUp();
			break;
		case LEFTDOWN:
			rotateLeftDown();
			break;
		case RIGHTUP:
			rotateRightUp();
			break;
		case RIGHTDOWN:
			rotateRightDown();
			break;
		case TOPLEFT:
			rotateTopLeft();
			break;
		case TOPRIGHT:
			rotateTopRight();
			break;
		case BOTTOMLEFT:
			rotateBottomLeft();
			break;
		case BOTTOMRIGHT:
			rotateBottomRight();
			break;
		case LEFTHALF:
			rotateLeftUp();
			rotateLeftUp();
			break;
		case RIGHTHALF:
			rotateRightUp();
			rotateRightUp();
			break;
		case TOPHALF:
			rotateTopLeft();
			rotateTopLeft();
			break;
		case BOTTOMHALF:
			rotateBottomLeft();
			rotateBottomLeft();
			break;
		}
	}

	private void rotateLeftUp () {
		Cubie[] newstate = new Cubie[8];
		System.arraycopy(state_,0,newstate,0,8);

		newstate[0] =
		    new Cubie(state_[3].frontback(),state_[3].updown(),
		              state_[3].leftright(),state_[3].id());
		newstate[3] =
		    new Cubie(state_[7].frontback(),state_[7].updown(),
		              state_[7].leftright(),state_[7].id());
		newstate[7] =
		    new Cubie(state_[4].frontback(),state_[4].updown(),
		              state_[4].leftright(),state_[4].id());
		newstate[4] =
		    new Cubie(state_[0].frontback(),state_[0].updown(),
		              state_[0].leftright(),state_[0].id());

		System.arraycopy(newstate,0,state_,0,8);
	}

	private void rotateLeftDown () {
		Cubie[] newstate = new Cubie[8];
		System.arraycopy(state_,0,newstate,0,8);

		newstate[0] =
		    new Cubie(state_[4].frontback(),state_[4].updown(),
		              state_[4].leftright(),state_[4].id());
		newstate[3] =
		    new Cubie(state_[0].frontback(),state_[0].updown(),
		              state_[0].leftright(),state_[0].id());
		newstate[7] =
		    new Cubie(state_[3].frontback(),state_[3].updown(),
		              state_[3].leftright(),state_[3].id());
		newstate[4] =
		    new Cubie(state_[7].frontback(),state_[7].updown(),
		              state_[7].leftright(),state_[7].id());

		System.arraycopy(newstate,0,state_,0,8);
	}

	private void rotateRightUp () {
		Cubie[] newstate = new Cubie[8];
		System.arraycopy(state_,0,newstate,0,8);

		newstate[1] =
		    new Cubie(state_[2].frontback(),state_[2].updown(),
		              state_[2].leftright(),state_[2].id());
		newstate[2] =
		    new Cubie(state_[6].frontback(),state_[6].updown(),
		              state_[6].leftright(),state_[6].id());
		newstate[6] =
		    new Cubie(state_[5].frontback(),state_[5].updown(),
		              state_[5].leftright(),state_[5].id());
		newstate[5] =
		    new Cubie(state_[1].frontback(),state_[1].updown(),
		              state_[1].leftright(),state_[1].id());

		System.arraycopy(newstate,0,state_,0,8);
	}

	private void rotateRightDown () {
		Cubie[] newstate = new Cubie[8];
		System.arraycopy(state_,0,newstate,0,8);

		newstate[1] =
		    new Cubie(state_[5].frontback(),state_[5].updown(),
		              state_[5].leftright(),state_[5].id());
		newstate[2] =
		    new Cubie(state_[1].frontback(),state_[1].updown(),
		              state_[1].leftright(),state_[1].id());
		newstate[6] =
		    new Cubie(state_[2].frontback(),state_[2].updown(),
		              state_[2].leftright(),state_[2].id());
		newstate[5] =
		    new Cubie(state_[6].frontback(),state_[6].updown(),
		              state_[6].leftright(),state_[6].id());

		System.arraycopy(newstate,0,state_,0,8);
	}

	private void rotateTopLeft () {
		Cubie[] newstate = new Cubie[8];
		System.arraycopy(state_,0,newstate,0,8);

		newstate[0] =
		    new Cubie(state_[1].updown(),state_[1].leftright(),
		              state_[1].frontback(),state_[1].id());
		newstate[1] =
		    new Cubie(state_[5].updown(),state_[5].leftright(),
		              state_[5].frontback(),state_[5].id());
		newstate[5] =
		    new Cubie(state_[4].updown(),state_[4].leftright(),
		              state_[4].frontback(),state_[4].id());
		newstate[4] =
		    new Cubie(state_[0].updown(),state_[0].leftright(),
		              state_[0].frontback(),state_[0].id());

		System.arraycopy(newstate,0,state_,0,8);
	}

	private void rotateTopRight () {
		Cubie[] newstate = new Cubie[8];
		System.arraycopy(state_,0,newstate,0,8);

		newstate[0] =
		    new Cubie(state_[4].updown(),state_[4].leftright(),
		              state_[4].frontback(),state_[4].id());
		newstate[1] =
		    new Cubie(state_[0].updown(),state_[0].leftright(),
		              state_[0].frontback(),state_[0].id());
		newstate[5] =
		    new Cubie(state_[1].updown(),state_[1].leftright(),
		              state_[1].frontback(),state_[1].id());
		newstate[4] =
		    new Cubie(state_[5].updown(),state_[5].leftright(),
		              state_[5].frontback(),state_[5].id());

		System.arraycopy(newstate,0,state_,0,8);
	}

	private void rotateBottomLeft () {
		Cubie[] newstate = new Cubie[8];
		System.arraycopy(state_,0,newstate,0,8);

		newstate[3] =
		    new Cubie(state_[2].updown(),state_[2].leftright(),
		              state_[2].frontback(),state_[2].id());
		newstate[2] =
		    new Cubie(state_[6].updown(),state_[6].leftright(),
		              state_[6].frontback(),state_[6].id());
		newstate[6] =
		    new Cubie(state_[7].updown(),state_[7].leftright(),
		              state_[7].frontback(),state_[7].id());
		newstate[7] =
		    new Cubie(state_[3].updown(),state_[3].leftright(),
		              state_[3].frontback(),state_[3].id());

		System.arraycopy(newstate,0,state_,0,8);
	}

	private void rotateBottomRight () {
		Cubie[] newstate = new Cubie[8];
		System.arraycopy(state_,0,newstate,0,8);

		newstate[3] =
		    new Cubie(state_[7].updown(),state_[7].leftright(),
		              state_[7].frontback(),state_[7].id());
		newstate[2] =
		    new Cubie(state_[3].updown(),state_[3].leftright(),
		              state_[3].frontback(),state_[3].id());
		newstate[6] =
		    new Cubie(state_[2].updown(),state_[2].leftright(),
		              state_[2].frontback(),state_[2].id());
		newstate[7] =
		    new Cubie(state_[6].updown(),state_[6].leftright(),
		              state_[6].frontback(),state_[6].id());

		System.arraycopy(newstate,0,state_,0,8);
	}

	@Override
	public boolean isSolved () {
		// require canonical orientation, not just that all faces are the same color
		Cube222 solved = new Cube222();
		for ( int ctr = 0 ; ctr < state_.length ; ctr++ ) {
			if ( state_[ctr].id() != ctr
			    || state_[ctr].frontback() != solved.state_[ctr].frontback()
			    || state_[ctr].updown() != solved.state_[ctr].updown()
			    || state_[ctr].leftright() != solved.state_[ctr].leftright() ) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Cube clone () {
		Cube222 newcube = new Cube222();
		System.arraycopy(state_,0,newcube.state_,0,8);
		return newcube;
	}

	/**
	 * Get the cubie in the ith position.
	 */
	@Override
	public Cubie getCubie ( int i ) {
		return state_[i];
	}

	/**
	 * Get the cubie in the ith position in the solved cube.
	 */
	@Override
	public Cubie getSolvedCubie ( int i ) {
		return (new Cube222()).state_[i];
	}

	/**
	 * Number of cubies in the cube.
	 */
	@Override
	public int numCubies () {
		return state_.length;
	}

	@Override
	public int getNumMoves ( int source, int target ) {
		return NUMMOVES[target][source];
	}
}
