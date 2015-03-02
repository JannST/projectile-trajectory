package de.janst.trajectory.config;

public class PluginConfiguration extends Configuration {

	
	public PluginConfiguration() {
		super("config.yml", true);
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
}
