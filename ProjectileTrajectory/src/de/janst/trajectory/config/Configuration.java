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
	protected boolean hasChanges = false;
	protected boolean NEWFILE = false;
	
	public Configuration(String file, boolean hasResource) throws IOException {
		this.trajectorySimulator = TrajectorySimulator.getPlugin();
		this.fileName = file;
		configFile = new File(trajectorySimulator.getDataFolder(), file);
		
		if(!configFile.exists()) {
			hasChanges = true;
			NEWFILE = true;
			if(hasResource) {
				trajectorySimulator.saveResource(file, true);
			}
			else {
				configFile.createNewFile();
			}
		}
		loadConfig();
	}
	
	private void loadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	public void save() throws IOException {
	    if (config == null || configFile == null) {
	    	return;
	    }
        config.save(configFile);
        hasChanges = false;
//	    } catch (IOException ex) {
//	    	trajectorySimulator.getLogger().log(Level.WARNING, "Could not save config " + fileName + " to " + configFile.getAbsolutePath());
//	    }
	}
	
	public boolean hasChanges() {
		return this.hasChanges;
	}

	public File getFile() {
		return this.configFile;
	}
	
	public YamlConfiguration getYamlConfiguration() {
		return this.config;
	}
}
