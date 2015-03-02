package de.janst.trajectory.menu.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemScheme {

	private Material material;
	private byte data;
	private int amount;
	private short damage;
	private boolean glow;
	
	public ItemScheme(Material material, int amount, byte data, short damage) {
		super();
		this.material = material;
		this.data = data;
		this.amount = amount;
		this.damage = damage;
	}
	
	public ItemScheme(Material material, int amount) {
		this(material, amount, (byte)0, (short)0);
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public short getDamage() {
		return damage;
	}

	public void setDamage(short damage) {
		this.damage = damage;
	}

	public boolean isGlowing() {
		return glow;
	}

	public void setGlow(boolean glow) {
		this.glow = glow;
	}
	
	public ItemStack toItem() {
		@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(material, amount, damage, data);
		return item;
	}
}
