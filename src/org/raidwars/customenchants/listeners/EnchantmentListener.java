package org.raidwars.customenchants.listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.raidwars.customenchants.ItemUtils;
import org.raidwars.customenchants.Main;
import org.raidwars.customenchants.armor.ArmorEquipEvent;
import org.raidwars.customenchants.enchants.EnchantType;

public class EnchantmentListener implements Listener {

	private Main plugin;

	public EnchantmentListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent event) {
		Player player = event.getPlayer();
		if(event.getNewArmorPiece() != null && event.getNewArmorPiece().getType() != Material.AIR) {
			for (String lore : ItemUtils.getLores(event.getNewArmorPiece())) {
				player.addPotionEffect(EnchantType.getEnchant(lore).getPotionEffect());
			}
		}
		if(event.getOldArmorPiece() != null && event.getOldArmorPiece().getType() != Material.AIR) {
			for (String lore : ItemUtils.getLores(event.getOldArmorPiece())) {
				player.removePotionEffect(EnchantType.getEnchant(lore).getPotionEffect().getType());
			}
		}
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
	}

	private boolean hasEnchantment(ItemStack item, String enchant) {
		if (item != null && item.hasItemMeta()) {
			ItemMeta itemMeta = item.getItemMeta();
			List<String> lore = itemMeta.getLore();
			if (lore != null && lore.contains(enchant))
				return true;
		}
		return false;
	}
	
}
