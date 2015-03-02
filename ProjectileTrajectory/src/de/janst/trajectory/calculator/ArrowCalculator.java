package de.janst.trajectory.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.util.Vector;

import de.janst.trajectory.TrajectorySimulator;

public class ArrowCalculator extends TrajectoryCalculator {

	private final float mod = 0.99F;
	private List<Vector> vectorList;
	
	public ArrowCalculator(UUID uuid, CalculatorType type) {
		super(uuid, type);
	}

	@Override
	public boolean calculate() {
		if(TrajectorySimulator.getPlugin().getBowListener().isHoldingBow(uuid)) {
		    List<Vector> vlist = new ArrayList<Vector>();
			
		    float powerMod = TrajectorySimulator.getPlugin().getBowListener().getBowPowerModifier(this.uuid) * 1.5F;
		    
			float yaw = location.getYaw();
			float pitch = location.getPitch();
			
			this.location.setX(this.location.getX() - Math.cos(yaw / 180.0F * 3.141593F) * 0.16F);
		    this.location.setY(this.location.getY() - 0.1000000014901161D);
		    this.location.setZ(this.location.getZ() - Math.sin(yaw / 180.0F * 3.141593F) * 0.16F);
		
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
		    
		    double y = this.location.getY()+motY;
		    double totalDistance = 0;
		  
		    while(y > 0 && totalDistance < MAXIMAL_LENGTH) {
				motY *= mod;
				motX *= mod;
				motZ *= mod;
				motY -= 0.05F;
				vlist.add(new Vector(motX, motY, motZ));
				y+=motY;
				totalDistance += Math.sqrt(Math.pow(motX, 2)+Math.pow(motY, 2)+Math.pow(motZ, 2));
		    }
		    this.vectorList = vlist;
		    return true;
		}
		else {
			return false;
		}
	}

	@Override
	public List<Vector> getTrajectory() {
		return vectorList != null ? vectorList : new ArrayList<Vector>();
	}

}
