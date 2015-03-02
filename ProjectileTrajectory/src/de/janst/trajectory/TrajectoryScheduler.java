package de.janst.trajectory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.janst.trajectory.calculator.TrajectoryCalculator;
import de.janst.trajectory.playerhandling.PlayerObject;
import de.janst.trajectory.util.Permission;

public class TrajectoryScheduler implements Runnable {

	private int pid;
	private final TrajectorySimulator trajectorySimulator;
	private final Map<UUID, TrajectoryCalculator> calculators = new HashMap<UUID, TrajectoryCalculator>();

	public TrajectoryScheduler(TrajectorySimulator trajectorySimulator, int speed) {
		this.trajectorySimulator = trajectorySimulator;
		start(speed);
	}
	
	public void start(long speed) {
		this.pid = Bukkit.getScheduler().scheduleSyncRepeatingTask(trajectorySimulator, this, 0L, speed);
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(pid);
	}

	public void addCalculator(UUID uuid, TrajectoryCalculator calculator) {
		calculators.put(uuid, calculator);
	}
	
	public void removeCalculator(UUID uuid) {
		calculators.remove(uuid);
	}
	
	public boolean hasCalculator(UUID uuid) {
		return calculators.containsKey(uuid);
	}
	
	public TrajectoryCalculator getCalculator(UUID uuid) {
		return calculators.get(uuid);
	}
	
	@Override
	public void run() {
		Player player;
		Location location;
		Location initialLocation;
		PlayerObject playerObject;
		int minimalDisance;
		
		for(TrajectoryCalculator calculator : calculators.values()) {
			player = trajectorySimulator.getServer().getPlayer(calculator.getPlayerUuid());
			playerObject = trajectorySimulator.getPlayerHandler().getPlayerObject(calculator.getPlayerUuid());
			if(player != null) {
				if(!player.hasPermission(Permission.USE.getString())) {
					calculators.remove(calculator.getPlayerUuid());
					continue;
				}
				if(player != null && player.isValid() && playerObject != null && playerObject.getConfig().isEnabled() && playerObject.getConfig().isTrajectoryEnabled(calculator.getType())) {
					location = player.getEyeLocation();
					initialLocation = player.getEyeLocation();
					calculator.setLocation(location);
					minimalDisance = playerObject.getConfig().getDistanceLevel(calculator.getType());
					
					if(calculator.calculate()) {
						for (Vector vector : calculator.getTrajectory()) {
							location.add(vector);
							if(location.getBlock().getType().isSolid())
								break;
							if(initialLocation.distance(location) >= minimalDisance)
							playerObject.sendParticle(player, location, calculator.getType());
						}
					}
				}
			}
		}
	}
}
