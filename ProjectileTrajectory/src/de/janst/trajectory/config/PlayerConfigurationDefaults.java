package de.janst.trajectory.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerConfigurationDefaults extends Configuration {

	public static PlayerConfigurationDefaults DEFAULTS;
	
	public PlayerConfigurationDefaults() {
		super("DefaultPlayerConfig.yml", true);
		DEFAULTS = this;
	}
	
	public YamlConfiguration getDefaults() {
		return config;
	}

}
