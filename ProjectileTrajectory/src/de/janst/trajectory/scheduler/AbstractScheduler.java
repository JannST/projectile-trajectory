package de.janst.trajectory.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractScheduler implements Runnable{
	
	private final JavaPlugin plugin;
	protected int pid;

	public AbstractScheduler(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void start(long speed) {
		this.pid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, speed);
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(pid);
	}
}
