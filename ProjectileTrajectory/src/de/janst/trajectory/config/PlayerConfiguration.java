package de.janst.trajectory.config;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Particle;

import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.util.RGBColor;

public class PlayerConfiguration extends Configuration {

	private final Map<CalculatorType, Particle> particles = new HashMap<CalculatorType, Particle>();
	private final Map<CalculatorType, Color> particleColors = new HashMap<CalculatorType, Color>();
	
	public PlayerConfiguration(UUID uuid) throws IOException {
		super("/players/" + uuid.toString() + ".yml" , false);
		
		config.addDefaults(PlayerConfigurationDefaults.DEFAULTS.getDefaults());
		save(false);
	}
	
	public boolean isEnabled() {
		return config.getBoolean("enabled");
	}
	
	public void setEnabled(boolean enabled) {
		config.set("enabled", enabled);
	}
	
	public boolean isTrajectoryEnabled(CalculatorType type) {
		return config.getBoolean(type.getName()+".enabled");
	}
	
	public void setTrajectoryEnabled(CalculatorType type, boolean enabled) {
		config.set(type.getName()+".enabled", enabled);
	}
	
	public void setTrajectoryParticle(Particle particle, CalculatorType type) {
		particles.put(type, particle);
		config.set(type.getName()+".particle", particle.toString());
	}
	
	public Particle getTrajectoryParticle(CalculatorType type) {
		if(particles.containsKey(type)) {
			return particles.get(type);
		}
		else {
			System.out.println(config.getString(type.getName()+".particle"));
			Particle effect = Particle.valueOf(config.getString(type.getName()+".particle"));
			particles.put(type, effect);
			return effect;
		}
	}
	
	public void setParticleColor(RGBColor color, CalculatorType type) {
		Color awtColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
		particleColors.put(type, awtColor);
		config.set(type.getName()+".color", color.getName());
	}
	
	public Color getOrdinaryParticleColor(CalculatorType type) {
		if(particleColors.containsKey(type)) {
			return particleColors.get(type);
		}
		else {
			RGBColor color = RGBColor.fromName(config.getString(type.getName()+".color"));
			Color awtColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
			particleColors.put(type, awtColor);
			return awtColor;
		}
	}
	
	public RGBColor getParticleColor(CalculatorType type) {
		return RGBColor.fromName(config.getString(type.getName()+".color"));
	}
	
	public void updateDistanceLevel(CalculatorType type){
		int level = getDistanceLevel(type);
		if(level+1 > 10) 
			level = 0;
		else 
			level++;
		config.set(type.getName()+".distance-level", level);
	}
	
	public int getDistanceLevel(CalculatorType type) {
		return config.getInt(type.getName()+".distance-level");
	}
	
	public void setChanges(boolean b){
		hasChanges = b;
	}
}
