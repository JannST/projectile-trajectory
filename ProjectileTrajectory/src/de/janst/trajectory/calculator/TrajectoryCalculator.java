package de.janst.trajectory.calculator;

import java.util.List;
import org.bukkit.util.Vector;

import de.janst.trajectory.playerhandling.PlayerObject;

public abstract class TrajectoryCalculator {

	public static int MAXIMAL_LENGTH;
	protected final PlayerObject playerObject;
	protected final CalculatorType type;

	public TrajectoryCalculator(PlayerObject playerObject, CalculatorType type) {
		this.playerObject = playerObject;
		this.type = type;
	}
	
	public CalculatorType getType() {
		return type;
	}
	
	public abstract List<Vector> getTrajectory();
}
