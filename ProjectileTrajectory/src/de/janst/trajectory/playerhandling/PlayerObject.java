package de.janst.trajectory.playerhandling;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.config.PlayerConfiguration;
import de.janst.trajectory.menu.MainMenu;
import de.janst.trajectory.util.Permission;

public class PlayerObject {

	private final UUID uuid;
	private final PlayerConfiguration config;
	
	public PlayerObject(UUID uuid) throws IOException {
		this.uuid = uuid;
		this.config = new PlayerConfiguration(uuid);
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public boolean isOnline() {
		return Bukkit.getServer().getPlayer(uuid) != null;
	}
	
	public Player getPlayer() {
		return Bukkit.getServer().getPlayer(uuid);
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
	
	public void sendParticle(Player player, Location location, CalculatorType type) {
		Particle particleEffect = config.getTrajectoryParticle(type);
		player.spawnParticle(particleEffect, location, 3,0,0,0,0);
	}
}
