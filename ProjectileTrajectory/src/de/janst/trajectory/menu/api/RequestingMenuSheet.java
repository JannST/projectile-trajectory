package de.janst.trajectory.menu.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class RequestingMenuSheet extends MenuSheet {

	private Map<String, Integer> selectionSheets = new HashMap<String, Integer>(); 
	
	public RequestingMenuSheet(JavaPlugin plugin, String title, int size, MenuSheet parent) {
		super(plugin, title, size, parent);
	}
	
	public RequestingMenuSheet(JavaPlugin plugin, String title, int size, Player player) {
		super(plugin, title, size, player);
	}
	
	public boolean isRequestedSelectionSheet(String selectionSheet) {
		return selectionSheets.containsKey(selectionSheet);
	}
	
	public void addSelectionSheet(String request, SelectionMenuSheet sheet) {
		selectionSheets.put(request, sheet.getID());
	}
	
	public void requestData(String selectionSheet) {
		if(selectionSheets.containsKey(selectionSheet)) {
			MenuSheet sheet = getSheet(selectionSheets.get(selectionSheet));
			if(sheet == null)
				return;
				standby();
				sheet.show();
		}
	}
	
	public void handleRawResult(String request, SelectionData data) {
		if(!this.selectionSheets.containsKey(request))
			return;
		selectionSheets.remove(request);
		handleResult(request, data);
	}
	
	protected abstract void handleResult(String request, SelectionData data);

}
