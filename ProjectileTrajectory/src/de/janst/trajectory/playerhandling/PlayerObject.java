package de.janst.trajectory.playerhandling;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.darkblade12.particleeffect.ParticleEffect;
import com.darkblade12.particleeffect.ParticleEffect.ParticleProperty;

import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.config.PlayerConfiguration;

public class PlayerObject {

	private final UUID uuid;
	private final PlayerConfiguration config;
	
	public PlayerObject(UUID uuid) {
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
		if(particleEffect.hasProperty(ParticleProperty.COLORABLE)) {
			particleEffect.display(config.getOrdinaryParticleColor(type), location, player);
			//particleEffect.display(config.getOrdinaryParticleColor(type), location, 50); //show to all
		}
		else {
			particleEffect.display(0, 0, 0, 0, 1, location, player);
			//particleEffect.display(0, 0, 0, 0, 1, location, 50);
		}
	}
}
