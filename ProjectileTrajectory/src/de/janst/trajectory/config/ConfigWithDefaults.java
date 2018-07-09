package de.janst.trajectory.config;

import java.io.IOException;

public class ConfigWithDefaults extends Config {

	public ConfigWithDefaults(String file, Config defaults) throws IOException {
		super(file);
		config.addDefaults(defaults.getYamlConfiguration());
	}

}
