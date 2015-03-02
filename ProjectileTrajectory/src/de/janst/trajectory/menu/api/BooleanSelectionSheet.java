package de.janst.trajectory.menu.api;

import org.bukkit.plugin.java.JavaPlugin;

public class BooleanSelectionSheet extends SelectionMenuSheet {

	private SelectionData rtrue;
	private SelectionData rfalse;
	
	public BooleanSelectionSheet(JavaPlugin plugin, String title, RequestingMenuSheet parent, SelectionData rtrue, SelectionData rfalse, String requestName) {
		super(plugin, title, 27, parent, requestName);
		rtrue.addData("boolean_result", true);
		rfalse.addData("boolean_result", false);
		this.rtrue = rtrue;
		this.rfalse = rfalse;
		initContents();
	}

	@Override
	public void initContents() {
		setDataItem(11, rtrue);
		setDataItem(15, rfalse);
		updateInventory();
	}

}
