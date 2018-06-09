package org.raidwars.customenchants.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.raidwars.customenchants.ItemUtils;
import org.raidwars.customenchants.Main;
import org.raidwars.customenchants.enchants.EnchantType;

public class InventoryClickListener implements Listener {

	private Main plugin;

	public InventoryClickListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getName().equals(plugin.colorize(plugin.getConfig().getString("enchantment-shop")))) {
			if (event.getInventory() == null) return;
			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getType() == Material.NETHER_STAR && event.getClickedInventory() != player.getInventory()) {
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
	
}
