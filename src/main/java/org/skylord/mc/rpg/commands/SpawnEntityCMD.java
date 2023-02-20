package org.skylord.mc.rpg.commands;

import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.skylord.mc.rpg.Rpg;

public class SpawnEntityCMD extends AbstractCMD
{
    private Rpg plugin;
    public SpawnEntityCMD(Rpg plugin)
    {
        super("spawnentity");
        this.plugin = plugin;
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            String msg = plugin.getConfig().getString("messages.playeronly");
            msg = msg.replace("&", "\u00a7");
            sender.sendMessage(msg);
            plugin.saveConfig();
            return;
        }
        Player p = (Player) sender;
        Creeper creeper = (Creeper) p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
        creeper.setPowered(true);
        creeper.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(999999, 1));
    }
}
