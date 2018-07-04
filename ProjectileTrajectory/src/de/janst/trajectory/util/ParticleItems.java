package de.janst.trajectory.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Particle;

import de.janst.trajectory.menu.api.ItemCreator;

public enum ParticleItems {

	REDSTONE(Particle.REDSTONE, new ItemCreator("�a�lRedstone particles", Material.REDSTONE, 1).toItem()),
	FIREWORK_SPARK(Particle.FIREWORKS_SPARK, new ItemCreator("�a�lFirework spark particles", Material.FIREWORK, 1).toItem()),
	MAGIC_CRIT(Particle.CRIT_MAGIC, new ItemCreator("�a�lMagic crit particles", Material.NETHER_STAR, 1).toItem()),
	SPELL(Particle.SPELL_MOB, new ItemCreator("�a�lSpell particles", Material.POTION, 1).toItem()),
	SMOKE(Particle.SMOKE_NORMAL, new ItemCreator("�a�lSmoke particles", Material.FIREWORK_CHARGE, 1).toItem()),
	FLAME(Particle.FLAME, new ItemCreator("�a�lFlame particles", Material.TORCH, 1).toItem()),
	EXPLODE(Particle.EXPLOSION_LARGE, new ItemCreator("�a�lExplode particles", Material.TNT, 1).toItem()),
	LAVA(Particle.LAVA, new ItemCreator("�a�lLava particles", Material.LAVA_BUCKET, 1).toItem()),
	CLOUD(Particle.CLOUD, new ItemCreator("�a�lCloud particles", Material.FEATHER, 1).toItem()),
	HEART(Particle.HEART, new ItemCreator("�a�lLove particles", Material.COOKIE, 1).toItem()),
	GREEN_STAR(Particle.VILLAGER_HAPPY, new ItemCreator("�a�lGreen star particles", Material.LONG_GRASS, 1, (byte)1, (short)0).toItem()),
	WATER_DRIP(Particle.DRIP_WATER, new ItemCreator("�a�lWater drip particles", Material.WATER_BUCKET, 1).toItem()),
	LAVA_DRIP(Particle.DRIP_LAVA, new ItemCreator("�a�lLava drip particles", Material.LAVA_BUCKET, 1).toItem()),
	CRIT(Particle.CRIT, new ItemCreator("�a�lCrit particles", Material.NETHER_STAR, 1).toItem());
	
	private Particle effect;
	private ItemStack itemStack;

	ParticleItems(Particle effect, ItemStack itemStack) {
		this.effect = effect;
		this.itemStack = itemStack;
	}
	
	public Particle getParticle() {
		return this.effect;
	}
	
	public ItemStack getItem() {
		return this.itemStack;
	}
	
	public static ParticleItems fromEffect(Particle effect) {
		for(ParticleItems item : values()) {
			if(item.getParticle() == effect) {
				return item;
			}
		}
		return null;
	}
}
