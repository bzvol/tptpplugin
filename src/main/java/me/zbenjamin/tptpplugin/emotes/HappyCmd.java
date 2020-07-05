package me.zbenjamin.tptpplugin.emotes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.ArrayList;

public class HappyCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            if (sender instanceof Player) Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                    ((Player) sender).getDisplayName() + ChatColor.GREEN + ChatColor.BOLD + " boldog :)");
            else System.out.println("Csak játékosok használhatják ezt a parancsot.");
        }
        else{
            if (Bukkit.getServer().getPlayerExact(args[0]) != null) Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                    Bukkit.getServer().getPlayer(args[0]).getDisplayName() + ChatColor.GREEN + ChatColor.BOLD + " boldog :)");
            else{
                if (sender instanceof Player) ((Player) sender).sendMessage("A játékos nincs a szerveren.");
                else System.out.println("A játékos nincs a szerveren.");
                return false;
            }
        }

        return true;
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