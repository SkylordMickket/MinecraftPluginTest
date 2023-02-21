package org.skylord.mc.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.skylord.mc.rpg.Rpg;

public class LoadCharacterCMD extends AbstractCMD
{
    private Rpg plugin;
    public LoadCharacterCMD()
    {
        super("loadcharacter");
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args)
    {
        if(args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "/loadcharacter <user>");
            return;
        }
        String playerName = args[0];
        Player p = Bukkit.getPlayer(playerName);
        if(p == null)
        {
            sender.sendMessage(ChatColor.RED + "Player must be online!");
            return;
        }
        plugin.LoadConfig(p);
        sender.sendMessage(ChatColor.GREEN + "Config has successfully loaded!");
    }
}
