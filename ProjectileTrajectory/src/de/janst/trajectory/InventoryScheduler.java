package de.janst.trajectory;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.util.ItemChecker;

public class InventoryScheduler implements Runnable {

	private final List<UUID> players = new LinkedList<UUID>();
	private TrajectorySimulator trajectorySimulator;
	private int pid;
	
	public InventoryScheduler(TrajectorySimulator trajectorySimulator) {
		this.trajectorySimulator = trajectorySimulator;
		start();
	}
	
	public void start() {
		this.pid = Bukkit.getScheduler().scheduleSyncRepeatingTask(trajectorySimulator, this, 0L, 5L);
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(pid);
	}

	public void addPlayer(UUID uuid) {
		if(!players.contains(uuid)) {
			players.add(uuid);
		}
	}
	
	public void removePlayer(UUID uuid) {
		players.remove(uuid);
	}
	
	@Override
	public void run() {
		CalculatorType type;
		Player player;
		ItemStack itemStack;
		for(UUID uuid : players) {
			player = trajectorySimulator.getServer().getPlayer(uuid);
			if(player != null) {
				itemStack = player.getInventory().getItemInHand();
				type = itemStack != null ? CalculatorType.getByMaterial(itemStack.getType()) : null;
				
				if(type == null) {
					trajectorySimulator.getTrajectoryScheduler().removeCalculator(uuid);
				}
				else {
					if(trajectorySimulator.getTrajectoryScheduler().hasCalculator(uuid)) {
						if(trajectorySimulator.getTrajectoryScheduler().getCalculator(uuid).getType() != type) {
							trajectorySimulator.getTrajectoryScheduler().addCalculator(uuid, ItemChecker.ITEM_CHECKER.checkItem(itemStack, uuid));
						}
					}
					else {
						trajectorySimulator.getTrajectoryScheduler().addCalculator(uuid, ItemChecker.ITEM_CHECKER.checkItem(itemStack, uuid));
					}
				}
			}
			players.remove(uuid);
		}
	}

}
