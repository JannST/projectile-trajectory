package de.janst.trajectory.listener;

import java.io.IOException;
import java.util.logging.Level;

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
import org.bukkit.inventory.ItemStack;

import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.calculator.TrajectoryCalculator;
import de.janst.trajectory.playerhandling.PlayerObject;
import de.janst.trajectory.util.Permission;
import de.janst.trajectory.util.TrajectoryCalculatorHelper;

public class PlayerListener implements Listener {

	private final TrajectorySimulator trajectorySimulator;

	public PlayerListener(TrajectorySimulator trajectorySimulator) {
		this.trajectorySimulator = trajectorySimulator;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemChange(PlayerItemHeldEvent event) {
		PlayerObject playerObject = TrajectorySimulator.getInstance().getPlayerHandler().getPlayerObject(event.getPlayer().getUniqueId());
		if(!event.isCancelled() && playerObject != null && playerObject.hasPermission(Permission.USE)) {
			TrajectoryCalculator calculator = TrajectoryCalculatorHelper.getCalculator(event.getPlayer().getInventory().getItem(event.getNewSlot()), playerObject);
			
			if(calculator != null) {
				playerObject.setCalculator(calculator);
			}
			else if(playerObject.getCalculator() != null) {
				playerObject.setCalculator(null);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getWhoClicked().hasPermission(Permission.USE.getString())) {
			trajectorySimulator.getInventoryScheduler().addPlayer(event.getWhoClicked().getUniqueId());
		}
	}

	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		if(event.getWhoClicked().hasPermission(Permission.USE.getString())) {
			trajectorySimulator.getInventoryScheduler().addPlayer(event.getWhoClicked().getUniqueId());
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		try {
			trajectorySimulator.getPlayerHandler().addPlayer(event.getPlayer());
		} catch (IOException e) {
			trajectorySimulator.getLogger().log(Level.SEVERE, "Could not add player " + event.getPlayer().getName());
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		trajectorySimulator.getPlayerHandler().removePlayer(event.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if(event.getPlayer().hasPermission(Permission.USE.getString())) {
			trajectorySimulator.getInventoryScheduler().addPlayer(event.getPlayer().getUniqueId());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onThrow(ProjectileLaunchEvent event) {
		if(event.getEntity().getShooter() instanceof Player && !event.isCancelled()) {
			Player player = (Player) event.getEntity().getShooter();
			PlayerObject playerObject = TrajectorySimulator.getInstance().getPlayerHandler().getPlayerObject(player.getUniqueId());
			
			if(playerObject != null && playerObject.hasPermission(Permission.USE)) {
				ItemStack inHand = player.getInventory().getItemInMainHand();
				
				if(inHand == null || inHand.getAmount() > 0)
					return;
				
				if(CalculatorType.getByItemStack(inHand) != null) {
					playerObject.setCalculator(null);
				}
			}
		}
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		trajectorySimulator.getPlayerHandler().removePlayer(event.getPlayer().getUniqueId());
	}
}
