package de.janst.trajectory.scheduler;

import de.janst.trajectory.TrajectorySimulator;

public class FileSaveScheduler extends AbstractScheduler {

	public FileSaveScheduler() {
		super(TrajectorySimulator.getInstance());
	}
	
	@Override
	public void run() {
		TrajectorySimulator.getInstance().getPlayerHandler().saveAll();
	}

}
