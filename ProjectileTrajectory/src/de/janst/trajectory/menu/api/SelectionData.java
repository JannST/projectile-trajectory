package de.janst.trajectory.menu.api;

import java.util.HashMap;
import java.util.Map;

public class SelectionData {

	private static int idCounter = 0;
	private final int id;
	private final ItemCreator creator;
	private Map<String, Object> data = new HashMap<String, Object>();
	
	public SelectionData(ItemCreator creator) {
		id = idCounter++;
		this.creator = creator;
	}
	
	public int getId() {
		return id;
	}

	public ItemCreator getCreator() {
		return creator;
	}
	
	public void addData(String identifier, Object data) {
		this.data.put(identifier, data);
	}
	
	public boolean isAssigned(String identifier) {
		return this.data.containsKey(identifier);
	}
	
	public Object getData(String identifier) {
		return this.data.get(identifier);
	}
}
