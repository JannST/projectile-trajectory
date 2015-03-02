package de.janst.trajectory.menu.api.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.janst.trajectory.menu.api.MenuSheet;
import de.janst.trajectory.menu.api.SlotListener;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void invEvent(InventoryClickEvent event) {
		for(MenuSheet menu : MenuSheet.getSheets().values()) {
			if(menu.isActive()) {
				if(event.getView().equals(menu.getView())) {
					event.setCancelled(true);	
					if(event.getRawSlot() > menu.getSize()-1)
						return;
					if(menu.hasListeners()) {
						for(SlotListener listener : menu.getListeners()) {
							fireListener(listener, event);
						}
					}
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		for(MenuSheet menu : MenuSheet.getSheets().values()) {
			try {
				if(menu.isActive()) {
					if(event.getView().equals(menu.getView())) {
						menu.setView(null);
						if(!menu.isOnStopList()) {
							menu.shutMenuConstruct();
						}
						else {
							menu.removeFromStopList();
						}
						return;
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void fireListener(SlotListener listener, InventoryClickEvent event) {
		if(listener != null) {
			listener.clickSlot(event);
			if(event.isLeftClick())
				listener.leftClick(event);
			if(event.isRightClick())
				listener.rightClick(event);
			if(event.isShiftClick()) 
				listener.shiftClick(event);
		}
	}
	
	
}
