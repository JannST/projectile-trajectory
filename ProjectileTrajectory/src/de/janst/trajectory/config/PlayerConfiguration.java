package de.janst.trajectory.config;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Particle;

import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.util.RGBColor;

public class PlayerConfiguration extends Config {

	private final Map<CalculatorType, Particle> particles = new HashMap<CalculatorType, Particle>();
	private final Map<CalculatorType, Color> particleColors = new HashMap<CalculatorType, Color>();
	
	public PlayerConfiguration(UUID uuid) throws IOException {
		super("/players/" + uuid.toString() + ".yml");
		
		config.addDefaults(TrajectorySimulator.getInstance().getPlayerConfigurationDefaults().getDefaults());
		save(false);
	}
	
	public boolean isEnabled() {
		return config.getBoolean("enabled");
	}
	
	public void setEnabled(boolean enabled) {
		set("enabled", enabled);
	}
	
	public boolean isTrajectoryEnabled(CalculatorType type) {
		return config.getBoolean(type.getName()+".enabled");
	}
	
	public void setTrajectoryEnabled(CalculatorType type, boolean enabled) {
		set(type.getName()+".enabled", enabled);
	}
	
	public void setTrajectoryParticle(Particle particle, CalculatorType type) {
		particles.put(type, particle);
		set(type.getName()+".particle", particle.toString());
	}
	
	public Particle getTrajectoryParticle(CalculatorType type) {
		if(particles.containsKey(type)) {
			return particles.get(type);
		}
		else {
			Particle effect = Particle.valueOf(config.getString(type.getName()+".particle"));
			particles.put(type, effect);
			return effect;
		}
	}
	
	public void setParticleColor(RGBColor color, CalculatorType type) {
		Color awtColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
		particleColors.put(type, awtColor);
		set(type.getName()+".color", color.getName());
	}
	
	public void updateDistanceLevel(CalculatorType type){
		int level = getDistanceLevel(type);
		if(level+1 > 12) 
			level = 1;
		else 
			level++;
		set(type.getName()+".distance-level", level);
	}
	
	public int getDistanceLevel(CalculatorType type) {
		return config.getInt(type.getName()+".distance-level");
	}
}
