package de.janst.trajectory.menu.api;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface SlotListener {
	public void clickSlot(InventoryClickEvent event);
	public void leftClick(InventoryClickEvent event);
	public void rightClick(InventoryClickEvent event);
	public void shiftClick(InventoryClickEvent event);
}
