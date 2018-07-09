package de.janst.trajectory.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.janst.trajectory.TrajectorySimulator;
import de.janst.trajectory.playerhandling.PlayerObject;
import de.janst.trajectory.util.Permission;

public class MenuCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("sorry pal, this is a player only command");
			return true;
		}
		if(label.equalsIgnoreCase("trajectory") || label.equalsIgnoreCase("tra")) {
			Player player = (Player)sender;
			PlayerObject playerObject = TrajectorySimulator.getInstance().getPlayerHandler().getPlayerObject(player.getUniqueId());
			
			if(playerObject != null) {
				if(playerObject.hasPermission(Permission.USE)) {
					playerObject.showMenu();
				}
				else {
					player.sendMessage("�cSorry pal, you are not allowed to use this command");
					return true;
				}
			}
		}
		return false;
	}

}
