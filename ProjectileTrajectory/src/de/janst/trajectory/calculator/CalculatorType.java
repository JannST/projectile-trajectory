package de.janst.trajectory.calculator;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum CalculatorType {
	ARROW("Arrow", Material.BOW),
	SNOWBALL("Snowball", Material.SNOW_BALL),
	ENDERPEARL("Enderpearl", Material.ENDER_PEARL),
	EGG("Egg", Material.EGG),
	POTION("Potion", Material.POTION);
	
	private static final List<Integer> POTIONMETA = Arrays.asList(16385, 16449, 16417, 16386, 16450, 16418, 16387, 16451, 16389, 16421, 16390, 16454, 16393, 16457, 16425, 16395, 16459, 16427,
			16397, 16261, 16398, 16462, 16388, 16452, 16420, 16392, 16456, 16394, 16458, 16396, 16428, 16481, 16482, 16489, 16484);
	
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
				if(type == CalculatorType.POTION) {
					if(POTIONMETA.contains((int)item.getDurability()))
						return type;
				}else
					return type;
			}
		}
		return null;
	}
}
