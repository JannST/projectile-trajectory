package de.janst.trajectory;

import org.bukkit.Bukkit;

public class FileSaveScheduler implements Runnable {

	private final TrajectorySimulator trajectorySimulator;
	private int pid;

	public FileSaveScheduler(TrajectorySimulator trajectorySimulator, int speed) {
		this.trajectorySimulator = trajectorySimulator;
		start(speed*60*20);
	}

	public void start(long speed) {
		this.pid = Bukkit.getScheduler().scheduleSyncRepeatingTask(trajectorySimulator, this, 0L, speed);
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(pid);
	}
	
	@Override
	public void run() {
		trajectorySimulator.getPlayerHandler().saveAll();
	}

}
