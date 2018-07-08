package de.janst.trajectory.util;

import org.bukkit.inventory.ItemStack;
import de.janst.trajectory.calculator.ArrowCalculator;
import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.calculator.NormalCalculator;
import de.janst.trajectory.calculator.TrajectoryCalculator;
import de.janst.trajectory.playerhandling.PlayerObject;

public class TrajectoryCalculatorHelper {

	public static TrajectoryCalculator getCalculator(ItemStack itemStack, PlayerObject playerObject) {
		CalculatorType calculatorType = CalculatorType.getByItemStack(itemStack);
		return getCalculator(calculatorType, playerObject);
	}
	
	public static TrajectoryCalculator getCalculator(CalculatorType calculatorType, PlayerObject playerObject) {
		TrajectoryCalculator calculator = null;
		if (calculatorType != null) {
			switch (calculatorType) {
			case ARROW:
				calculator = new ArrowCalculator(playerObject);
				break;
			case POTION:
				calculator = new NormalCalculator(playerObject, calculatorType, 0.5F, -20.0F, 0.05F);
				break;
			default:
				calculator = new NormalCalculator(playerObject, calculatorType);
				break;
			}
		}
		return calculator;
	}
}
