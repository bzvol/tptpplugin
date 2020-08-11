package me.zbenjamin.tptpplugin.warpsystem;

import me.zbenjamin.tptpplugin.Methods;
import me.zbenjamin.tptpplugin.enums.BroadcastType;
import me.zbenjamin.tptpplugin.enums.MessageType;
import me.zbenjamin.tptpplugin.files.WarpConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TpRemove implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            if (args.length != 1) return false;
            if (WarpConfig.get().contains("warps." + args[0])){
                WarpConfig.get().set("warps." + args[0], null);

                Methods.langBasedMessage(ChatColor.BLUE + args[0] + ChatColor.RESET + " pont kitörölve!",
                                        "Point " + ChatColor.BLUE + args[0] + ChatColor.RESET + " has been removed!",
                        BroadcastType.Sender, MessageType.Info, sender);

                WarpConfig.save();
                WarpConfig.reload();

                return true;
            }
            else if (WarpConfig.get().contains("playerwarps." + ((Player) sender).getUniqueId().toString() + "." + args[0])) {
                WarpConfig.get().set("playerwarps" + ((Player) sender).getUniqueId().toString() + "." + args[0], null);

                Methods.langBasedMessage(ChatColor.DARK_AQUA + args[0] + ChatColor.RESET + " saját pont kitörölve!",
                        "Your own point, " + ChatColor.DARK_AQUA + args[0] + ChatColor.RESET + " has been removed!",
                        BroadcastType.Sender, MessageType.Info, sender);

                WarpConfig.save();
                WarpConfig.reload();

                return true;
            }
            else{
                Methods.langBasedMessage("Ilyen pont nem létezik!",
                        "This point does not exist!",
                        BroadcastType.Sender, MessageType.Error, sender);

                return true;
            }
        }
        else{
            if (args.length != 1) return false;
            if (WarpConfig.get().contains("warps." + args[0])){
                WarpConfig.get().set("warps." + args[0], null);

                Methods.langBasedMessage(ChatColor.BLUE + args[0] + ChatColor.RESET + " pont kitörölve!",
                        "Point " + ChatColor.BLUE + args[0] + ChatColor.RESET + " has been removed!",
                        BroadcastType.Sender, MessageType.Info, sender);

                WarpConfig.save();
                WarpConfig.reload();

                return true;
            }
            else{
                Methods.langBasedMessage("Ilyen pont nem létezik!",
                        "This point does not exist!",
                        BroadcastType.Sender, MessageType.Error, sender);
                return true;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            Set<String> warps = null;
            Set<String> warps2 = null;

            try { warps = Objects.requireNonNull(WarpConfig.get().getConfigurationSection("warps")).getKeys(false); }
            catch (NullPointerException ignored){}

            try { warps2 = Objects.requireNonNull(Objects.requireNonNull(WarpConfig.get().getConfigurationSection("playerwarps." + ((Player) sender).getUniqueId().toString())).getKeys(false)); }
            catch (NullPointerException ignored){}

            if (warps2 != null) Objects.requireNonNull(warps).addAll(warps2);
            if (warps != null) return new ArrayList<>(warps);
        }
        return null;
    }
}
