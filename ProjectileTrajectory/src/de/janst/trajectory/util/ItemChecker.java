package de.janst.trajectory.util;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import de.janst.trajectory.calculator.ArrowCalculator;
import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.calculator.NormalCalculator;
import de.janst.trajectory.calculator.TrajectoryCalculator;

public class ItemChecker {
	
	public static final ItemChecker ITEM_CHECKER = new ItemChecker();
	private static final List<Integer> potionMeta = Arrays.asList(16385, 16449, 16417, 16386, 16450, 16418, 16387, 16451, 16389, 16421, 16390, 16454, 16393, 16457, 16425, 16395, 16459, 16427,
			16397, 16261, 16398, 16462, 16388, 16452, 16420, 16392, 16456, 16394, 16458, 16396, 16428, 16481, 16482, 16489, 16484);
	
	public TrajectoryCalculator checkItem(int newSlot, PlayerInventory inventory) {
		return checkItem(inventory.getItem(newSlot), inventory.getHolder().getUniqueId());
	}
	
	public TrajectoryCalculator checkItem(ItemStack itemStack, UUID uuid) {
		if(itemStack != null) {
			Material material =  itemStack.getType();
			
			if(material == Material.BOW) {
				return new ArrowCalculator(uuid, CalculatorType.ARROW);
			}
			else if(material == Material.SNOW_BALL) {
				return new NormalCalculator(uuid, CalculatorType.SNOWBALL);
			}
			else if(material == Material.ENDER_PEARL) {
				return new NormalCalculator(uuid, CalculatorType.ENDERPEARL);
			}
			else if(material == Material.EGG) {
				return new NormalCalculator(uuid, CalculatorType.EGG);
			}
			else if(material == Material.POTION) {
				if(potionMeta.contains((int)itemStack.getDurability())) {
					return new NormalCalculator(uuid, CalculatorType.POTION, 0.5F, -20.0F, 0.05F);
				}
			}
		}
		return null;
	}
}
