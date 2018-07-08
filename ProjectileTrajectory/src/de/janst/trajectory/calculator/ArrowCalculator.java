package de.janst.trajectory.calculator;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import de.janst.trajectory.playerhandling.PlayerObject;

public class ArrowCalculator extends TrajectoryCalculator {

	private final float mod = 0.99F;

	public ArrowCalculator(PlayerObject playerObject) {
		super(playerObject, CalculatorType.ARROW);
	}

	@Override
	public List<Vector> getTrajectory() {
		Location location = playerObject.getPlayer().getEyeLocation();
		List<Vector> vlist = new ArrayList<Vector>();

		float powerMod = getBowPowerModifier(playerObject.getPlayerHoldingBowTime()) * 1.5F;

		float yaw = location.getYaw();
		float pitch = location.getPitch();

		location.setX(location.getX() - Math.cos(yaw / 180.0F * 3.141593F) * 0.16F);
		location.setY(location.getY() - 0.1000000014901161D);
		location.setZ(location.getZ() - Math.sin(yaw / 180.0F * 3.141593F) * 0.16F);

		double motX = (-Math.sin(yaw / 180.0F * 3.141593F) * Math.cos(pitch / 180.0F * 3.141593F));
		double motZ = (Math.cos(yaw / 180.0F * 3.141593F) * Math.cos(pitch / 180.0F * 3.141593F));
		double motY = (-Math.sin(pitch / 180.0F * 3.141593F));

		double f2 = Math.sqrt(motX * motX + motY * motY + motZ * motZ);
		motX /= f2;
		motY /= f2;
		motZ /= f2;
		motX *= powerMod;
		motY *= powerMod;
		motZ *= powerMod;

		double y = location.getY() + motY;
		double totalDistance = 0;

		while (y > 0 && totalDistance < MAXIMAL_LENGTH) {
			motY *= mod;
			motX *= mod;
			motZ *= mod;
			motY -= 0.05F;
			vlist.add(new Vector(motX, motY, motZ));
			y += motY;
			totalDistance += Math.sqrt(Math.pow(motX, 2) + Math.pow(motY, 2) + Math.pow(motZ, 2));

		}
		return vlist;
	}
	
	private float getBowPowerModifier(long playerHoldingBowTime) {
		long l = (72000 - (System.currentTimeMillis() - playerHoldingBowTime) / 50);
		long i = l < 0 ? 0 : l;
		int j = (int) (72000 - i);
		float f = (float) j / 20.0F;

		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F) {
			f = 1.0F;
		}

		return f * 2.0F;
	}
}
