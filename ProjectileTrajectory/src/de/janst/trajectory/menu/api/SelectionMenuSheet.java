package de.janst.trajectory.menu.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SelectionMenuSheet extends MenuSheet {

	private List<SelectionData> options;
	private Map<Integer, SelectionData> data = new HashMap<Integer, SelectionData>();
	private final String requestName;
	
	public SelectionMenuSheet(JavaPlugin plugin, String title, int size, List<SelectionData> options, RequestingMenuSheet parent, String requestName) {
		this(plugin, title, size, parent, requestName);
		this.options = options;
	}
	
	protected SelectionMenuSheet(JavaPlugin plugin, String title, int size, RequestingMenuSheet parent, String requestName) {
		super(plugin, title, size, parent);
		this.requestName = requestName;
		registerListener("main_selection_listener", new SelectionSheetListener(this));
	}

	public List<SelectionData> getOptions() {
		return options;
	}
	
	public void handleClick(int slot) {
		if(data.containsKey(slot)) {
			System.out.println("shut?");
			super.shutMenu();
			((RequestingMenuSheet)getParent()).handleRawResult(getRequestName(), this.data.get(slot));
		}
	}
	
	@Override
	public void shutMenuConstruct() {
		shutMenu();
	}
	
	@Override
	public void shutMenu() {
		super.shutMenu();
		((RequestingMenuSheet)getParent()).handleRawResult(this.requestName, null);
	}
	
	@Override
	public void standby() {
		shutMenu();
	}
	
	public void setDataItem(int slot, SelectionData data) {
		setContent(slot, data.getCreator().toItem());
		this.data.put(slot, data);
	}

	public Map<Integer, SelectionData> getData() {
		return data;
	}

	public void setData(Map<Integer, SelectionData> data) {
		this.data = data;
	}

	public String getRequestName() {
		return requestName;
	}
	
	private class SelectionSheetListener implements SlotListener {

		private SelectionMenuSheet menu;

		public SelectionSheetListener(SelectionMenuSheet menu) {
			this.menu = menu;
		}
		
		
		@Override
		public void clickSlot(InventoryClickEvent event) {
			System.out.println("heh?");
			menu.handleClick(event.getSlot());
		}

		@Override
		public void leftClick(InventoryClickEvent event) {}

		@Override
		public void rightClick(InventoryClickEvent event) {}

		@Override
		public void shiftClick(InventoryClickEvent event) {}
		
	}
}
