package de.janst.trajectory.scheduler;

import java.util.Collection;
import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.playerhandling.PlayerObject;

public class TrajectoryScheduler extends AbstractScheduler {

	public TrajectoryScheduler(int speed) {
		super(TrajectorySimulator.getInstance());
		start(speed);
	}

	@Override
	public void run() {
		Collection<PlayerObject> playerObjects = TrajectorySimulator.getInstance().getPlayerHandler().getPlayerObjects();

		for (PlayerObject playerObject : playerObjects) {
			if (playerObject.isValidForTrajectoryRender()) {
				playerObject.drawTrajectory();
			}
		}
	}
}
