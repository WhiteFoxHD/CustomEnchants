package org.raidwars.customenchants.armor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ArmorListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		boolean shift = false;
		boolean numberkey = false;
		if (e.isCancelled()) {
			return;
		}
		if (e.getClick().equals((Object) ClickType.SHIFT_LEFT) || e.getClick().equals((Object) ClickType.SHIFT_RIGHT)) {
			shift = true;
		}
		if (e.getClick().equals((Object) ClickType.NUMBER_KEY)) {
			numberkey = true;
		}
		if (e.getSlotType() != InventoryType.SlotType.ARMOR && e.getSlotType() != InventoryType.SlotType.QUICKBAR
				&& e.getSlotType() != InventoryType.SlotType.CONTAINER) {
			return;
		}
		if (e.getClickedInventory() != null
				&& !e.getClickedInventory().getType().equals((Object) InventoryType.PLAYER)) {
			return;
		}
		if (!e.getInventory().getType().equals((Object) InventoryType.CRAFTING)
				&& !e.getInventory().getType().equals((Object) InventoryType.PLAYER)) {
			return;
		}
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		if (e.getCurrentItem() == null) {
			return;
		}
		ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
		if (!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()) {
			return;
		}
		if (shift) {
			newArmorType = ArmorType.matchType(e.getCurrentItem());
			if (newArmorType != null) {
				boolean equipping = true;
				if (e.getRawSlot() == newArmorType.getSlot()) {
					equipping = false;
				}
				Label_0458: {
					if (newArmorType.equals(ArmorType.HELMET)) {
						if (equipping) {
							if (e.getWhoClicked().getInventory().getHelmet() == null) {
								break Label_0458;
							}
						} else if (e.getWhoClicked().getInventory().getHelmet() != null) {
							break Label_0458;
						}
					}
					if (newArmorType.equals(ArmorType.CHESTPLATE)) {
						if (equipping) {
							if (e.getWhoClicked().getInventory().getChestplate() == null) {
								break Label_0458;
							}
						} else if (e.getWhoClicked().getInventory().getChestplate() != null) {
							break Label_0458;
						}
					}
					if (newArmorType.equals(ArmorType.LEGGINGS)) {
						if (equipping) {
							if (e.getWhoClicked().getInventory().getLeggings() == null) {
								break Label_0458;
							}
						} else if (e.getWhoClicked().getInventory().getLeggings() != null) {
							break Label_0458;
						}
					}
					if (!newArmorType.equals(ArmorType.BOOTS)) {
						return;
					}
					if (equipping) {
						if (e.getWhoClicked().getInventory().getBoots() != null) {
							return;
						}
					} else if (e.getWhoClicked().getInventory().getBoots() == null) {
						return;
					}
				}
				ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(),
						ArmorEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(),
						equipping ? e.getCurrentItem() : null);
				Bukkit.getServer().getPluginManager().callEvent((Event) armorEquipEvent);
				if (armorEquipEvent.isCancelled()) {
					e.setCancelled(true);
				}
			}
		} else {
			ItemStack newArmorPiece = e.getCursor();
			ItemStack oldArmorPiece = e.getCurrentItem();
			if (numberkey) {
				if (e.getClickedInventory().getType().equals((Object) InventoryType.PLAYER)) {
					ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
					if (hotbarItem != null) {
						newArmorType = ArmorType.matchType(hotbarItem);
						newArmorPiece = hotbarItem;
						oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
					} else {
						newArmorType = ArmorType
								.matchType((e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR)
										? e.getCurrentItem() : e.getCursor());
					}
				}
			} else {
				newArmorType = ArmorType
						.matchType((e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR)
								? e.getCurrentItem() : e.getCursor());
			}
			if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
				ArmorEquipEvent.EquipMethod method = ArmorEquipEvent.EquipMethod.PICK_DROP;
				if (e.getAction().equals((Object) InventoryAction.HOTBAR_SWAP) || numberkey) {
					method = ArmorEquipEvent.EquipMethod.HOTBAR_SWAP;
				}
				ArmorEquipEvent armorEquipEvent2 = new ArmorEquipEvent((Player) e.getWhoClicked(), method, newArmorType,
						oldArmorPiece, newArmorPiece);
				Bukkit.getServer().getPluginManager().callEvent((Event) armorEquipEvent2);
				if (armorEquipEvent2.isCancelled()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL) {
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = e.getPlayer();
			if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock().getState() instanceof InventoryHolder) {
					return;
				}
			}
			ArmorType newArmorType = ArmorType.matchType(e.getItem());
			if (newArmorType != null && ((newArmorType.equals(ArmorType.HELMET)
					&& e.getPlayer().getInventory().getHelmet() == null)
					|| (newArmorType.equals(ArmorType.CHESTPLATE)
							&& e.getPlayer().getInventory().getChestplate() == null)
					|| (newArmorType.equals(ArmorType.LEGGINGS) && e.getPlayer().getInventory().getLeggings() == null)
					|| (newArmorType.equals(ArmorType.BOOTS) && e.getPlayer().getInventory().getBoots() == null))) {
				ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), ArmorEquipEvent.EquipMethod.HOTBAR,
						ArmorType.matchType(e.getItem()), null, e.getItem());
				Bukkit.getServer().getPluginManager().callEvent((Event) armorEquipEvent);
				if (armorEquipEvent.isCancelled()) {
					e.setCancelled(true);
					player.updateInventory();
				}
			}
		}
	}

	@EventHandler
	public void inventoryDrag(InventoryDragEvent event) {
		ArmorType type = ArmorType.matchType(event.getOldCursor());
		if (event.getRawSlots().isEmpty()) {
			return;
		}
		if (type != null && type.getSlot() == (int) event.getRawSlots().stream().findFirst().orElse(0)) {
			ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(),
					ArmorEquipEvent.EquipMethod.DRAG, type, null, event.getOldCursor());
			Bukkit.getServer().getPluginManager().callEvent((Event) armorEquipEvent);
			if (armorEquipEvent.isCancelled()) {
				event.setResult(Event.Result.DENY);
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void itemBreakEvent(PlayerItemBreakEvent e) {
		ArmorType type = ArmorType.matchType(e.getBrokenItem());
		if (type != null) {
			Player p = e.getPlayer();
			ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.BROKE, type,
					e.getBrokenItem(), null);
			Bukkit.getServer().getPluginManager().callEvent((Event) armorEquipEvent);
			if (armorEquipEvent.isCancelled()) {
				ItemStack i = e.getBrokenItem().clone();
				i.setAmount(1);
				i.setDurability((short) (i.getDurability() - 1));
				if (type.equals(ArmorType.HELMET)) {
					p.getInventory().setHelmet(i);
				} else if (type.equals(ArmorType.CHESTPLATE)) {
					p.getInventory().setChestplate(i);
				} else if (type.equals(ArmorType.LEGGINGS)) {
					p.getInventory().setLeggings(i);
				} else if (type.equals(ArmorType.BOOTS)) {
					p.getInventory().setBoots(i);
				}
			}
		}
	}

	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent e) {
		Player p = e.getEntity();
		ItemStack[] armorContents;
		for (int length = (armorContents = p.getInventory().getArmorContents()).length, j = 0; j < length; ++j) {
			ItemStack i = armorContents[j];
			if (i != null && !i.getType().equals((Object) Material.AIR)) {
				Bukkit.getServer().getPluginManager().callEvent((Event) new ArmorEquipEvent(p,
						ArmorEquipEvent.EquipMethod.DEATH, ArmorType.matchType(i), i, null));
			}
		}
	}
}
