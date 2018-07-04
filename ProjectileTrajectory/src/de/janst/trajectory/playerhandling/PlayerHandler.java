package de.janst.trajectory.playerhandling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.calculator.TrajectoryCalculator;
import de.janst.trajectory.util.ItemChecker;
import de.janst.trajectory.util.Permission;

public class PlayerHandler {

	private final TrajectorySimulator trajectorySimulator;
	private Map<UUID, PlayerObject> playerObjects = new HashMap<UUID, PlayerObject>();

	public PlayerHandler(TrajectorySimulator trajectorySimulator) {
		this.trajectorySimulator = trajectorySimulator;
	}
	
	private void addPlayerObject(PlayerObject playerObject) {
		if(playerObject != null) {
			playerObjects.put(playerObject.getUUID(), playerObject);
		}
	}
	
	public void addPlayer(Player player) throws IOException {
		if(player != null) {
			if(player.hasPermission(Permission.USE.getString())) {
				addPlayerObject(new PlayerObject(player.getUniqueId()));
				TrajectoryCalculator calculator = ItemChecker.ITEM_CHECKER.checkItem(player.getInventory().getItemInMainHand(), player.getUniqueId());
				
				if(calculator != null) {
					trajectorySimulator.getTrajectoryScheduler().addCalculator(player.getUniqueId(), calculator);
				}
			}
		}
	}
	
	public void removePlayer(UUID uuid) {
		trajectorySimulator.getTrajectoryScheduler().removeCalculator(uuid);
		trajectorySimulator.getBowListener().removePlayer(uuid);
		playerObjects.remove(uuid);
	}
	
	public PlayerObject getPlayerObject(UUID uuid) {
		return playerObjects.get(uuid);
	}

	public boolean containsPlayerObject(UUID uuid) {
		return playerObjects.containsKey(uuid);
	}
	
	public TrajectorySimulator getTrajectorySimulator() {
		return trajectorySimulator;
	}
	
	public void loadOnlinePlayers() throws IOException {
		for(Player player : trajectorySimulator.getServer().getOnlinePlayers()) {
			addPlayer(player);
		}
	}
	
	public void saveAll() throws IOException {
		for(PlayerObject playerObject : playerObjects.values()) {
			if(playerObject.getConfig().hasChanges()) {
				playerObject.getConfig().save();
			}
		}
	}

}
