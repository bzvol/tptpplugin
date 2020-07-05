package me.zbenjamin.tptpplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TptpPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Tptp Plugin is now booted up!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Tptp Plugin is now shutted down.");
    }

    @EventHandler
    public void onTNTPlace(BlockPlaceEvent event){
        if (event.getBlock().getType() == Material.TNT){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.WHITE + "Nem tudsz TNT-t lehelyezni, " + ChatColor.RED
                    + "mert a Tptp Plugin ezt megakadályozza! :)");
        }
    }
}
