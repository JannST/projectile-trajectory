package de.janst.trajectory.listener;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.playerhandling.PlayerObject;

public class PlayerListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		try {
			TrajectorySimulator.getInstance().getPlayerHandler().addPlayer(event.getPlayer());
		} catch (IOException e) {
			TrajectorySimulator.getInstance().getLogger().log(Level.SEVERE,"Could not add player " + event.getPlayer().getName());
			e.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onItemChange(PlayerItemHeldEvent event) {
		scheduleUpdate(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		scheduleUpdate(event.getWhoClicked().getUniqueId());

	}

	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		scheduleUpdate(event.getWhoClicked().getUniqueId());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		TrajectorySimulator.getInstance().getPlayerHandler().removePlayer(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		scheduleUpdate(event.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onThrow(ProjectileLaunchEvent event) {
		if (event.getEntity().getShooter() instanceof Player) {
			Player player = (Player) event.getEntity().getShooter();
			scheduleUpdate(player.getUniqueId());
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		TrajectorySimulator.getInstance().getPlayerHandler().removePlayer(event.getPlayer().getUniqueId());
	}

	private void scheduleUpdate(UUID uuid) {
		PlayerObject playerObject = TrajectorySimulator.getInstance().getPlayerHandler().getPlayerObject(uuid);
		if (playerObject != null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(TrajectorySimulator.getInstance(), new Runnable() {

				@Override
				public void run() {
					playerObject.updateCalculator();

				}
			});
		}
	}
}
