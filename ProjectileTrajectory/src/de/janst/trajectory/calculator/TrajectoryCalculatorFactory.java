package de.janst.trajectory.calculator;

import de.janst.trajectory.playerhandling.PlayerObject;

public class TrajectoryCalculatorFactory {
	
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