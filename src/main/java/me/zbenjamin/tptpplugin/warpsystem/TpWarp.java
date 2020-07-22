package me.zbenjamin.tptpplugin.warpsystem;

import me.zbenjamin.tptpplugin.Methods;
import me.zbenjamin.tptpplugin.files.WarpConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TpWarp implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            if (args.length == 0) return false;
            if(WarpConfig.get().contains("warps." + args[0])){
                double x = WarpConfig.get().getDouble("warps." + args[0] + ".x"),
                        y = WarpConfig.get().getDouble("warps." + args[0] + ".y"),
                        z = WarpConfig.get().getDouble("warps." + args[0] + ".z");
                ((Player) sender).teleport(new Location(((Player) sender).getWorld(), x, y, z));
                return true;
            }
            else if(WarpConfig.get().contains("playerwarps." + ((Player) sender).getUniqueId() + "." + args[0])){
                double x = WarpConfig.get().getDouble("playerwarps." + ((Player) sender).getUniqueId() + "." + args[0] + ".x"),
                        y = WarpConfig.get().getDouble("playerwarps." + ((Player) sender).getUniqueId() + "." + args[0] + ".y"),
                        z = WarpConfig.get().getDouble("playerwarps." + ((Player) sender).getUniqueId() + "." + args[0] + ".z");
                ((Player) sender).teleport(new Location(((Player) sender).getWorld(), x, y, z));
                return true;
            }
            else{
                if (Methods.getLocaleHu()) sender.sendMessage("Ilyen pont nem létezik!");
                else sender.sendMessage("This point does not exist!");
                return false;
            }
        }
        else{
            if (Methods.getLocaleHu()) sender.sendMessage("Csak játékosok használhatják ezt a parancsot!");
            else sender.sendMessage("Only players are allowed to use this command!");
            return true;
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
