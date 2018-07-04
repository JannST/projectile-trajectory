package de.janst.trajectory.playerhandling;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.inventivetalent.particle.ParticleEffect;

import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.config.PlayerConfiguration;

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
	
	public PlayerConfiguration getConfig() {
		return config;
	}
	
	public void sendParticle(Player player, Location location, CalculatorType type) {
		ParticleEffect particleEffect = config.getTrajectoryParticle(type);
		if(particleEffect.hasFeature(ParticleEffect.Feature.COLOR)) {
			particleEffect.sendColor(Bukkit.getOnlinePlayers(), location, config.getOrdinaryParticleColor(type));
		}
		else {
			ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), location, 0, 0, 0, 0, 1);
		}
	}
}
