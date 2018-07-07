package de.janst.trajectory;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import de.janst.trajectory.calculator.TrajectoryCalculator;
import de.janst.trajectory.command.MenuCommand;
import de.janst.trajectory.config.PlayerConfigurationDefaults;
import de.janst.trajectory.config.PluginConfiguration;
import de.janst.trajectory.listener.BowListener;
import de.janst.trajectory.listener.PlayerListener;
import de.janst.trajectory.menu.TrajectoryCustomizeMenu;
import de.janst.trajectory.menu.api.MenuSheet;
import de.janst.trajectory.menu.api.listener.InventoryListener;
import de.janst.trajectory.playerhandling.PlayerHandler;

public class TrajectorySimulator extends JavaPlugin {

	private static TrajectorySimulator INSTANCE;
	private static TrajectorySimulator plugin;
	private TrajectoryScheduler trajectoryScheduler;
	private BowListener bowListener;
	private PlayerHandler playerHandler;
	private PluginConfiguration config;
	private InventoryScheduler inventoryScheduler;
	
	public void onEnable() {
		INSTANCE = this;
		TrajectorySimulator.plugin = this;
		setUpFiles();
		try {
		config = new PluginConfiguration();

		TrajectoryCustomizeMenu.ALLOWPARTICLECHANGE = config.allowParticleChange();
		TrajectoryCalculator.MAXIMAL_LENGTH = config.getMaximalLength();
		new PlayerConfigurationDefaults();
		
		trajectoryScheduler = new TrajectoryScheduler(this, config.getTickSpeed());
		inventoryScheduler = new InventoryScheduler(this);
		this.playerHandler = new PlayerHandler(this);
		this.playerHandler.loadOnlinePlayers();
		
		} catch (IOException e) {
			getLogger().log(Level.SEVERE, "Could not load online players");
			e.printStackTrace();
		}
		
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		this.bowListener = new BowListener();
		getServer().getPluginManager().registerEvents(bowListener, this);
		
		MenuCommand menuCommand = new MenuCommand(this);
		getCommand("trajectory").setExecutor(menuCommand);
		getCommand("tra").setExecutor(menuCommand);
		
		new FileSaveScheduler(this, config.getSaveInterval());
		getLogger().info("Plugin enabled");
	}

	public void onDisable() {
		MenuSheet.closeAllMenuSheets();
		getServer().getScheduler().cancelTasks(this);
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
	
	public static TrajectorySimulator getInstance() {
		return INSTANCE;
	}
}
