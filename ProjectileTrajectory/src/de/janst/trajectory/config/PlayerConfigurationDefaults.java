package de.janst.trajectory.config;

import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerConfigurationDefaults extends Configuration {

	public static PlayerConfigurationDefaults DEFAULTS;
	
	public PlayerConfigurationDefaults() throws IOException {
		super("DefaultPlayerConfig.yml", true);
		DEFAULTS = this;
	}
	
	public YamlConfiguration getDefaults() {
		return config;
	}

}
