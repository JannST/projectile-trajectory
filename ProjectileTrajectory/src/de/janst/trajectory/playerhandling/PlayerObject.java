package de.janst.trajectory.playerhandling;

import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.calculator.TrajectoryCalculator;
import de.janst.trajectory.config.PlayerConfiguration;
import de.janst.trajectory.menu.MainMenu;
import de.janst.trajectory.util.Permission;
import de.janst.trajectory.util.TrajectoryCalculatorHelper;

public class PlayerObject {

	private final PlayerConfiguration config;
	private Player player;
	private TrajectoryCalculator currentCalculator;
	private long playerHoldingBowTime;

	public PlayerObject(Player player) throws IOException {
		this.player = player;
		this.config = new PlayerConfiguration(player.getUniqueId());

		TrajectoryCalculator calculator = TrajectoryCalculatorHelper.getCalculator(player.getInventory().getItemInMainHand(), this);
		if (calculator != null)
			this.currentCalculator = calculator;
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

	public void setCalculator(TrajectoryCalculator calculator) {
		this.currentCalculator = calculator;
	}

	public TrajectoryCalculator getCalculator() {
		return currentCalculator;
	}

	public boolean isValidForTrajectoryRender() {
		if (currentCalculator == null)
			return false;
		else if (config.isEnabled() && config.isTrajectoryEnabled(currentCalculator.getType())) {
			if(currentCalculator.getType() == CalculatorType.ARROW && playerHoldingBowTime == 0)
				return false;
			return true;
		}
		return false;
	}

	public void drawTrajectory() {
		if(currentCalculator != null) {
			Location location = player.getEyeLocation();
			for (Vector vector : currentCalculator.getTrajectory()) {
				if(location.getBlock().getType().isSolid())
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
