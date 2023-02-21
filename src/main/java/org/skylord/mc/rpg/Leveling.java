package org.skylord.mc.rpg;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Leveling
{
    private Player p;

    private int xp = 0;
    private int level = 1;
    public Leveling(Player p)
    {
        this.p = p;
        String filePath = Rpg.getInstance().getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        xp = config.getInt(p.getUniqueId() + ".Exp");
        level = config.getInt(p.getUniqueId() + ".Level");
    }
    public int getXp()
    {
        return xp;
    }
    public int getLevel()
    {
        return level;
    }
    public void addXp(int xpToAdd)
    {
        String filePath = Rpg.getInstance().getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        xp += xpToAdd;
        int needToLevel = getXpToLevelUp(level);
        while(xp >= needToLevel)
        {
            level += 1;
            xp -= needToLevel;
            p.sendMessage(ChatColor.GOLD + "Level up! New level: " + level);
            config.set(p.getUniqueId() + ".Level", Integer.valueOf(level));
        }
        config.set(p.getUniqueId() + ".Exp", Integer.valueOf(xp));
        try { config.save(f); }
        catch (Exception exc) { exc.printStackTrace(); }
    }
    public static final int getXpToLevelUp(int level)
    {
        return 500 + level * 100;
    }
}
