package org.skylord.mc.rpg.commands;

import org.bukkit.command.CommandSender;

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
