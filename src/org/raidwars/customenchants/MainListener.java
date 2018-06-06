package org.raidwars.customenchants;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.raidwars.customenchants.enchants.EnchantType;

public class MainListener implements Listener {

	private Main plugin;

	public MainListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void addEnchantment(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		Player player = (Player) e.getWhoClicked();
		if (inv != null && e.getCursor() != null && e.getCurrentItem() != null
				&& e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
			ItemStack crystal = e.getCursor();
			ItemStack item = e.getCurrentItem();
			if (crystal.getType() == Material.NETHER_STAR && ItemUtils.isCrystal(crystal)) {
				EnchantType type = EnchantType.getEnchant(ItemUtils.getLore(crystal));
				if (type.getItems().contains(item.getType()) && !hasEnchantment(item, type.getName())) {
					e.setCancelled(true);
					if (player.getItemOnCursor().getAmount() == 1) {
						player.setItemOnCursor((ItemStack) null);
					} else {
						player.getItemOnCursor().setAmount(player.getItemOnCursor().getAmount() - 1);
					}
					player.getInventory().setItem(e.getSlot(), ItemUtils.enchantItem(type, item));
					player.updateInventory();
				}
			}
		}
		if (e.getSlotType().equals(SlotType.ARMOR)) {
			if (e.getCursor() != null && EnchantType.getEnchant(ItemUtils.getLore(e.getCursor())) != null) {
				ItemUtils.getLores(e.getCursor())
						.forEach(l -> player.addPotionEffect(EnchantType.getEnchant(l).getPotionEffect()));
			}
			if (e.getCurrentItem() != null && EnchantType.getEnchant(ItemUtils.getLore(e.getCurrentItem())) != null) {
				ItemUtils.getLores(e.getCurrentItem())
						.forEach(l -> player.removePotionEffect(EnchantType.getEnchant(l).getPotionEffect().getType()));
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getName().equals(plugin.colorize(plugin.getConfig().getString("enchantment-shop")))) {
			if (event.getInventory() == null) return;
			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getType() == Material.NETHER_STAR) {
				if (player.getInventory().firstEmpty() == -1) {
					player.sendMessage(ChatColor.RED + "Your inventory is full!");
					return;
				}
				EnchantType enchantType = EnchantType.valueOf(ItemUtils.getKeyString(event.getCurrentItem(), "ENCHANT"));
				if(player.getLevel() >= plugin.getConfig().getInt(enchantType + ".levels")) {
					player.getInventory().addItem(ItemUtils.getCustomItem(enchantType));
					player.setLevel(player.getLevel() - plugin.getConfig().getInt(enchantType + ".levels"));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("not-enought-xp")));
				}
			}
			event.setCancelled(true);
		}
	}

	private boolean hasEnchantment(ItemStack item, String enchant) {
		if (item != null && item.hasItemMeta()) {
			ItemMeta itemMeta = item.getItemMeta();
			List<String> lore = itemMeta.getLore();
			if (lore.contains(enchant))
				return true;
		}
		return false;
	}

}
