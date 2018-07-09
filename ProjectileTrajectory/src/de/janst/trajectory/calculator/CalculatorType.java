package de.janst.trajectory.calculator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum CalculatorType {
	ARROW("Arrow", Material.BOW),
	SNOWBALL("Snowball", Material.SNOW_BALL),
	ENDERPEARL("Enderpearl", Material.ENDER_PEARL),
	EGG("Egg", Material.EGG),
	POTION("Potion", Material.SPLASH_POTION);
	
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
	
	public static CalculatorType getByItemStack(ItemStack item) {
		if(item == null)
			return null;
		for(CalculatorType type : values()) {
			if(type.getMaterial() == item.getType()) {
					return type;
			}
		}
		return null;
	}
}
