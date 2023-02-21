package org.skylord.mc.rpg;

import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.skylord.mc.rpg.commands.*;

import java.io.File;
import java.util.*;

public final class Rpg extends JavaPlugin implements Listener
{
    public Map<Player, Inventory> holders = new HashMap<>();
    private static Rpg instance;
    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new Handler(this), this);
        new SpawnEntityCMD(this);
        new MenuCMD(this);
        new ClassCMD(this);
        new LoadCharacterCMD();
        new EchoCMD();
        new LevelingInfoCMD();
    }
    @Override
    public void onDisable()
    {

    }
    public static Rpg getInstance() { return instance; }
    public void SetClass(Player p, int cl, int maxHealth)
    {
        String filePath = getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set(p.getUniqueId() + ".Class", Integer.valueOf(cl));
        config.set(p.getUniqueId() + ".Health", Integer.valueOf(maxHealth));
        config.set(p.getUniqueId() + ".Exp", Integer.valueOf(0));
        config.set(p.getUniqueId() + ".Level", Integer.valueOf(1));
        config.set(p.getUniqueId() + ".Spawn.x", Double.valueOf(190));
        config.set(p.getUniqueId() + ".Spawn.y", Double.valueOf(71));
        config.set(p.getUniqueId() + ".Spawn.z", Double.valueOf(-120));
        config.set(p.getUniqueId() + ".Spawn.yaw", Double.valueOf(180));
        config.set(p.getUniqueId() + ".Spawn.pitch", Double.valueOf(0));
        try { config.save(f); }
        catch (Exception exc) { exc.printStackTrace(); }
        p.setMaxHealth(config.getInt(p.getUniqueId() + ".Health"));
        p.setHealth(p.getMaxHealth());
        p.teleport(GetSpawnLocation(p));
    }
    public Location GetSpawnLocation(Player p)
    {
        String filePath = getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        double x = config.getDouble(p.getUniqueId() + ".Spawn.x");
        double y = config.getDouble(p.getUniqueId() + ".Spawn.y");
        double z = config.getDouble(p.getUniqueId() + ".Spawn.z");
        float yaw = (float) config.getDouble(p.getUniqueId() + ".Spawn.yaw");
        float pitch = (float) config.getDouble(p.getUniqueId() + ".Spawn.pitch");
        Location location = new Location(Bukkit.getServer().getWorlds().get(0), x, y, z, yaw, pitch);
        return location;
    }
    public void LoadConfig(Player p)
    {
        String filePath = getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        p.setMaxHealth(config.getInt(p.getUniqueId() + ".Health"));
    }
}
