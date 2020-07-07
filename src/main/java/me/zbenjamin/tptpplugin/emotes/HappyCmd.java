package me.zbenjamin.tptpplugin.emotes;

import me.zbenjamin.tptpplugin.TptpPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.ArrayList;

public class HappyCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            if (sender instanceof Player) {
                if (TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale") == "hu") Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        ((Player) sender).getDisplayName() + ChatColor.GREEN + ChatColor.BOLD + " boldog :)");
                else Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        ((Player) sender).getDisplayName() + ChatColor.GREEN + ChatColor.BOLD + " is happy :)");
                return true;
            }
            else if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender){
                if (TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale") == "hu") Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        "A konzol" + ChatColor.GREEN + ChatColor.BOLD + " boldog :)");
                else Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        "The console" + ChatColor.GREEN + ChatColor.BOLD + " is happy :)");
                return true;
            }
            else if (sender instanceof BlockCommandSender){
                if (TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale") == "hu") Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        "Egy parancsblokk" + ChatColor.GREEN + ChatColor.BOLD + " boldog :)");
                else Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        "A command block" + ChatColor.GREEN + ChatColor.BOLD + " is happy :)");
                return true;
            }
            else {
                if (TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale") == "hu") System.out.println("Csak játékosok használhatják ezt a parancsot.");
                else System.out.println("Only players are allowed to use this command.");
                return true;
            }
        }
        else{
            if (Bukkit.getServer().getPlayerExact(args[0]) != null) {
                if (TptpPlugin.getPlugin(TptpPlugin.class)
                        .getConfig().getString("locale") == "hu") Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        Bukkit.getServer().getPlayer(args[0]).getDisplayName() + ChatColor.GREEN + ChatColor.BOLD + " boldog :)");
                else Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                        Bukkit.getServer().getPlayer(args[0]).getDisplayName() + ChatColor.GREEN + ChatColor.BOLD + " is happy :)");
                return true;
            }
            else{
                if (sender instanceof Player) {
                    if (TptpPlugin.getPlugin(TptpPlugin.class)
                            .getConfig().getString("locale") == "hu") ((Player) sender).sendMessage("A játékos nincs a szerveren.");
                    else ((Player) sender).sendMessage("The player isn't on the server.");
                }
                else {
                    if (TptpPlugin.getPlugin(TptpPlugin.class)
                            .getConfig().getString("locale") == "hu") System.out.println("A játékos nincs a szerveren.");
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
            for (int i = 0; i < players.length; i++) playerNames.add(players[i].getName());

            return playerNames;
        }
        return null;
    }
}