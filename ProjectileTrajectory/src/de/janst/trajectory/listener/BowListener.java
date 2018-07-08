package de.janst.trajectory.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.playerhandling.PlayerObject;

public class BowListener implements Listener {

	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_AIR) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && item.getType().equals(Material.BOW)) {
				if(!item.containsEnchantment(Enchantment.ARROW_INFINITE) && player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW))
					return;
				PlayerObject playerObject = TrajectorySimulator.getInstance().getPlayerHandler().getPlayerObject(event.getPlayer().getUniqueId());
				if(playerObject != null) {
					playerObject.setPlayerHoldingBowTime(System.currentTimeMillis());
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onBowRelease(ProjectileLaunchEvent event) {
		if(event.getEntityType() == EntityType.ARROW && event.getEntity().getShooter() instanceof Player) {
			Player player = (Player)event.getEntity().getShooter();
			PlayerObject playerObject = TrajectorySimulator.getInstance().getPlayerHandler().getPlayerObject(player.getUniqueId());
			if(playerObject != null) {
				playerObject.setPlayerHoldingBowTime(0);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onItemHeld(PlayerItemHeldEvent event) {
		PlayerObject playerObject = TrajectorySimulator.getInstance().getPlayerHandler().getPlayerObject(event.getPlayer().getUniqueId());
		if(playerObject != null) {
			playerObject.setPlayerHoldingBowTime(0);
		}
	}
}
