package me.zbenjamin.tptpplugin.warpsystem;

import me.zbenjamin.tptpplugin.Methods;
import me.zbenjamin.tptpplugin.enums.BroadcastType;
import me.zbenjamin.tptpplugin.enums.MessageType;
import me.zbenjamin.tptpplugin.files.WarpConfig;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LobbyCmd implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            if (args.length == 0){
                double x = WarpConfig.get().getDouble("lobby.x"),
                        y = WarpConfig.get().getDouble("lobby.y"),
                        z = WarpConfig.get().getDouble("lobby.z");
                ((Player) sender).teleport(new Location(((Player) sender).getWorld(), x, y, z));
                return true;
            }
            else{
                if (args.length == 1 && args[0].equals("set")){
                    Player p = (Player) sender;
                    double x = p.getLocation().getX(), y = p.getLocation().getY(), z = p.getLocation().getZ();
                    WarpConfig.get().set("lobby.x", x);
                    WarpConfig.get().set("lobby.y", y);
                    WarpConfig.get().set("lobby.z", z);
                    Methods.langBasedMessage(
                            "A lobby sikeresen beállítva!",
                            "You have set the lobby successfully!",
                            BroadcastType.Sender, MessageType.Info, sender
                    );
                    return true;
                }
                else if (args.length == 4 && args[0].equals("set")){
                    double x = Double.parseDouble(args[1]), y = Double.parseDouble(args[2]), z = Double.parseDouble(args[3]);
                    WarpConfig.get().set("lobby.x", x);
                    WarpConfig.get().set("lobby.y", y);
                    WarpConfig.get().set("lobby.z", z);
                    Methods.langBasedMessage(
                            "A lobby sikeresen beállítva!",
                            "You have set the lobby successfully!",
                            BroadcastType.Sender, MessageType.Info, sender
                    );
                    return true;
                }
                else if (args[0].equals("reloadconfig")){
                    WarpConfig.save();
                    WarpConfig.reload();
                    Methods.langBasedMessage(
                            "A konfiguráció elmentve és újratöltve.",
                            "Config saved and reloaded.",
                            BroadcastType.Sender, MessageType.Info, sender
                    );
                    return true;
                }
                else if (args[0].equals("setprotect") && (args[1].equals("p1") || args[1].equals("p2"))){
                    Block p = Methods.getTargetBlock(((Player) sender), 10);
                    WarpConfig.get().set("lobby." + args[1] + "x", p.getX());
                    WarpConfig.get().set("lobby." + args[1] + "y", p.getY());
                    WarpConfig.get().set("lobby." + args[1] + "z", p.getZ());
                    Methods.langBasedMessage(
                            "A lobby " + args[1] + " pontja sikeresen beállítva!",
                            "You have set the " + args[1] + " point of the lobby successfully!",
                            BroadcastType.Sender, MessageType.Info, sender
                    );
                    return true;
                }
                else if (args[0].equals("protect") && args[1].equals("on")){
                    WarpConfig.get().set("lobby.protect", true);
                    Methods.langBasedMessage(
                            "A lobby védelme bekapcsolva.",
                            "The protection of lobby is on.",
                            BroadcastType.Sender, MessageType.Info, sender
                    );
                    return true;
                }
                else if (args[0].equals("protect") && args[1].equals("off")){
                    WarpConfig.get().set("lobby.protect", false);
                    Methods.langBasedMessage(
                            "A lobby védelme kikapcsolva.",
                            "The protection of lobby is off.",
                            BroadcastType.Sender, MessageType.Info, sender
                    );
                    return true;
                }
                else return false;
            }
        }
        else{
            Methods.langBasedMessage(
                    "Csak játékosok használhatják ezt a parancsot!",
                    "Only players are allowed to use this command!",
                    BroadcastType.Sender, MessageType.Error, sender
            );
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("set", "reloadconfig", "setprotect", "protect");
        if (args.length == 2 && args[0].equals("set")) return Collections.singletonList(String.valueOf(((Player) sender).getLocation().getX()));
        else if (args.length == 3 && args[0].equals("set")) return Collections.singletonList(String.valueOf(((Player) sender).getLocation().getY()));
        else if (args.length == 4 && args[0].equals("set")) return Collections.singletonList(String.valueOf(((Player) sender).getLocation().getZ()));
        if (args.length == 2 && args[0].equals("setprotect")) return Arrays.asList("p1", "p2");
        if (args.length == 2 && args[0].equals("protect")) return Arrays.asList("on", "off");

        return null;
    }
}
