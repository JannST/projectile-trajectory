package de.janst.trajectory.config;

import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerConfigurationDefaults extends Config {
	
	public PlayerConfigurationDefaults(String fileName) throws IOException {
		super(fileName);
	}
	
	public YamlConfiguration getDefaults() {
		return config;
	}

}
