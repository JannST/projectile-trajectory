package de.janst.trajectory.calculator;

import java.util.List;
import org.bukkit.util.Vector;

import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.playerhandling.PlayerObject;

public abstract class TrajectoryCalculator {

	protected int maximalLength;
	protected final PlayerObject playerObject;
	protected final CalculatorType type;

	public TrajectoryCalculator(PlayerObject playerObject, CalculatorType type) {
		this.maximalLength = TrajectorySimulator.getInstance().getPluginConfig().getMaximalLength();
		this.playerObject = playerObject;
		this.type = type;
	}
	
	public CalculatorType getType() {
		return type;
	}
	
	public abstract List<Vector> getTrajectory();
}
