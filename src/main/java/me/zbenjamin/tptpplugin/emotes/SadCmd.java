package me.zbenjamin.tptpplugin.emotes;

import me.zbenjamin.tptpplugin.TptpPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class SadCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            if (sender instanceof Player) {
                if (Objects.requireNonNull(TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale")).equals("hu")) Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        ((Player) sender).getDisplayName() + ChatColor.DARK_AQUA + ChatColor.BOLD + " szomorú :(");
                else Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        ((Player) sender).getDisplayName() + ChatColor.DARK_AQUA + ChatColor.BOLD + " is sad :(");
                return true;
            }
            else if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender){
                if (Objects.requireNonNull(TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale")).equals("hu")) Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        "A konzol" + ChatColor.DARK_AQUA + ChatColor.BOLD + " szomorú :(");
                else Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        "The console" + ChatColor.DARK_AQUA + ChatColor.BOLD + " is sad :(");
                return true;
            }
            else if (sender instanceof BlockCommandSender){
                if (Objects.requireNonNull(TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale")).equals("hu")) Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        "Egy parancsblokk" + ChatColor.DARK_AQUA + ChatColor.BOLD + " szomorú :(");
                else Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        "A command block" + ChatColor.DARK_AQUA + ChatColor.BOLD + " is sad :(");
                return true;
            }
            else {
                if (Objects.requireNonNull(TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale")).equals("hu")) System.out.println("Csak játékosok használhatják ezt a parancsot.");
                else System.out.println("Only players are allowed to use this command.");
                return true;
            }
        }
        else{
            if (Bukkit.getServer().getPlayerExact(args[0]) != null) {
                if (Objects.requireNonNull(TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale")).equals("hu")) Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        Objects.requireNonNull(Bukkit.getServer().getPlayer(args[0])).getDisplayName() + ChatColor.DARK_AQUA + ChatColor.BOLD + " szomorú :(");
                else Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        Objects.requireNonNull(Bukkit.getServer().getPlayer(args[0])).getDisplayName() + ChatColor.DARK_AQUA + ChatColor.BOLD + " is sad :(");
                return true;
            }
            else{
                if (sender instanceof Player) {
                    if (Objects.requireNonNull(TptpPlugin.getPlugin(TptpPlugin.class)
                            .getConfig().getString("locale")).equals("hu")) sender.sendMessage("A játékos nincs a szerveren.");
                    else sender.sendMessage("The player isn't on the server.");
                }
                else {
                    if (Objects.requireNonNull(TptpPlugin.getPlugin(TptpPlugin.class)
                            .getConfig().getString("locale")).equals("hu")) System.out.println("A játékos nincs a szerveren.");
                    else System.out.println("The player isn't on the server.");
                }
                return false;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1){
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (Player player : players) playerNames.add(player.getName());

            return playerNames;
        }
        return null;
    }
}