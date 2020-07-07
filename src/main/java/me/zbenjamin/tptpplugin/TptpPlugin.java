package me.zbenjamin.tptpplugin;

import me.zbenjamin.tptpplugin.emotes.HappyCmd;
import me.zbenjamin.tptpplugin.emotes.SadCmd;
import me.zbenjamin.tptpplugin.files.WarpConfig;
import me.zbenjamin.tptpplugin.warpsystem.LobbyCmd;
import me.zbenjamin.tptpplugin.warpsystem.SetWarp;
import me.zbenjamin.tptpplugin.warpsystem.TpWarp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TptpPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Tptp Plugin is now booted up!");

        getServer().getPluginManager().registerEvents(this, this);

        getCommand("sad").setExecutor(new SadCmd());
        getCommand("szomoru").setExecutor(new SadCmd());
        getCommand("happy").setExecutor(new HappyCmd());
        getCommand("boldog").setExecutor(new HappyCmd());
        getCommand("tptpconfig").setExecutor(new ConfigCmd());
        getCommand("tptpset").setExecutor(new SetWarp());
        getCommand("tptp").setExecutor(new TpWarp());
        getCommand("lobby").setExecutor(new LobbyCmd());

        getConfig().addDefault("locale", "hu");
        getConfig().addDefault("tnt-allowed", false);
        getConfig().addDefault("customjoinmessage", true);
        getConfig().addDefault("joinmessage", "Má' megin' itt van ez a %%player%%");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        WarpConfig.setup();

        WarpConfig.get().addDefault("lobby.x", 0.00);
        WarpConfig.get().addDefault("lobby.y", 0.00);
        WarpConfig.get().addDefault("lobby.z", 0.00);
        WarpConfig.get().addDefault("lobby.p1x", 0);
        WarpConfig.get().addDefault("lobby.p1y", 0);
        WarpConfig.get().addDefault("lobby.p1z", 0);
        WarpConfig.get().addDefault("lobby.p2x", 0);
        WarpConfig.get().addDefault("lobby.p2y", 0);
        WarpConfig.get().addDefault("lobby.p2z", 0);
        WarpConfig.get().addDefault("lobby.protect", false);

        // PLUSZ ÖTLETEK KÉSŐBBRE:
        // - Google Translate Freemium API-ját használva fordító parancs
        // - Ránézve egy blokkra sendMessage-elje a koordinátáit
        // - Item renamer parancs

        WarpConfig.get().options().copyDefaults(true);
        WarpConfig.save();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Tptp Plugin is now shutted down.");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if (event.getBlock().getType() == Material.TNT){
            if (getConfig().getBoolean("tnt-allowed")){
                event.setCancelled(true);
                if (getConfig().getString("locale") == "hu") event.getPlayer()
                        .sendMessage(ChatColor.RED + "[Tptp Plugin] Nem tudsz TNT-t lehelyezni.");
                else event.getPlayer().sendMessage(ChatColor.RED + "[Tptp Plugin] You cannot place TNTs.");
            }
        }
        Location p1 = new Location(
                event.getPlayer().getWorld(),
                WarpConfig.get().getInt("lobby.p1x"),
                WarpConfig.get().getInt("lobby.p1y"),
                WarpConfig.get().getInt("lobby.p1z"));
        Location p2 = new Location(
                event.getPlayer().getWorld(),
                WarpConfig.get().getInt("lobby.p2x"),
                WarpConfig.get().getInt("lobby.p2y"),
                WarpConfig.get().getInt("lobby.p2z"));
        Location plo = event.getPlayer().getLocation();
        if (
                event.getPlayer().getWorld().getName().equalsIgnoreCase("flatworld")
                        && plo.getX() >= p1.getX() && plo.getY() >= p1.getY() && plo.getZ() >= p1.getZ()
                        && plo.getX() <= p2.getX() && plo.getY() <= p2.getY() && plo.getZ() <= p2.getZ()
        ){
            event.setCancelled(true);
            if (getConfig().getString("locale") == "hu") event.getPlayer()
                    .sendMessage(ChatColor.RED + "[Tptp Plugin] Nem tudod módosítani a lobbyt.");
            else event.getPlayer().sendMessage(ChatColor.RED + "[Tptp Plugin] You cannot edit the lobby.");
        }
    }
    public void OnBlockDestroy(BlockBreakEvent event){
        Location p1 = new Location(
                event.getPlayer().getWorld(),
                WarpConfig.get().getInt("lobby.p1x"),
                WarpConfig.get().getInt("lobby.p1y"),
                WarpConfig.get().getInt("lobby.p1z"));
        Location p2 = new Location(
                event.getPlayer().getWorld(),
                WarpConfig.get().getInt("lobby.p2x"),
                WarpConfig.get().getInt("lobby.p2y"),
                WarpConfig.get().getInt("lobby.p2z"));
        Location plo = event.getPlayer().getLocation();
        if (
                event.getPlayer().getWorld().getName().equalsIgnoreCase("flatworld")
                        && plo.getX() >= p1.getX() && plo.getY() >= p1.getY() && plo.getZ() >= p1.getZ()
                        && plo.getX() <= p2.getX() && plo.getY() <= p2.getY() && plo.getZ() <= p2.getZ()
        ){
            event.setCancelled(true);
            if (getConfig().getString("locale") == "hu") event.getPlayer()
                    .sendMessage(ChatColor.RED + "[Tptp Plugin] Nem tudod módosítani a lobbyt.");
            else event.getPlayer().sendMessage(ChatColor.RED + "[Tptp Plugin] You cannot edit the lobby.");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (getConfig().getBoolean("customjoinmessage")){
            e.setJoinMessage(ChatColor.GOLD + getConfig().getString("joinmessage")
                    .replace('_', ' ')
                    .replace("%%player%%", ChatColor.WHITE
                            + e.getPlayer().getDisplayName() + ChatColor.GOLD));
        }
    }
}