package de.janst.trajectory;

import java.io.File;
import java.io.IOException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.janst.trajectory.calculator.TrajectoryCalculator;
import de.janst.trajectory.command.MenuCommand;
import de.janst.trajectory.config.PlayerConfiguration;
import de.janst.trajectory.config.PlayerConfigurationDefaults;
import de.janst.trajectory.config.PluginConfiguration;
import de.janst.trajectory.listener.BowListener;
import de.janst.trajectory.listener.PlayerListener;
import de.janst.trajectory.menu.api.MenuSheet;
import de.janst.trajectory.menu.api.listener.InventoryListener;
import de.janst.trajectory.metrics.Metrics;
import de.janst.trajectory.playerhandling.PlayerHandler;

public class TrajectorySimulator extends JavaPlugin implements Listener {

	private static TrajectorySimulator plugin;
	private TrajectoryScheduler trajectoryScheduler;
	private BowListener bowListener;
	private PlayerHandler playerHandler;
	private PluginConfiguration config;
	private InventoryScheduler inventoryScheduler;
	
	public void onEnable() {
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
			getLogger().info("Metrics enabled");
		} catch (IOException e) {}
		TrajectorySimulator.plugin = this;
		setUpFiles();
		config = new PluginConfiguration();
		TrajectoryCalculator.MAXIMAL_LENGTH = config.getMaximalLength();
		new PlayerConfigurationDefaults();
		
		trajectoryScheduler = new TrajectoryScheduler(this, config.getTickSpeed());
		inventoryScheduler = new InventoryScheduler(this);
		this.playerHandler = new PlayerHandler(this);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		this.bowListener = new BowListener();
		getServer().getPluginManager().registerEvents(bowListener, this);
		
		MenuCommand menuCommand = new MenuCommand(this);
		getCommand("trajectory").setExecutor(menuCommand);
		getCommand("tra").setExecutor(menuCommand);
		
		if(config.saveInstant()) {
			PlayerConfiguration.SAVE_INSTANT = true;
		}
		else {
			new FileSaveScheduler(this, config.getSaveInterval());
		}
		getLogger().info("Plugin enabled");
	}

	public void onDisable() {
		MenuSheet.closeAllMenuSheets();
		getServer().getScheduler().cancelTasks(this);
		if(!config.saveInstant())
			getPlayerHandler().saveAll();
	}
	
	public InventoryScheduler getInventoryScheduler() {
		return inventoryScheduler;
	}
	
	public BowListener getBowListener() {
		return this.bowListener;
	}

	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	public TrajectoryScheduler getTrajectoryScheduler() {
		return trajectoryScheduler;
	}

	public static TrajectorySimulator getPlugin() {
		return TrajectorySimulator.plugin;
	}
	
	public void setUpFiles() {
		File file = getDataFolder();
		if(!file.exists()) {
			file.mkdir();
		}
		file = new File(getDataFolder() + "/players");
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	public PluginConfiguration getPluginConfig() {
		return this.config;
	}
}
