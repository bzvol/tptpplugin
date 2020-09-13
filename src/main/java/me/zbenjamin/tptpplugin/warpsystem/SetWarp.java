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

import java.text.DecimalFormat;
import java.util.*;

public class SetWarp implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            if(args.length == 0) return false;
            else if (args.length == 1){
                Player p = (Player) sender;
                double x = p.getLocation().getX(), y = p.getLocation().getY(), z = p.getLocation().getZ();
                WarpConfig.get().set("warps." + args[0] + ".x", x);
                WarpConfig.get().set("warps." + args[0] + ".y", y);
                WarpConfig.get().set("warps." + args[0] + ".z", z);
                WarpConfig.save();
                WarpConfig.reload();

                Methods.langBasedMessage(
                        ChatColor.BLUE + args[0] + ChatColor.WHITE + " pont sikeresen beállítva!",
                        ChatColor.BLUE + args[0] + ChatColor.WHITE + " point has been set up successfully!",
                        BroadcastType.Sender, MessageType.Info, sender
                );

                return true;
            }
            else if (args.length == 4){
                double x = Double.parseDouble(args[1]), y = Double.parseDouble(args[2]), z = Double.parseDouble(args[3]);
                WarpConfig.get().set("warps." + args[0] + ".x", x);
                WarpConfig.get().set("warps." + args[0] + ".y", y);
                WarpConfig.get().set("warps." + args[0] + ".z", z);
                WarpConfig.save();
                WarpConfig.reload();

                Methods.langBasedMessage(
                        ChatColor.BLUE + args[0] + ChatColor.WHITE + " pont sikeresen beállítva!",
                        ChatColor.BLUE + args[0] + ChatColor.WHITE + " point has been set up successfully!",
                        BroadcastType.Sender, MessageType.Info, sender
                );

                return true;
            }
            else if (args.length == 2 && args[1].equals("self")){
                Player p = (Player) sender;
                double x = p.getLocation().getX(), y = p.getLocation().getY(), z = p.getLocation().getZ();
                WarpConfig.get().set("playerwarps." + ((Player) sender).getUniqueId().toString() + "." + args[0] + ".x", x);
                WarpConfig.get().set("playerwarps." + ((Player) sender).getUniqueId().toString() + "." + args[0] + ".y", y);
                WarpConfig.get().set("playerwarps." + ((Player) sender).getUniqueId().toString() + "." + args[0] + ".z", z);
                WarpConfig.save();
                WarpConfig.reload();

                Methods.langBasedMessage(
                        ChatColor.DARK_AQUA + args[0] + ChatColor.WHITE + " saját pont sikeresen beállítva!",
                        ChatColor.DARK_AQUA + args[0] + ChatColor.WHITE + " own point has been set up successfully!",
                        BroadcastType.Sender, MessageType.Info, sender
                );

                return true;
            }
            else if (args.length == 5 && args[4].equals("self")){
                double x = Double.parseDouble(args[1]), y = Double.parseDouble(args[2]), z = Double.parseDouble(args[3]);
                WarpConfig.get().set("playerwarps." + ((Player) sender).getUniqueId().toString() + "." + args[0] + ".x", x);
                WarpConfig.get().set("playerwarps." + ((Player) sender).getUniqueId().toString() + "." + args[0] + ".y", y);
                WarpConfig.get().set("playerwarps." + ((Player) sender).getUniqueId().toString() + "." + args[0] + ".z", z);
                WarpConfig.save();
                WarpConfig.reload();

                Methods.langBasedMessage(
                        ChatColor.DARK_AQUA + args[0] + ChatColor.WHITE + " saját pont sikeresen beállítva!",
                        ChatColor.DARK_AQUA + args[0] + ChatColor.WHITE + " own point has been set up successfully!",
                        BroadcastType.Sender, MessageType.Info, sender
                );

                return true;
            }
            else return false;
        }
        else{
            Methods.langBasedMessage(
                    "Csak játékosok használhatják ezt a parancsot.",
                    "Only players are allowed to use this command.",
                    BroadcastType.Sender, MessageType.Error, sender
            );
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        DecimalFormat decform = new DecimalFormat("#.##");
        if (args.length == 1) {
            Set<String> warps = null, warps2 = null;

            try { warps = Objects.requireNonNull(WarpConfig.get().getConfigurationSection("warps")).getKeys(false); }
            catch (NullPointerException ignored){}

            try { warps2 = Objects.requireNonNull(Objects.requireNonNull(WarpConfig.get().getConfigurationSection("playerwarps." + ((Player) sender).getUniqueId().toString())).getKeys(false)); }
            catch (NullPointerException ignored){}

            if (warps2 != null) Objects.requireNonNull(warps).addAll(warps2);
            if (warps != null) return new ArrayList<>(warps);
        }
        if (args.length == 2) return Arrays.asList(decform.format(((Player) sender).getLocation().getX()),"self");
        if (args.length == 3 && !args[1].equals("self")) return Collections.singletonList(decform.format(((Player) sender).getLocation().getY()));
        if (args.length == 4 && !args[1].equals("self")) return Collections.singletonList(decform.format(((Player) sender).getLocation().getZ()));
        if (args.length == 5 && !args[1].equals("self")) return Collections.singletonList("self");

        return null;
    }
}
