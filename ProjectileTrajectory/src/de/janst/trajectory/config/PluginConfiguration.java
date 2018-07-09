package de.janst.trajectory.config;

import java.io.IOException;

public class PluginConfiguration extends ConfigWithInternalResource {

	public PluginConfiguration(String fileName) throws IOException {
		super(fileName);
	}

	public int getTickSpeed() {
		return config.getInt("ticks-between-particles");
	}
	
	public boolean saveInstant() {
		return config.getBoolean("save-instant");
	}

	public int getSaveInterval() {
		return config.getInt("save-interval");
	}
	
	public int getMaximalLength() {
		return config.getInt("maximal-length");
	}
	
	public boolean allowParticleChange() {
		return config.getBoolean("allow-particle-change");
	}
}
