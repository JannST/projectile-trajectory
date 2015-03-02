package de.janst.trajectory.menu;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.menu.api.ItemCreator;
import de.janst.trajectory.menu.api.MenuSheet;
import de.janst.trajectory.menu.api.SlotListener;
import de.janst.trajectory.playerhandling.PlayerObject;
import de.janst.trajectory.util.RGBColor;

public class ColorSelectMenu extends MenuSheet {

	private final PlayerObject playerObject;
	private final CalculatorType type;

	public ColorSelectMenu(MenuSheet parent, PlayerObject playerObject, CalculatorType type) {
		super(parent.getPlugin(), "§6§lChoose color", 18, parent);
		registerListener("base", new MainListener());
		this.playerObject = playerObject;
		this.type = type;
		initContents();
		updateInventory();
	}

	@Override
	public void initContents() {
		setContent(0, new ItemCreator("§c§lback", Material.BUCKET, 1).toItem());
		int insertion = 1;
		for(RGBColor color : RGBColor.values()) {
			setContent(insertion++, new ItemCreator("§a§lChoose: "+color.getDisplayName(), Material.WOOL, 1, color.getData(), (short)0).toItem());
		}
	}

	private class MainListener implements SlotListener {

		@Override
		public void clickSlot(InventoryClickEvent event) {
			if(event.getSlot() == 0) {
				shutMenu();
				getParent().show();
			}
			else if(event.getSlot() <= RGBColor.values().length && event.getSlot() > 0) {
				playerObject.getConfig().setParticleColor(RGBColor.values()[event.getSlot()-1], type);
				TrajectoryCustomizeMenu menu = (TrajectoryCustomizeMenu) getParent();
				menu.setColorItems();
				menu.updateInventory();				
			}
		}

		@Override
		public void leftClick(InventoryClickEvent event) {

		}

		@Override
		public void rightClick(InventoryClickEvent event) {

		}

		@Override
		public void shiftClick(InventoryClickEvent event) {
			
		}
		
	}
}
