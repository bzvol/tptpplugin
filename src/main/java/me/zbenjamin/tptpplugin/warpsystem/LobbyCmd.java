package me.zbenjamin.tptpplugin.warpsystem;

import me.zbenjamin.tptpplugin.Methods;
import me.zbenjamin.tptpplugin.TptpPlugin;
import me.zbenjamin.tptpplugin.files.WarpConfig;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class LobbyCmd implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Plugin pl = TptpPlugin.getPlugin(TptpPlugin.class);
        if (sender instanceof Player){
            if (args.length == 0){
                double x = WarpConfig.get().getDouble("lobby.x"),
                        y = WarpConfig.get().getDouble("lobby.y"),
                        z = WarpConfig.get().getDouble("lobby.z");
                ((Player) sender).teleport(new Location(((Player) sender).getWorld(), x, y, z));
                return true;
            }
            else{
                if (args.length == 1 && args[0] == "set"){
                    Player p = (Player) sender;
                    double x = p.getLocation().getX(), y = p.getLocation().getY(), z = p.getLocation().getZ();
                    WarpConfig.get().set("lobby.x", x);
                    WarpConfig.get().set("lobby.y", y);
                    WarpConfig.get().set("lobby.z", z);
                    if (pl.getConfig().getString("locale") == "hu") sender.sendMessage("A lobby sikeresen beállítva!");
                    else sender.sendMessage("You have set the lobby successfully!");
                    return true;
                }
                else if (args.length == 4 && args[0] == "set"){
                    double x = Double.parseDouble(args[1]), y = Double.parseDouble(args[2]), z = Double.parseDouble(args[3]);
                    WarpConfig.get().set("lobby.x", x);
                    WarpConfig.get().set("lobby.y", y);
                    WarpConfig.get().set("lobby.z", z);
                    if (pl.getConfig().getString("locale") == "hu") sender.sendMessage("A lobby sikeresen beállítva!");
                    else sender.sendMessage("You have set the lobby successfully!");
                    return true;
                }
                else if (args[0] == "reloadconfig"){
                    WarpConfig.save();
                    WarpConfig.reload();
                    if (pl.getConfig().getString("locale") == "hu") sender.sendMessage("[Tptp Plugin] A konfiguráció elmentve és újratöltve.");
                    else sender.sendMessage("[Tptp Plugin] Config saved and reloaded.");
                    return true;
                }
                else if (args[0] == "setprotect" && (args[1] == "p1" || args[1] == "p2")){
                    Block p = Methods.getTargetBlock(((Player) sender), 10);
                    WarpConfig.get().set("lobby." + args[1] + "x", p.getX());
                    WarpConfig.get().set("lobby." + args[1] + "x", p.getY());
                    WarpConfig.get().set("lobby." + args[1] + "x", p.getZ());
                    if (pl.getConfig().getString("locale") == "hu") sender.sendMessage("A lobby " + args[1] + " pontja sikeresen beállítva!");
                    else sender.sendMessage("You have set the " + args[1] + " point of the lobby successfully!");
                    return true;
                }
                else if (args[0] == "protect" && args[1] == "on"){
                    WarpConfig.get().set("lobby.protect", true);
                    if (pl.getConfig().getString("locale") == "hu") sender.sendMessage("A lobby védelme bekapcsolva.");
                    else sender.sendMessage("The protection of lobby is on.");
                    return true;
                }
                else if (args[0] == "protect" && args[1] == "off"){
                    WarpConfig.get().set("lobby.protect", false);
                    if (pl.getConfig().getString("locale") == "hu") sender.sendMessage("A lobby védelme kikapcsolva.");
                    else sender.sendMessage("The protection of lobby is off.");
                    return true;
                }
                else return false;
            }
        }
        else{
            if (pl.getConfig().getString("locale") == "hu") sender.sendMessage("Csak játékosok használhatják ezt a parancsot!");
            else sender.sendMessage("Only players are allowed to use this command!");
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("set", "reloadconfig", "setprotect", "protect");
        if (args.length == 2 && args[0] == "set") return Arrays.asList(String.valueOf(((Player) sender).getLocation().getX()));
        else if (args.length == 3 && args[0] == "set") return Arrays.asList(String.valueOf(((Player) sender).getLocation().getY()));
        else if (args.length == 4 && args[0] == "set") return Arrays.asList(String.valueOf(((Player) sender).getLocation().getZ()));
        if (args.length == 2 && args[0] == "setprotect") return Arrays.asList("p1", "p2");
        if (args.length == 2 && args[0] == "protect") return Arrays.asList("on", "off");

        return null;
    }
}
