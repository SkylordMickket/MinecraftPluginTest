package org.skylord.mc.rpg;

import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.io.File;
import java.util.*;

public class Handler implements Listener
{
    private Rpg plugin;
    private static final Map<UUID, Leveling> playerLeveling = new HashMap<UUID, Leveling>();
    public static final Leveling getPlayerLeveling(Player p)
    {
        Leveling result = playerLeveling.get(p.getUniqueId());
        if(result == null)
        {
            result = new Leveling(p);
            playerLeveling.put(p.getUniqueId(), result);
        }
        return result;
    }
    public Handler(Rpg plugin)
    {
        this.plugin = plugin;
    }
    @EventHandler
    public void OnEntityDeath(EntityDeathEvent event)
    {
        Entity killed = event.getEntity();
        Entity killer = event.getEntity().getKiller();
        if(killer == null) return;
        if(killer.getType().equals(EntityType.PLAYER))
        {
            Player p = (Player) killer;
            p.sendMessage("lol");
            if(killed.getType().equals(EntityType.ZOMBIE))
            {
                getPlayerLeveling(p).addXp(100);
            }
        }
    }
    @EventHandler
    public void OnJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        String filePath = plugin.getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        if(!p.hasPlayedBefore())
        {
            config.set(p.getUniqueId() + ".Class", Integer.valueOf(0));
            try { config.save(f); }
            catch (Exception exc) { exc.printStackTrace(); }
        }
        if(config.getInt(p.getUniqueId() + ".Class") == 0)
        {
            p.sendMessage(ChatColor.BLUE + "Hello there!");
            p.sendMessage(ChatColor.BLUE + "Take a look around here!");
            p.sendMessage(ChatColor.BLUE + "And then choice your class! (Command /class)");
        }
        else
        {
            plugin.LoadConfig(p);
        }
    }
    @EventHandler
    public void OnRespawn(PlayerRespawnEvent event)
    {
        Player p = event.getPlayer();
        String filePath = plugin.getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        if(config.getInt(p.getUniqueId() + ".Class") == 0) return;
        event.setRespawnLocation(plugin.GetSpawnLocation(p));
    }
    @EventHandler
    public void OnSpawn(PlayerSpawnLocationEvent event)
    {
        Player p = event.getPlayer();
        String filePath = plugin.getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        if(config.getInt(p.getUniqueId() + ".Class") == 0)
        {
            config.set(p.getUniqueId() + ".Spawn.x", Double.valueOf(164));
            config.set(p.getUniqueId() + ".Spawn.y", Double.valueOf(74));
            config.set(p.getUniqueId() + ".Spawn.z", Double.valueOf(-138));
            config.set(p.getUniqueId() + ".Spawn.yaw", Double.valueOf(-90));
            config.set(p.getUniqueId() + ".Spawn.pitch", Double.valueOf(0));
            try { config.save(f); }
            catch (Exception exc) { exc.printStackTrace(); }
            event.setSpawnLocation(plugin.GetSpawnLocation(p));
        }
    }
    @EventHandler
    public void OnInventoryClicked(InventoryClickEvent event)
    {
        Player p = (Player)event.getWhoClicked();
        Inventory inv = plugin.holders.get(p);
        Inventory clickedInv = event.getClickedInventory();
        if(inv == null) return;
        event.setCancelled(true);
        if(event.getView().getTitle().equalsIgnoreCase("Menu"))
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
        else if(event.getView().getTitle().equalsIgnoreCase("Choice class"))
        {
            if(inv.equals(clickedInv) && event.getCurrentItem().getType() == Material.NETHERITE_SWORD)
            {
                plugin.SetClass(p, 1, 16);
                p.sendMessage(ChatColor.GREEN + "You picked the Knight class!");
            }
            if(inv.equals(clickedInv) && event.getCurrentItem().getType() == Material.BLAZE_ROD)
            {
                plugin.SetClass(p, 2, 10);
                p.sendMessage(ChatColor.GREEN + "You picked the Mage class!");
            }
            if(inv.equals(clickedInv) && event.getCurrentItem().getType() == Material.NETHERITE_AXE)
            {
                plugin.SetClass(p, 3, 20);
                p.sendMessage(ChatColor.GREEN + "You picked the Warrior class!");
            }
            inv.close();
        }
    }
    @EventHandler
    public void OnInventoryClose(InventoryCloseEvent event)
    {
        plugin.holders.remove(event.getPlayer());
    }
}
