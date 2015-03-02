package de.janst.trajectory.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.util.Vector;

public class NormalCalculator extends TrajectoryCalculator {

	private List<Vector> vectorList;
	private final float mod = 0.99F;
	private final float f = 0.4F;
	
	private float modE = 1.5F;
	private float modF = 0.0F;
	private float modFall = 0.03F;
	
	public NormalCalculator(UUID uuid, CalculatorType type, float modE, float modF, float modFall) {
		super(uuid, type);
		this.modE = modE;
		this.modF = modF;
		this.modFall = modFall;
	}
	
	public NormalCalculator(UUID uuid, CalculatorType type) {
		super(uuid, type);
	}
	
	@Override
	public boolean calculate() {
	    List<Vector> vlist = new ArrayList<Vector>();
		
		float yaw = location.getYaw();
		float pitch = location.getPitch();
		
		this.location.setX(this.location.getX() - Math.cos(yaw / 180.0F * 3.141593F) * 0.16F);
	    this.location.setY(this.location.getY() - 0.1000000014901161D);
	    this.location.setZ(this.location.getZ() - Math.sin(yaw / 180.0F * 3.141593F) * 0.16F);
	
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
	    
	    double y = this.location.getY()+motY;
	    double totalDistance = 0;
	    
	    while(y > 0 && totalDistance < MAXIMAL_LENGTH) {
			motY *= mod;
			motX *= mod;
			motZ *= mod;
			motY -= modFall;
			vlist.add(new Vector(motX, motY, motZ));
			y+=motY;
			totalDistance += Math.sqrt(Math.pow(motX, 2)+Math.pow(motY, 2)+Math.pow(motZ, 2));
	    }
	    this.vectorList = vlist;
	    return true;
	}

	@Override
	public List<Vector> getTrajectory() {
		return vectorList != null ? vectorList : new ArrayList<Vector>();
	}
}
