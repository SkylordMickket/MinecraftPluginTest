package org.skylord.mc.rpg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.skylord.mc.rpg.Rpg;

import java.io.File;

import static org.skylord.mc.rpg.Leveling.getXpToLevelUp;

public class LevelingInfoCMD extends AbstractCMD
{
    public LevelingInfoCMD()
    {
        super("leveling");
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args)
    {
        if(!(sender instanceof Player))
        {
            String msg = Rpg.getInstance().getConfig().getString("messages.playeronly");
            msg = msg.replace("&", "\u00a7");
            sender.sendMessage(msg);
            return;
        }
        Player p = (Player) sender;
        String filePath = Rpg.getInstance().getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        int level = config.getInt(p.getUniqueId() + ".Level");
        int xp = config.getInt(p.getUniqueId() + ".Exp");
        p.sendMessage(ChatColor.DARK_GREEN + "Level: " + level);
        p.sendMessage(ChatColor.DARK_GREEN + "XP: " + xp + "/" + getXpToLevelUp(level));
    }
}
