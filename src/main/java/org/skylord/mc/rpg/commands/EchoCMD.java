package org.skylord.mc.rpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.skylord.mc.rpg.Rpg;

public class EchoCMD extends AbstractCMD
{
    public EchoCMD()
    {
        super("echo");
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) return;
        String msg = "";
        for (String str : args) {
            msg += str + " ";
        }
        sender.sendMessage(msg);
    }
}
