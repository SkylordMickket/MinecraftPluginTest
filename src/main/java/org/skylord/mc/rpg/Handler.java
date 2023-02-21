package org.skylord.mc.rpg;

import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
    public void OnJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        String filePath = plugin.getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        if(!p.hasPlayedBefore())
        {
            config.set(p.getUniqueId() + ".Class", Integer.valueOf(0));
            config.set(p.getUniqueId() + ".Spawn.x", Double.valueOf(164));
            config.set(p.getUniqueId() + ".Spawn.y", Double.valueOf(74));
            config.set(p.getUniqueId() + ".Spawn.z", Double.valueOf(-138));
            config.set(p.getUniqueId() + ".Spawn.yaw", Double.valueOf(-90));
            config.set(p.getUniqueId() + ".Spawn.pitch", Double.valueOf(0));
        }
        if(config.getInt(p.getUniqueId() + ".Class") == 0)
        {
            p.sendMessage(ChatColor.BLUE + "Hello there!");
            p.sendMessage(ChatColor.BLUE + "Take a look around here!");
            p.sendMessage(ChatColor.BLUE + "And then choice your class! (Command /class)");
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
        double x = config.getDouble(p.getUniqueId() + ".Spawn.x");
        double y = config.getDouble(p.getUniqueId() + ".Spawn.y");
        double z = config.getDouble(p.getUniqueId() + ".Spawn.z");
        float yaw = (float) config.getDouble(p.getUniqueId() + ".Spawn.yaw");
        float pitch = (float) config.getDouble(p.getUniqueId() + ".Spawn.pitch");
        Location location = new Location(Bukkit.getServer().getWorlds().get(0), x, y, z, yaw, pitch);
        event.setRespawnLocation(location);
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
            double x = config.getDouble(p.getUniqueId() + ".Spawn.x");
            double y = config.getDouble(p.getUniqueId() + ".Spawn.y");
            double z = config.getDouble(p.getUniqueId() + ".Spawn.z");
            float yaw = (float) config.getDouble(p.getUniqueId() + ".Spawn.yaw");
            float pitch = (float) config.getDouble(p.getUniqueId() + ".Spawn.pitch");
            Location location = new Location(Bukkit.getServer().getWorlds().get(0), x, y, z, yaw, pitch);
            event.setSpawnLocation(location);
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
                SetClass(p, 1, 16);
                p.sendMessage(ChatColor.GREEN + "You picked the Knight class!");
            }
            if(inv.equals(clickedInv) && event.getCurrentItem().getType() == Material.BLAZE_ROD)
            {
                SetClass(p, 2, 10);
                p.sendMessage(ChatColor.GREEN + "You picked the Mage class!");
            }
            if(inv.equals(clickedInv) && event.getCurrentItem().getType() == Material.NETHERITE_AXE)
            {
                SetClass(p, 3, 20);
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

    public void SetClass(Player p, int cl, int maxHealth)
    {
        String filePath = plugin.getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set(p.getUniqueId() + ".Class", Integer.valueOf(cl));
        config.set(p.getUniqueId() + ".Health", Integer.valueOf(maxHealth));
        config.set(p.getUniqueId() + ".Spawn.x", Double.valueOf(190));
        config.set(p.getUniqueId() + ".Spawn.y", Double.valueOf(71));
        config.set(p.getUniqueId() + ".Spawn.z", Double.valueOf(-120));
        config.set(p.getUniqueId() + ".Spawn.yaw", Double.valueOf(180));
        config.set(p.getUniqueId() + ".Spawn.pitch", Double.valueOf(0));
        try { config.save(f); }
        catch (Exception exc) { exc.printStackTrace(); }
        p.setMaxHealth(config.getInt(p.getUniqueId() + ".Health"));
        p.setHealth(p.getMaxHealth());
        double x = config.getDouble(p.getUniqueId() + ".Spawn.x");
        double y = config.getDouble(p.getUniqueId() + ".Spawn.y");
        double z = config.getDouble(p.getUniqueId() + ".Spawn.z");
        float yaw = (float) config.getDouble(p.getUniqueId() + ".Spawn.yaw");
        float pitch = (float) config.getDouble(p.getUniqueId() + ".Spawn.pitch");
        Location location = new Location(Bukkit.getServer().getWorlds().get(0), x, y, z, yaw, pitch);
        p.teleport(location);
    }
}
