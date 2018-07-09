package de.janst.trajectory.menu;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import de.janst.trajectory.calculator.CalculatorType;
import de.janst.trajectory.menu.api.ItemCreator;
import de.janst.trajectory.menu.api.MenuSheet;
import de.janst.trajectory.menu.api.SlotListener;
import de.janst.trajectory.playerhandling.PlayerObject;
import de.janst.trajectory.util.ParticleItems;

public class ParticleSelectMenu extends MenuSheet implements SlotListener {

	private final PlayerObject playerObject;
	private final CalculatorType type;

	public ParticleSelectMenu(MenuSheet parent, PlayerObject playerObject, CalculatorType type) {
		super(parent.getPlugin(), "�6�lChoose particle", 18, parent);
		registerListener("base", this);
		this.playerObject = playerObject;
		this.type = type;
		initContents();
		updateInventory();
	}

	@Override
	public void initContents() {
		setContent(0, new ItemCreator("�c�lback", Material.BUCKET, 1).toItem());
		int insertion = 1;
		for (ParticleItems item : ParticleItems.values()) {
			setContent(insertion++, item.getItem());
		}
	}

	@Override
	public void clickSlot(InventoryClickEvent event) {
		if (event.getSlot() == 0) {
			shutMenu();
			getParent().show();
		} else if (event.getSlot() <= ParticleItems.values().length) {
			playerObject.getConfig().setTrajectoryParticle(ParticleItems.values()[event.getSlot() - 1].getParticle(),
					type);
			TrajectoryCustomizeMenu menu = (TrajectoryCustomizeMenu) getParent();
			menu.setParticleItem();
			// menu.setColorItems();
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
