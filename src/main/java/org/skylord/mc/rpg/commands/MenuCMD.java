package org.skylord.mc.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.skylord.mc.rpg.Rpg;

public class MenuCMD extends AbstractCMD
{
    private Rpg plugin;
    public MenuCMD(Rpg plugin)
    {
        super("menu");
        this.plugin = plugin;
    }
    public void execute(CommandSender sender, String label, String[] args)
    {
        if(!(sender instanceof Player))
        {
            String msg = Rpg.getInstance().getConfig().getString("messages.playeronly");
            msg = msg.replace("&", "\u00a7");
            sender.sendMessage(msg);
            return;
        }
        Player p = (Player)sender;
        if(args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "Try /menu help");
        }
        else if(args[0].equalsIgnoreCase("help"))
        {
            sender.sendMessage("/menu open: opens menu");
        }
        else if(args[0].equalsIgnoreCase("open"))
        {
            Inventory inv = plugin.holders.get(p);
            if(inv == null)
            {
                inv = Bukkit.createInventory(null, 9, "Menu");
                plugin.holders.put(p, inv);
            }
            ItemStack item = new ItemStack(Material.BEDROCK);
            inv.setItem(4, item);
            p.openInventory(inv);
        }
    }
}
