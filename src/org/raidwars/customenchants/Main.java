package org.raidwars.customenchants;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.raidwars.customenchants.enchants.EnchantType;

public class Main extends JavaPlugin {

	private static Main instance;

	public void onEnable() {
		(Main.instance = this).saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new MainListener(this), this);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("customenchant") && sender instanceof Player) {
			Inventory gui = Bukkit.createInventory(null, 27, colorize(getConfig().getString("enchantment-shop")));
			addItems(gui);
			for (int i = 0; i < 27; i++) {
				if (gui.getItem(i) == null)
					gui.setItem(i, getBlankItem());
			}
			Player p = (Player) sender;
			p.openInventory(gui);
		}
		return true;
	}

	public void addItems(Inventory gui) {
		gui.setItem(10, ItemUtils.getShowCase(EnchantType.SPEED));
		gui.setItem(11, ItemUtils.getShowCase(EnchantType.STRENGTH));
		gui.setItem(12, ItemUtils.getShowCase(EnchantType.FIRE_RESISTANCE));
		gui.setItem(13, ItemUtils.getShowCase(EnchantType.HEALTH_BOOST));
		gui.setItem(14, ItemUtils.getShowCase(EnchantType.REGENERATION));
		gui.setItem(15, ItemUtils.getShowCase(EnchantType.WATER_BREATHING));
		gui.setItem(16, ItemUtils.getShowCase(EnchantType.SATURATION));
	}
	
	private ItemStack getBlankItem() {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public String colorize(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static Main getInstance() {
		return instance;
	}

}
