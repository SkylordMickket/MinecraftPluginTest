package org.skylord.mc.rpg.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.skylord.mc.rpg.Rpg;

public class SpawnEntityCMD extends AbstractCMD
{
    public SpawnEntityCMD(Rpg plugin)
    {
        super("spawnentity");
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args)
    {
        if (!(sender instanceof Player)) {
            String msg = Rpg.getInstance().getConfig().getString("messages.playeronly");
            msg = msg.replace("&", "\u00a7");
            sender.sendMessage(msg);
            Rpg.getInstance().saveConfig();
            return;
        }
        Player p = (Player) sender;
        Creeper creeper = (Creeper) p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
        creeper.setPowered(true);
        creeper.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(999999, 1));
    }
}
