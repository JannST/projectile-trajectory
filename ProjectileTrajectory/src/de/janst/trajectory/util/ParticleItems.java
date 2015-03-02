package de.janst.trajectory.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.darkblade12.particleeffect.ParticleEffect;

import de.janst.trajectory.menu.api.ItemCreator;

public enum ParticleItems {

	REDSTONE(ParticleEffect.REDSTONE, new ItemCreator("§a§lRedstone particles", Material.REDSTONE, 1).toItem()),
	FIREWORK_SPARK(ParticleEffect.FIREWORKS_SPARK, new ItemCreator("§a§lFirework spark particles", Material.FIREWORK, 1).toItem()),
	MAGIC_CRIT(ParticleEffect.CRIT_MAGIC, new ItemCreator("§a§lMagic crit particles", Material.NETHER_STAR, 1).toItem()),
	SPELL(ParticleEffect.SPELL_MOB, new ItemCreator("§a§lSpell particles", Material.POTION, 1).toItem()),
	SMOKE(ParticleEffect.SMOKE_NORMAL, new ItemCreator("§a§lSmoke particles", Material.FIREWORK_CHARGE, 1).toItem()),
	FLAME(ParticleEffect.FLAME, new ItemCreator("§a§lFlame particles", Material.TORCH, 1).toItem()),
	EXPLODE(ParticleEffect.EXPLOSION_LARGE, new ItemCreator("§a§lExplode particles", Material.TNT, 1).toItem()),
	LAVA(ParticleEffect.LAVA, new ItemCreator("§a§lLava particles", Material.LAVA_BUCKET, 1).toItem()),
	CLOUD(ParticleEffect.CLOUD, new ItemCreator("§a§lCloud particles", Material.FEATHER, 1).toItem()),
	HEART(ParticleEffect.HEART, new ItemCreator("§a§lLove particles", Material.COOKIE, 1).toItem()),
	GREEN_STAR(ParticleEffect.VILLAGER_HAPPY, new ItemCreator("§a§lGreen star particles", Material.LONG_GRASS, 1, (byte)1, (short)0).toItem()),
	WATER_DRIP(ParticleEffect.DRIP_WATER, new ItemCreator("§a§lWater drip particles", Material.WATER_BUCKET, 1).toItem()),
	LAVA_DRIP(ParticleEffect.DRIP_LAVA, new ItemCreator("§a§lLava drip particles", Material.LAVA_BUCKET, 1).toItem()),
	CRIT(ParticleEffect.CRIT, new ItemCreator("§a§lCrit particles", Material.NETHER_STAR, 1).toItem());
	
	private ParticleEffect effect;
	private ItemStack itemStack;

	ParticleItems(ParticleEffect effect, ItemStack itemStack) {
		this.effect = effect;
		this.itemStack = itemStack;
	}
	
	public ParticleEffect getParticleEffect() {
		return this.effect;
	}
	
	public ItemStack getItem() {
		return this.itemStack;
	}
	
	public static ParticleItems fromEffect(ParticleEffect effect) {
		for(ParticleItems item : values()) {
			if(item.getParticleEffect() == effect) {
				return item;
			}
		}
		return null;
	}
}
