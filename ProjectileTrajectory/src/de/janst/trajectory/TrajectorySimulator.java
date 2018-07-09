package de.janst.trajectory;

import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

import de.janst.trajectory.command.MenuCommand;
import de.janst.trajectory.config.PlayerConfigurationDefaults;
import de.janst.trajectory.config.PluginConfiguration;
import de.janst.trajectory.listener.BowListener;
import de.janst.trajectory.listener.PlayerListener;
import de.janst.trajectory.menu.api.MenuSheet;
import de.janst.trajectory.menu.api.listener.InventoryListener;
import de.janst.trajectory.playerhandling.PlayerHandler;
import de.janst.trajectory.scheduler.FileSaveScheduler;
import de.janst.trajectory.scheduler.InventoryScheduler;
import de.janst.trajectory.scheduler.TrajectoryScheduler;

public class TrajectorySimulator extends JavaPlugin {

	private static TrajectorySimulator INSTANCE;
	private PlayerHandler playerHandler;
	private PluginConfiguration config;
	private InventoryScheduler inventoryScheduler;
	private PlayerConfigurationDefaults playerConfigurationDefaults;
	
	public void onEnable() {
		INSTANCE = this;
		try {
		setupFiles();
		this.config = new PluginConfiguration("config.yml");
		this.playerConfigurationDefaults = new PlayerConfigurationDefaults("DefaultPlayerConfig.yml");
		
		this.playerHandler = new PlayerHandler(this);
		this.playerHandler.loadOnlinePlayers();
		
		TrajectoryScheduler trajectoryScheduler = new TrajectoryScheduler();
		trajectoryScheduler.start(config.getTickSpeed());
		
		this.inventoryScheduler = new InventoryScheduler();
		this.inventoryScheduler.start(5L);

		if(!config.saveInstant()) {
			FileSaveScheduler fileSaveScheduler = new FileSaveScheduler();
			fileSaveScheduler.start(config.getSaveInterval()*60*20);
		}
		
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new BowListener(), this);
		
		MenuCommand menuCommand = new MenuCommand();
		getCommand("trajectory").setExecutor(menuCommand);
		getCommand("tra").setExecutor(menuCommand);
		
		getLogger().info("Plugin enabled!");
		
		} catch (Exception e) {
			getLogger().info("Failed to enable Plugin!");
			e.printStackTrace();
		}
	}

	public void onDisable() {
		MenuSheet.closeAllMenuSheets();
		getServer().getScheduler().cancelTasks(this);
		getPlayerHandler().saveAll();
	}
	
	public void setupFiles() {
		File file = getDataFolder();
		if(!file.exists()) {
			file.mkdir();
		}
		file = new File(getDataFolder() + "/players");
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	public InventoryScheduler getInventoryScheduler() {
		return inventoryScheduler;
	}

	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}
	
	public PluginConfiguration getPluginConfig() {
		return this.config;
	}
	
	public PlayerConfigurationDefaults getPlayerConfigurationDefaults() {
		return playerConfigurationDefaults;
	}
	
	public static TrajectorySimulator getInstance() {
		return INSTANCE;
	}
}
