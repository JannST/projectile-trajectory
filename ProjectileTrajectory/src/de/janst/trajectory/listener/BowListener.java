package de.janst.trajectory.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

public class BowListener implements Listener {

	private final Map<UUID, Long> bowHolders = new HashMap<UUID, Long>();

	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_AIR && event.getPlayer().getItemInHand().getType().equals(Material.BOW)) {
			Player player = event.getPlayer();
			ItemStack bow = player.getItemInHand();
			if(!bow.containsEnchantment(Enchantment.ARROW_INFINITE) && player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW))
				return;
			bowHolders.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onBowRelease(ProjectileLaunchEvent event) {
		if(event.getEntityType() == EntityType.ARROW && event.getEntity().getShooter() instanceof Player) {
			bowHolders.remove(((Player)event.getEntity().getShooter()).getUniqueId());
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onItemHeld(PlayerItemHeldEvent event) {
		bowHolders.remove(event.getPlayer().getUniqueId());
	}
    
    private long getHoldingTimeInTicks(UUID uuid) {
    		long l = (72000 - (System.currentTimeMillis() - bowHolders.get(uuid))/50);
    		return l < 0 ? 0 : l;
    }
    
    public float getBowPowerModifier(UUID uuid) {
	    	long i = getHoldingTimeInTicks(uuid);
	    	int j = (int) (72000 - i);
	        float f = (float) j / 20.0F;
	
	        f = (f * f + f * 2.0F) / 3.0F;
	
	        if (f > 1.0F) {
	            f = 1.0F;
	        }
	        
	        return f * 2.0F;
    }
    
    public boolean isHoldingBow(UUID uuid) {
    	return bowHolders.containsKey(uuid);
    }
    
    public void removePlayer(UUID uuid) {
    	bowHolders.remove(uuid);
    }
}
