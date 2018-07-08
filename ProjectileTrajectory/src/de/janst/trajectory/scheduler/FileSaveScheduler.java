package de.janst.trajectory.scheduler;

import de.janst.trajectory.TrajectorySimulator;

public class FileSaveScheduler extends AbstractScheduler {

	public FileSaveScheduler(int speed) {
		super(TrajectorySimulator.getInstance());
		start(speed*60*20);
	}
	
	@Override
	public void run() {
		TrajectorySimulator.getInstance().getPlayerHandler().saveAll();
	}

}
