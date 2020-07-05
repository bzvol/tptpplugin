package me.zbenjamin.tptpplugin.emotes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HappyCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) Bukkit.getServer().broadcastMessage(ChatColor.WHITE +
                ((Player) sender).getDisplayName() + ChatColor.GREEN + " boldog :)");
        else System.out.println("Csak játékosok használhatják ezt a parancsot!");
        return false;
    }
}
