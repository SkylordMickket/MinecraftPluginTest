package org.skylord.mc.rpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;
import org.skylord.mc.rpg.Rpg;

public abstract class AbstractCMD implements CommandExecutor
{
    public AbstractCMD(String command)
    {
        PluginCommand pluginCommand = Rpg.getInstance().getCommand(command);
        if(pluginCommand != null)
        {
            pluginCommand.setExecutor(this);
        }
    }
    public abstract void execute(CommandSender sender, String label, String[] args);
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        execute(sender, label, args);
        return true;
    }
}
