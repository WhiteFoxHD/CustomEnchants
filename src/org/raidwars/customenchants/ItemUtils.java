package org.raidwars.customenchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.raidwars.customenchants.enchants.EnchantType;

public class ItemUtils {

	public static ItemStack getCustomItem(EnchantType type) {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("crystal-name")));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(type.getName());
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static ItemStack getShowCase(EnchantType type) {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString(type + ".display")));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(type.getName());
		lore.add(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("cost-lore").replace("%levels%", String.valueOf(Main.getInstance().getConfig().getInt(type + ".levels")))));
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return setKeyString(item, "ENCHANT", type.name());
	}
	
	public static ItemStack setKeyString(ItemStack itemStack, String key, String value) {
		net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
		nms.getTag().setString(key, value);
		return CraftItemStack.asBukkitCopy(nms);
	}

	public static String getKeyString(ItemStack itemStack, String key) {
		net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
		return nms.getTag().getString(key);
	}
	
	public static boolean isCrystal(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta == null)
			return false;
		return itemMeta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("crystal-name")));
	}

	public static List<String> getLores(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta == null)
			return null;
		return itemMeta.getLore();
	}

	public static String getLore(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta == null)
			return null;
		return itemMeta.getLore().get(0);
	}

	public static boolean hasLore(ItemStack item, String lore) {
		ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta == null)
			return false;
		return itemMeta.getLore().contains(lore);
	}

	public static ItemStack enchantItem(EnchantType type, ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		item.setItemMeta(itemMeta);
		ArrayList<String> lore = new ArrayList<String>();
		if (itemMeta.hasLore()) {
			itemMeta.getLore().forEach(l -> lore.add(l));
		}
		lore.add(type.getName());
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
}