package de.janst.trajectory.calculator;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import de.janst.trajectory.playerhandling.PlayerObject;

public class NormalCalculator extends TrajectoryCalculator {

	private final float mod = 0.99F;
	private final float f = 0.4F;
	
	private float modE = 1.5F;
	private float modF = 0.0F;
	private float modFall = 0.03F;
	
	public NormalCalculator(PlayerObject playerObject, CalculatorType type, float modE, float modF, float modFall) {
		super(playerObject, type);
		this.modE = modE;
		this.modF = modF;
		this.modFall = modFall;
	}
	
	public NormalCalculator(PlayerObject playerObject, CalculatorType type) {
		super(playerObject, type);
	}
	
	@Override
	public List<Vector> getTrajectory() {
		Location location = playerObject.getPlayer().getEyeLocation();
	    List<Vector> vlist = new ArrayList<Vector>();
		
		float yaw = location.getYaw();
		float pitch = location.getPitch();
		
		location.setX(location.getX() - Math.cos(yaw / 180.0F * 3.141593F) * 0.16F);
	    location.setY(location.getY() - 0.1000000014901161D);
	    location.setZ(location.getZ() - Math.sin(yaw / 180.0F * 3.141593F) * 0.16F);
	
		double motX = (-Math.sin(yaw / 180.0F * 3.141593F) * Math.cos(pitch / 180.0F * 3.141593F) * f);
	    double motZ = (Math.cos(yaw / 180.0F * 3.141593F) * Math.cos(pitch / 180.0F * 3.141593F) * f);
	    double motY = (-Math.sin((pitch + modF) / 180.0F * 3.141593F) * f);

		double f2 = Math.sqrt(motX * motX + motY * motY + motZ * motZ);
		motX /= f2;
		motY /= f2;
		motZ /= f2;
		motX *= modE;
		motY *= modE;
		motZ *= modE;
	    
	    double y = location.getY()+motY;
	    double totalDistance = 0;
	    
	    while(y > 0 && totalDistance < maximalLength) {
			motY *= mod;
			motX *= mod;
			motZ *= mod;
			motY -= modFall;
			vlist.add(new Vector(motX, motY, motZ));
			y+=motY;
			totalDistance += Math.sqrt(Math.pow(motX, 2)+Math.pow(motY, 2)+Math.pow(motZ, 2));
	    }
	    return vlist;
	}
}
