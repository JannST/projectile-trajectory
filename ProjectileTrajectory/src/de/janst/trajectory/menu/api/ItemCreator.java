package de.janst.trajectory.menu.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCreator {
	
	private List<String> lore = new ArrayList<String>();
	private String title;
	private ItemScheme scheme;
	
	public ItemCreator(String title, Material material, int amount, byte data, short damage) {
		this(title, new ItemScheme(material, amount, data, damage));
	}
	
	public ItemCreator(String title, ItemScheme scheme) {
		this.scheme = scheme;
		this.title = ChatColor.RESET + title;
	}
	
	public ItemCreator(String title, Material material, int amount) {
		this(title, material, amount, (byte)0, (short) 0);
	}
	
	public ItemCreator addLore(String loreString) {
		this.lore.add(ChatColor.RESET + loreString);
		return this;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ItemStack toItem() {
		ItemStack item = scheme.toItem();
		ItemMeta meta = item.getItemMeta();
		if(title != null)
			meta.setDisplayName(title);
		if(lore != null && lore.size() > 0)
			meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
