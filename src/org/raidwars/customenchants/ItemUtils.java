package org.raidwars.customenchants;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.raidwars.customenchants.enchants.EnchantType;

public class ItemUtils {
	
	public static ItemStack getCustomItem(EnchantType type) {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN + "Enchanted Crystal");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(type.getName());
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static boolean isCrystal(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) return false;
		return itemMeta.getDisplayName().equals(ChatColor.GREEN + "Enchanted Crystal");
	}
//	
	public static String getLore(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) return null;
		return itemMeta.getLore().get(0);
	}
//	
	public static boolean hasLore(ItemStack item, String lore) {
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) return false;
		return itemMeta.getLore().contains(lore);
	}
//	
	public static ItemStack enchantItem(EnchantType type, ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		item.setItemMeta(itemMeta);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(type.getName());
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
	
}