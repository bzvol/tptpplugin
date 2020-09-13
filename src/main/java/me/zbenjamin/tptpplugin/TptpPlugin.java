package me.zbenjamin.tptpplugin;

import me.zbenjamin.tptpplugin.emotes.HappyCmd;
import me.zbenjamin.tptpplugin.emotes.SadCmd;
import me.zbenjamin.tptpplugin.enums.BroadcastType;
import me.zbenjamin.tptpplugin.enums.MessageType;
import me.zbenjamin.tptpplugin.files.WarpConfig;
import me.zbenjamin.tptpplugin.warpsystem.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

// Feladatok
// - Lobby protection kijavítása
// - Yaw, pitch beépítése a warpokhoz, lobbyhoz
// - Methods.java -> javadoc készítése

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
        Objects.requireNonNull(getCommand("tptpremove")).setExecutor(new TpRemove());
        Objects.requireNonNull(getCommand("tptplist")).setExecutor(new TpList());

        Objects.requireNonNull(getCommand("lobby")).setExecutor(new LobbyCmd());

        Objects.requireNonNull(getCommand("rename")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("blockcoords")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("getcoords")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("heal")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("feed")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("invulnerable")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("gcmd")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("gbarr")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("killitems")).setExecutor(new OtherCommands());

        getConfig().addDefault("locale", "hu");
        getConfig().addDefault("block-explodes-allowed", false);
        getConfig().addDefault("entity-explodes-allowed", true);
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
    public void onBlockExplode(BlockExplodeEvent e){
        if (!getConfig().getBoolean("block-explodes-allowed")){
            e.setCancelled(true);
            Methods.langBasedMessage(
                    "A robbantások nem engedélyezettek a szerveren.",
                    "Explodes are not allowed on this server.",
                    BroadcastType.Server, MessageType.Error
            );
        }
    }
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e){
        if (!getConfig().getBoolean("entity-explodes-allowed")){
            e.setCancelled(true);
            String entityName = e.getEntityType().toString().toLowerCase();
            Methods.langBasedMessage(
                    "Egy " + ChatColor.RED + entityName + ChatColor.RESET + " megpróbált felrobbani, de nem sikerült neki, ugyanis a robbanások nem engedélyezettek a szerveren.",
                    "A " + ChatColor.RED + entityName + ChatColor.RESET + " has tried to explode, but it couldn't, because explodes are not allowed on this server.",
                    BroadcastType.Server, MessageType.Error
            );
        }
    }
}
