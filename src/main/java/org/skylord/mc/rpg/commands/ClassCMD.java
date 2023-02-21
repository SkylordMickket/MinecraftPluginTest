package org.skylord.mc.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.skylord.mc.rpg.Rpg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassCMD extends AbstractCMD
{
    private Rpg plugin;
    public ClassCMD(Rpg plugin)
    {
        super("class");
        this.plugin = plugin;
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
        String filePath = plugin.getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml";
        File f = new File(filePath);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        if(config.getInt(p.getUniqueId() + ".Class") != 0)
        {
            p.sendMessage(ChatColor.RED + "You have already chosen a class!");
            return;
        }
        Inventory inv = plugin.holders.get(p);
        if (inv == null)
        {
            inv = Bukkit.createInventory(null, 9, "Choice class");
            plugin.holders.put(p, inv);
        }
        ItemStack warrior = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta warriorMeta = warrior.getItemMeta();
        warriorMeta.setDisplayName("Воин");
        List<String> warriorLore = new ArrayList<>();
        warriorLore.add("Жестокий воин, мастерски владеющий топором.");
        warriorMeta.setLore(warriorLore);
        warriorMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        warrior.setItemMeta(warriorMeta);

        ItemStack mage = new ItemStack(Material.BLAZE_ROD);
        ItemMeta mageMeta = mage.getItemMeta();
        mageMeta.setDisplayName("Маг");
        List<String> mageLore = new ArrayList<>();
        mageLore.add("Мудрый маг, в соверщенстве владеющий тысячами древних заклинаний.");
        mageMeta.setLore(mageLore);
        mageMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        mage.setItemMeta(mageMeta);

        ItemStack knight = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta knightMeta = knight.getItemMeta();
        knightMeta.setDisplayName("Рыцарь");
        List<String> knightLore = new ArrayList<>();
        knightLore.add("Гордый рыцарь, способный в одиночку расправиться с толпами врагов.");
        knightMeta.setLore(knightLore);
        knightMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        knight.setItemMeta(knightMeta);

        inv.setItem(3, knight);
        inv.setItem(4, mage);
        inv.setItem(5, warrior);
        p.openInventory(inv);
    }
}
