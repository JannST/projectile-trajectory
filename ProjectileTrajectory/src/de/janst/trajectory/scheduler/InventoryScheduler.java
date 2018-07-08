package de.janst.trajectory.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.playerhandling.PlayerObject;
import de.janst.trajectory.util.TrajectoryCalculatorHelper;

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
	//TODO this is never called???
	public void removePlayer(UUID uuid) {
		players.remove(uuid);
	}
	
	@Override
	public void run() {
		CalculatorType type;
		PlayerObject playerObject;
		ItemStack itemStack;
		for(UUID uuid : players) {
			playerObject = trajectorySimulator.getPlayerHandler().getPlayerObject(uuid);
			if(playerObject != null) {
				itemStack = playerObject.getPlayer().getInventory().getItemInMainHand();
				type = CalculatorType.getByItemStack(itemStack);
				
				if(type == null) {
					playerObject.setCalculator(null);
				}
				else {
					if(playerObject.getCalculator() != null) {
						if(playerObject.getCalculator().getType() != type) {
							playerObject.setCalculator(TrajectoryCalculatorHelper.getCalculator(type, playerObject));
						}
					}
					else {
						playerObject.setCalculator(TrajectoryCalculatorHelper.getCalculator(type, playerObject));	
					}
				}
			}
			players.remove(uuid);
		}
	}

}
