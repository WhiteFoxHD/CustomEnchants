package org.raidwars.customenchants;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
		if (inv != null && e.getCursor() != null && e.getCurrentItem() != null) {
			ItemStack crystal = e.getCursor();
			ItemStack item = e.getCurrentItem();
			if (crystal.getType() == Material.NETHER_STAR && ItemUtils.isCrystal(crystal)) {
				EnchantType type = EnchantType.getEnchant(ItemUtils.getLore(crystal));
				if (type.getItems().contains(item.getType())) {
					e.setCancelled(true);
					e.setCurrentItem(ItemUtils.enchantItem(type, item));
				}
			}
		}
		if (e.getSlotType().equals(SlotType.ARMOR)) {
			if (e.getCursor() != null && EnchantType.getEnchant(ItemUtils.getLore(e.getCursor())) != null) {
				player.addPotionEffect(EnchantType.getEnchant(ItemUtils.getLore(e.getCursor())).getPotionEffect());
			}
			if (e.getCurrentItem() != null && EnchantType.getEnchant(ItemUtils.getLore(e.getCurrentItem())) != null) {
				player.removePotionEffect(EnchantType.getEnchant(ItemUtils.getLore(e.getCurrentItem())).getPotionEffect().getType());
			}
		}

	}

}
