package me.zbenjamin.tptpplugin;

import me.zbenjamin.tptpplugin.emotes.HappyCmd;
import me.zbenjamin.tptpplugin.emotes.SadCmd;
import me.zbenjamin.tptpplugin.files.WarpConfig;
import me.zbenjamin.tptpplugin.warpsystem.LobbyCmd;
import me.zbenjamin.tptpplugin.warpsystem.SetWarp;
import me.zbenjamin.tptpplugin.warpsystem.TpSign;
import me.zbenjamin.tptpplugin.warpsystem.TpWarp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

// Feladatok
// - Lobby protection kijavítása
// - LobbyCmd classban a TabCompletion rész javítása --> xyz koordináták rövidítése 2 tizedre

public final class TptpPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Tptp Plugin is now booted up!");

        getServer().getPluginManager().registerEvents(this, this);

        Objects.requireNonNull(getCommand("sad")).setExecutor(new SadCmd());
        Objects.requireNonNull(getCommand("happy")).setExecutor(new HappyCmd());

        Objects.requireNonNull(getCommand("tptpconfig")).setExecutor(new ConfigCmd());
        Objects.requireNonNull(getCommand("tptpset")).setExecutor(new SetWarp());
        Objects.requireNonNull(getCommand("tptp")).setExecutor(new TpWarp());
        Objects.requireNonNull(getCommand("tptpsign")).setExecutor(new TpSign());

        Objects.requireNonNull(getCommand("lobby")).setExecutor(new LobbyCmd());

        Objects.requireNonNull(getCommand("rename")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("blockcoord")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("getcoord")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("heal")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("feed")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("invulnerable")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("gcmd")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("gbarr")).setExecutor(new OtherCommands());

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

        WarpConfig.get().options().copyDefaults(true);
        WarpConfig.save();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Tptp Plugin is now shutted down.");
    }

    @EventHandler
    public void onPlayerClickedBlock(PlayerInteractEvent e){
        if (
                e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && Objects.requireNonNull(e.getClickedBlock()).getState() instanceof Sign
                && ((Sign) e.getClickedBlock().getState()).getLine(0).equals("§f[§1Tptp§f]")
        ) {
            Sign state = (Sign) e.getClickedBlock().getState();
            String tpname = state.getLine(3).substring(3, state.getLine(3).length() - 1);
            //e.getPlayer().sendMessage(tpname);
            double x = WarpConfig.get().getDouble("warps." + tpname + ".x"),
                    y = WarpConfig.get().getDouble("warps." + tpname + ".y"),
                    z = WarpConfig.get().getDouble("warps." + tpname + ".z");
            e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), x, y, z));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (getConfig().getBoolean("customjoinmessage")){
            e.setJoinMessage(ChatColor.GOLD + Objects.requireNonNull(getConfig().getString("joinmessage"))
                    .replace('_', ' ')
                    .replace("%%player%%", ChatColor.WHITE
                            + e.getPlayer().getDisplayName() + ChatColor.GOLD));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if (e.getBlock().getType() == Material.TNT){
            if (!getConfig().getBoolean("tnt-allowed")){
                e.setCancelled(true);
                if (Methods.getLocaleHu()) e.getPlayer()
                        .sendMessage(ChatColor.RED + "[Tptp Plugin] Nem tudsz TNT-t lehelyezni.");
                else e.getPlayer().sendMessage(ChatColor.RED + "[Tptp Plugin] You cannot place TNTs.");
            }
        }/*
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
            if (Methods.getLocaleHu()) event.getPlayer()
                    .sendMessage(ChatColor.RED + "[Tptp Plugin] Nem tudod módosítani a lobbyt.");
            else event.getPlayer().sendMessage(ChatColor.RED + "[Tptp Plugin] You cannot edit the lobby.");
        }
        if (WarpConfig.get().getBoolean("lobby.protect")){
            Location p1 = new Location(
                    e.getPlayer().getWorld(),
                    WarpConfig.get().getInt("lobby.p1x"),
                    WarpConfig.get().getInt("lobby.p1y"),
                    WarpConfig.get().getInt("lobby.p1z"));
            Location p2 = new Location(
                    e.getPlayer().getWorld(),
                    WarpConfig.get().getInt("lobby.p2x"),
                    WarpConfig.get().getInt("lobby.p2y"),
                    WarpConfig.get().getInt("lobby.p2z"));
            Location plo = Methods.getTargetBlock(e.getPlayer()).getLocation();
            if (
                    e.getPlayer().getWorld().getName().equalsIgnoreCase("flatworld")
                            && plo.getX() >= p1.getX() && plo.getY() >= p1.getY() && plo.getZ() >= p1.getZ()
                            && plo.getX() <= p2.getX() && plo.getY() <= p2.getY() && plo.getZ() <= p2.getZ()
            )
        }*/
    }
/*
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event){
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
            if (Methods.getLocaleHu()) event.getPlayer()
                    .sendMessage(ChatColor.RED + "[Tptp Plugin] Nem tudod módosítani a lobbyt.");
            else event.getPlayer().sendMessage(ChatColor.RED + "[Tptp Plugin] You cannot edit the lobby.");
        }
    }*/
}
