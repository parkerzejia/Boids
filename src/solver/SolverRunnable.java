package solver;

import java.util.List;

import core.Move;
import core.RubiksCube;

public class SolverRunnable implements Runnable {

	private List<Move> solution_;
	private Solver solver_;
	private RubiksCube cube_;

	public SolverRunnable ( Solver solver, RubiksCube cube ) {
		solver_ = solver;
		cube_ = cube;
		solution_ = null;
	}

	@Override
	public void run () {
		solution_ = solver_.solve(cube_);
	}

	public List<Move> getSolution () {
		return solution_;
	}

	public void shutdown () {
		solver_.stop();
	}
}
