package de.janst.trajectory.calculator;

import org.bukkit.Material;

public enum CalculatorType {
	ARROW("Arrow", Material.BOW),
	SNOWBALL("Snowball", Material.SNOW_BALL),
	ENDERPEARL("Enderpearl", Material.ENDER_PEARL),
	EGG("Egg", Material.EGG),
	POTION("Potion", Material.POTION);
	
	private String name;
	private Material material;

	CalculatorType(String name, Material material) {
		this.name = name;
		this.material = material;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public static CalculatorType getByName(String name) {
		for(CalculatorType type : values()) {
			if(type.getName().equals(name)) {
				return type;
			}
		}
		return null;
	}
	
	public static CalculatorType getByMaterial(Material material) {
		for(CalculatorType type : values()) {
			if(type.getMaterial() == material) {
				return type;
			}
		}
		return null;
	}
}
