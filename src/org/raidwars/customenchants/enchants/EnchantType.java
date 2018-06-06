package org.raidwars.customenchants.enchants;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.ImmutableSet;

public enum EnchantType {

	STRENGTH(ChatColor.GRAY + "Strength II", ImmutableSet.of(Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE), new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000, 1)),
	SPEED(ChatColor.GRAY + "Speed II", ImmutableSet.of(Material.DIAMOND_BOOTS), new PotionEffect(PotionEffectType.SPEED, 100000, 1)),
	FIRE_RESISTANCE(ChatColor.GRAY + "Fire Resistance I", ImmutableSet.of(Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE), new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000, 0)),
	WATER_BREATHING(ChatColor.GRAY + "Water Breathing I", ImmutableSet.of(Material.DIAMOND_HELMET), new PotionEffect(PotionEffectType.WATER_BREATHING, 100000, 0)),
	REGENERATION(ChatColor.GRAY + "Regeneration I", ImmutableSet.of(Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE), new PotionEffect(PotionEffectType.REGENERATION, 100000, 0)),
	SATURATION(ChatColor.GRAY + "Saturation I", ImmutableSet.of(Material.DIAMOND_HELMET), new PotionEffect(PotionEffectType.SATURATION, 100000, 5)),
	HEALTH_BOOST(ChatColor.GRAY + "Health Boost I", ImmutableSet.of(Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE), new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000, 1));
//	HEALTHBOOST(ChatColor.GRAY + "Health Boost I", ImmutableSet.of(Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE), new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000, 1)),

	String name;
	ImmutableSet<Material> items;
	PotionEffect potionEffect;

	private EnchantType(String name, ImmutableSet<Material> material, PotionEffect potionEffect) {
		this.name = name;
		this.items = material;
		this.potionEffect = potionEffect;
	}

	private EnchantType(String name, ImmutableSet<Material> material) {
		this.name = name;
		this.items = material;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ImmutableSet<Material> getItems() {
		return items;
	}

	public PotionEffect getPotionEffect() {
		return potionEffect;
	}
	
	public static EnchantType getEnchant(String name) {
		for (EnchantType enchant : EnchantType.values()) {
			if (enchant.getName().equalsIgnoreCase(name)) {
				return enchant;
			}
		}
		return null;
	}

}
