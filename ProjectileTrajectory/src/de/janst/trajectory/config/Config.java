package de.janst.trajectory.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import de.janst.trajectory.TrajectorySimulator;

public class Config {

	protected File configFile = null;
	protected final String fileName;
	protected YamlConfiguration config;
	protected boolean hasChanges = false;
	protected boolean NEWFILE = false;
	
	public Config(String file) throws IOException {
		this.fileName = file;
		configFile = new File(TrajectorySimulator.getInstance().getDataFolder(), file);
		
		setupFile();
		loadConfig();
	}
	
	public void setupFile() throws IOException {
		if(!configFile.exists()) {
			configFile.createNewFile();
		}
	}
	
	private void loadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	protected void set(String key, Object value) {
		config.set(key, value);
		this.hasChanges = true;
		if(TrajectorySimulator.getInstance().getPluginConfig().saveInstant()) {
			try {
				save(false);
			} catch (IOException e) {
				TrajectorySimulator.getInstance().getLogger().log(Level.WARNING, "Could not save player settings");
				e.printStackTrace();
			}
		}
	}
	
	public void save(boolean force) throws IOException {
		System.out.println("save me");
		if(!hasChanges && !force)
			return;
		System.out.println("saving your shit");
        config.save(configFile);
        hasChanges = false;
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
