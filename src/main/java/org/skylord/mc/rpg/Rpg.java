package org.skylord.mc.rpg;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.skylord.mc.rpg.commands.ClassCMD;
import org.skylord.mc.rpg.commands.EchoCMD;
import org.skylord.mc.rpg.commands.MenuCMD;
import org.skylord.mc.rpg.commands.SpawnEntityCMD;

import java.io.File;
import java.sql.Array;
import java.util.*;

public final class Rpg extends JavaPlugin implements Listener
{
    public Map<Player, Inventory> holders = new HashMap<>();
    private static Rpg instance;
    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new Handler(this), this);
        new SpawnEntityCMD(this);
        new MenuCMD(this);
        new ClassCMD(this);
        new EchoCMD();
    }
    @Override
    public void onDisable()
    {

    }
    public static Rpg getInstance() { return instance; }
}
