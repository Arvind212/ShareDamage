package me.arvind.ShareDamage;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
    private List<Player> playersToShareDamage = new ArrayList<>();

    @Override
    public void onEnable() {
        // Register the plugin's listener
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Unregister the plugin's listener
    	HandlerList.unregisterAll((Listener) this);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (playersToShareDamage.contains(player)) {
                event.setDamage(0); // Sets the damage to 0 for all shared players
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sharestart")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!playersToShareDamage.contains(player)) {
                    playersToShareDamage.add(player);
                    player.sendMessage("Shared damage is enabled!");
                } else {
                    player.sendMessage("You are already sharing damage!");
                }
            } else {
                sender.sendMessage("Only players can use this command!");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("sharestop")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (playersToShareDamage.remove(player)) {
                    player.sendMessage("Shared damage is disabled!");
                } else {
                    player.sendMessage("You were not sharing damage!");
                }
            } else {
                sender.sendMessage("Only players can use this command!");
            }
            return true;
        }
        return false;
    }
}
