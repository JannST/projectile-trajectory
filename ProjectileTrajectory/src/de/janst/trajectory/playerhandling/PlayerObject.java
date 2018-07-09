package de.janst.trajectory.playerhandling;

import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.calculator.TrajectoryCalculator;
import de.janst.trajectory.calculator.TrajectoryCalculatorFactory;
import de.janst.trajectory.config.PlayerConfiguration;
import de.janst.trajectory.menu.MainMenu;
import de.janst.trajectory.util.Permission;

public class PlayerObject {

	private final PlayerConfiguration config;
	private Player player;
	private TrajectoryCalculator currentCalculator;
	private long playerHoldingBowTime;

	public PlayerObject(Player player) throws IOException {
		this.player = player;
		this.config = new PlayerConfiguration(player.getUniqueId());
		updateCalculator();
	}

	public Player getPlayer() {
		return this.player;
	}

	public boolean hasPermission(Permission permission) {
		return getPlayer().hasPermission(permission.getString());
	}

	public void showMenu() {
		MainMenu menu = new MainMenu(this);
		menu.show();
	}

	public PlayerConfiguration getConfig() {
		return config;
	}

	public void updateCalculator() {
		if(!hasPermission(Permission.USE))
			return;
		ItemStack itemStack = getPlayer().getInventory().getItemInMainHand();
		CalculatorType type = CalculatorType.getByItemStack(itemStack);
		if (type != null && itemStack.getAmount() > 0) {
			if (currentCalculator == null || currentCalculator.getType() != type) {
				currentCalculator = TrajectoryCalculatorFactory.getCalculator(type, this);
			}
		}
		else 
			currentCalculator = null;
	}

	public TrajectoryCalculator getCalculator() {
		return currentCalculator;
	}

	public boolean isValidForTrajectoryRender() {
		if (currentCalculator == null)
			return false;
		else if (config.isEnabled() && config.isTrajectoryEnabled(currentCalculator.getType())) {
			if (currentCalculator.getType() == CalculatorType.ARROW && playerHoldingBowTime == 0)
				return false;
			return true;
		}
		return false;
	}

	public void drawTrajectory() {
		if (currentCalculator != null) {
			Location location = player.getEyeLocation();
			for (Vector vector : currentCalculator.getTrajectory()) {
				if (location.getBlock().getType().isSolid())
					break;
				sendParticle(location.clone().add(vector.multiply(0.5 + Math.random())));
				location.add(vector);
			}
		}
	}

	private void sendParticle(Location location) {
		if (player.getEyeLocation().distance(location) >= config.getDistanceLevel(currentCalculator.getType())) {
			Particle particleEffect = config.getTrajectoryParticle(currentCalculator.getType());
			this.player.spawnParticle(particleEffect, location, 3, 0, 0, 0, 0);
		}
	}

	public void setPlayerHoldingBowTime(long playerHoldingBowTime) {
		this.playerHoldingBowTime = playerHoldingBowTime;
	}

	public long getPlayerHoldingBowTime() {
		return playerHoldingBowTime;
	}
}
