package me.zbenjamin.tptpplugin.warpsystem;

import me.zbenjamin.tptpplugin.Methods;
import me.zbenjamin.tptpplugin.enums.BroadcastType;
import me.zbenjamin.tptpplugin.enums.MessageType;
import me.zbenjamin.tptpplugin.files.WarpConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

public class TpList implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Set<String> global = null, self = null;

            try { global = Objects.requireNonNull(WarpConfig.get().getConfigurationSection("warps")).getKeys(false); }
            catch (NullPointerException ignored) {}

            try { self = Objects.requireNonNull(WarpConfig.get().getConfigurationSection("playerwarps." + ((Player) sender).getUniqueId())).getKeys(false); }
            catch (NullPointerException ignored) {}

            if (global != null) Methods.langBasedMessage(
                    "Tptp-k: " + ChatColor.BLUE + String.join(", ", global),
                    "Tptps: " + ChatColor.BLUE + String.join(", ", global),
                    BroadcastType.Sender, MessageType.Info, sender
            );
            else Methods.langBasedMessage(
                    "Még nem létezik egy tptp sem.",
                    "There are no tptps yet.",
                    BroadcastType.Sender, MessageType.Warning, sender
            );
            if (self != null) Methods.langBasedMessage(
                    "Saját tptp-k: " + ChatColor.DARK_AQUA + String.join(", ", self),
                    "Self tptps: " + ChatColor.DARK_AQUA + String.join(", ", self),
                    BroadcastType.Sender, MessageType.Info, sender
            );
            else Methods.langBasedMessage(
                    "Még nincsenek saját tptp-jeid.",
                    "You don't have any tptps yet.",
                    BroadcastType.Sender, MessageType.Warning, sender
            );
        }
        else {
            Set<String> global = null;

            try { global = Objects.requireNonNull(WarpConfig.get().getConfigurationSection("warps")).getKeys(false); }
            catch (NullPointerException ignored) {}

            if (global != null) Methods.langBasedMessage(
                    "Tptp-k: " + ChatColor.BLUE + String.join(", ", global),
                    "Tptps: " + ChatColor.BLUE + String.join(", ", global),
                    BroadcastType.Sender, MessageType.Info, sender
            );
            else Methods.langBasedMessage(
                    "Még nem létezik egy tptp sem.",
                    "There are no tptps yet.",
                    BroadcastType.Sender, MessageType.Warning, sender
            );
        }
        return true;
    }
}
