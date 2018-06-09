package org.raidwars.customenchants.armor;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public final class ArmorEquipEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final EquipMethod equipType;
    private final ArmorType type;
    private ItemStack oldArmorPiece;
    private ItemStack newArmorPiece;
    
    static {
        handlers = new HandlerList();
    }
    
    public ArmorEquipEvent(final Player player, final EquipMethod equipType, final ArmorType type, final ItemStack oldArmorPiece, final ItemStack newArmorPiece) {
        super(player);
        this.cancel = false;
        this.equipType = equipType;
        this.type = type;
        this.oldArmorPiece = oldArmorPiece;
        this.newArmorPiece = newArmorPiece;
    }
    
    public static final HandlerList getHandlerList() {
        return ArmorEquipEvent.handlers;
    }
    
    public final HandlerList getHandlers() {
        return ArmorEquipEvent.handlers;
    }
    
    public final void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public final boolean isCancelled() {
        return this.cancel;
    }
    
    public final ArmorType getType() {
        return this.type;
    }
    
    public final ItemStack getOldArmorPiece() {
        return this.oldArmorPiece;
    }
    
    public final void setOldArmorPiece(final ItemStack oldArmorPiece) {
        this.oldArmorPiece = oldArmorPiece;
    }
    
    public final ItemStack getNewArmorPiece() {
        return this.newArmorPiece;
    }
    
    public final void setNewArmorPiece(final ItemStack newArmorPiece) {
        this.newArmorPiece = newArmorPiece;
    }
    
    public EquipMethod getMethod() {
        return this.equipType;
    }
    
    public enum EquipMethod
    {
        SHIFT_CLICK("SHIFT_CLICK", 0), 
        DRAG("DRAG", 1), 
        PICK_DROP("PICK_DROP", 2), 
        HOTBAR("HOTBAR", 3), 
        HOTBAR_SWAP("HOTBAR_SWAP", 4), 
        DISPENSER("DISPENSER", 5), 
        BROKE("BROKE", 6), 
        DEATH("DEATH", 7);
        
        private EquipMethod(final String s, final int n) {
        }
    }
}
