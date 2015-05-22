package de.janst.trajectory.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import de.janst.trajectory.TrajectorySimulator;

public class Configuration {
	
	protected final TrajectorySimulator trajectorySimulator;

	protected File configFile = null;
	protected final String fileName;
	protected YamlConfiguration config;
	protected boolean changes = false;
	protected final boolean NEWFILE;
	
	public Configuration(String file, boolean hasResource) {
		this.trajectorySimulator = TrajectorySimulator.getPlugin();
		this.fileName = file;
		configFile = new File(trajectorySimulator.getDataFolder(), file);
		
		if(!configFile.exists()) {
			NEWFILE = true;
			if(hasResource) {
				trajectorySimulator.saveResource(file, true);
			}
			else {
				try {
					configFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			changes = true;
		}
		else {
			NEWFILE = false;
		}
		
		loadConfig();
	}
	
	private void loadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	public void save() {
	    if (config == null || configFile == null) {
	    	return;
	    }
	    
	    try {
	        config.save(configFile);
	        changes = false;
	    } catch (IOException ex) {
	    	trajectorySimulator.getLogger().log(Level.WARNING, "Could not save config " + fileName + " to " + configFile.getAbsolutePath());
	    }
	}
	
	public boolean hasChanges() {
		return this.changes;
	}

	public File getFile() {
		return this.configFile;
	}
	
	public YamlConfiguration getYamlConfiguration() {
		return this.config;
	}
}
