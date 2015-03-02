package de.janst.trajectory.calculator;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public abstract class TrajectoryCalculator {

	public static int MAXIMAL_LENGTH;
	protected Location location;
	protected final UUID uuid;
	protected final CalculatorType type;

	public TrajectoryCalculator(UUID uuid, CalculatorType type) {
		this.uuid = uuid;
		this.type = type;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public UUID getPlayerUuid() {
		return uuid;
	}
	
	public CalculatorType getType() {
		return type;
	}
	
	public abstract boolean calculate();
	public abstract List<Vector> getTrajectory();
}
