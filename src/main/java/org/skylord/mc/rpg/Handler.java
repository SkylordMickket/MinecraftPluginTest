package org.skylord.mc.rpg;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Handler implements Listener
{
    private Rpg plugin;
    public Handler(Rpg plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnInventoryClicked(InventoryClickEvent event)
    {
        Player p = (Player)event.getWhoClicked();
        Inventory inv = plugin.holders.get(p);
        Inventory clickedInv = event.getClickedInventory();
        if(inv == null) return;
        event.setCancelled(true);
        if(event.getView().getTitle() == "Menu")
        {
            if(inv.equals(clickedInv) && event.getCurrentItem().getType() == Material.BEDROCK)
            {
                ItemStack item = new ItemStack(Material.DIAMOND_BLOCK);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("Ключ от храма");
                itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
                itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString("usage"), PersistentDataType.INTEGER, 1);
                List<String> list = new ArrayList<>();
                list.add("Использований: " + itemMeta.getPersistentDataContainer().get(NamespacedKey.fromString("usage"), PersistentDataType.INTEGER));
                itemMeta.setLore(list);
                item.setItemMeta(itemMeta);
                p.sendMessage("YOU FOUND ME!");
                p.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 10, 1);
                p.getInventory().addItem(item);
            }
        }
    }
    @EventHandler
    public void OnInventoryClose(InventoryCloseEvent event)
    {
        plugin.holders.remove(event.getPlayer());
    }
}
