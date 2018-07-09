package de.janst.trajectory.config;

import java.io.IOException;

import de.janst.trajectory.TrajectorySimulator;

public class ConfigWithInternalResource extends Config {

	public ConfigWithInternalResource(String fileName) throws IOException {
		super(fileName);
	}
	
	@Override
	public void setupFile() {
		if(!configFile.exists()) {
			hasChanges = true;
			NEWFILE = true;
			TrajectorySimulator.getInstance().saveResource(fileName, true);
		}
	}
}
