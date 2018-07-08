package de.janst.trajectory.playerhandling;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.util.Permission;

public class PlayerHandler {

	private final TrajectorySimulator trajectorySimulator;
	private Map<UUID, PlayerObject> playerObjects = new HashMap<UUID, PlayerObject>();

	public PlayerHandler(TrajectorySimulator trajectorySimulator) {
		this.trajectorySimulator = trajectorySimulator;
	}

	private void addPlayerObject(PlayerObject playerObject) {
		if (playerObject != null) {
			playerObjects.put(playerObject.getPlayer().getUniqueId(), playerObject);
		}
	}

	public void addPlayer(Player player) throws IOException {
		if (player == null) {
			return;
		}
		if (player.hasPermission(Permission.USE.getString())) {
			PlayerObject playerObject = new PlayerObject(player);
			addPlayerObject(playerObject);
		}
	}

	public void removePlayer(UUID uuid) {
		playerObjects.remove(uuid);
	}

	public PlayerObject getPlayerObject(UUID uuid) {
		return playerObjects.get(uuid);
	}

	public boolean containsPlayerObject(UUID uuid) {
		return playerObjects.containsKey(uuid);
	}

	public Collection<PlayerObject> getPlayerObjects() {
		return playerObjects.values();
	}
	
	public void loadOnlinePlayers() throws IOException {
		for (Player player : trajectorySimulator.getServer().getOnlinePlayers()) {
			addPlayer(player);
		}
	}

	public void saveAll() {
		try {
			for (PlayerObject playerObject : playerObjects.values()) {
				playerObject.getConfig().save(false);
			}
		} catch (Exception e) {
			trajectorySimulator.getLogger().log(Level.WARNING, "Could not save player settings");
			e.printStackTrace();
		}
	}

}
