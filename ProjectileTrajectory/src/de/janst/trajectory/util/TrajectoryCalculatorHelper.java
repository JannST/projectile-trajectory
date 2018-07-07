package de.janst.trajectory.util;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import de.janst.trajectory.calculator.ArrowCalculator;
import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.calculator.NormalCalculator;
import de.janst.trajectory.calculator.TrajectoryCalculator;

public class TrajectoryCalculatorHelper {

	public static TrajectoryCalculator getFromItem(int newSlot, PlayerInventory inventory) {
		return getCalculator(inventory.getItem(newSlot), inventory.getHolder().getUniqueId());
	}

	public static TrajectoryCalculator getCalculator(ItemStack itemStack, UUID uuid) {
		CalculatorType calculatorType = CalculatorType.getByItemStack(itemStack);
		TrajectoryCalculator calculator = null;

		if (calculatorType != null) {
			switch (calculatorType) {
			case ARROW:
				calculator = new ArrowCalculator(uuid, calculatorType);
				break;
			case POTION:
				calculator = new NormalCalculator(uuid, calculatorType, 0.5F, -20.0F, 0.05F);
				break;
			default:
				calculator = new NormalCalculator(uuid, calculatorType);
				break;
			}
		}
		return calculator;
	}
}
