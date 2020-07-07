package me.zbenjamin.tptpplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Plugin pl = TptpPlugin.getPlugin(TptpPlugin.class);
        if (args.length == 0) return false;
        if (args[0] == "save"){
            TptpPlugin.getPlugin(TptpPlugin.class).saveDefaultConfig();
            if (pl.getConfig().getString("locale") == "hu") sender.sendMessage("[Tptp Plugin] A konfiguráció sikeresen elmentve.");
            else sender.sendMessage("[Tptp Plugin] Config saved successfully.");
            return true;
        }
        else if (args[0] == "reload"){
            TptpPlugin.getPlugin(TptpPlugin.class).reloadConfig();
            if (pl.getConfig().getString("locale") == "hu") sender.sendMessage("[Tptp Plugin] A konfiguráció újratöltve.");
            else sender.sendMessage("[Tptp Plugin] Config reloaded.");
            return true;
        }
        else if (args[0] == "set"){
            if (args[1] == "locale" || args[1] == "joinmessage") {
                TptpPlugin.getPlugin(TptpPlugin.class).getConfig().set(args[1], args[2]);
                TptpPlugin.getPlugin(TptpPlugin.class).saveDefaultConfig();
                if (pl.getConfig().getString("locale") == "hu") sender.sendMessage(ChatColor.WHITE + "[Tptp Plugin] "
                        + ChatColor.BLUE + args[1] + ChatColor.WHITE + "-t sikeresen " + ChatColor.BLUE + args[2] + ChatColor.WHITE + " értékre állítottad.");
                else sender.sendMessage(ChatColor.WHITE + "[Tptp Plugin] You have set " + ChatColor.BLUE + args[1] + ChatColor.WHITE
                        + " to " + ChatColor.BLUE + args[2] + ChatColor.WHITE + " successfully.");
                return true;
            }
            else if (args[1] == "tnt-allowed" || args[1] == "customjoinmessage"){
                TptpPlugin.getPlugin(TptpPlugin.class).getConfig().set(args[1], Boolean.parseBoolean(args[2]));
                TptpPlugin.getPlugin(TptpPlugin.class).saveDefaultConfig();
                if (pl.getConfig().getString("locale") == "hu") sender.sendMessage(ChatColor.WHITE + "[Tptp Plugin] "
                        + ChatColor.BLUE + args[1] + ChatColor.WHITE + "-t sikeresen " + ChatColor.BLUE + args[2] + ChatColor.WHITE + " értékre állítottad.");
                else sender.sendMessage(ChatColor.WHITE + "[Tptp Plugin] You have set " + ChatColor.BLUE + args[1] + ChatColor.WHITE
                        + " to " + ChatColor.BLUE + args[2] + ChatColor.WHITE + " successfully.");
                return true;
            }
            else return false;
        }
        else return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("set", "reload", "save");
        if (args.length == 2 && args[0] == "set") return new ArrayList<String>(TptpPlugin.getPlugin(TptpPlugin.class).getConfig().getKeys(false));
        if (args.length == 3 && args[0] == "set" && args[1] == "locale") return Arrays.asList("hu", "en");
        else if (args.length == 3 && args[0] == "set" && (args[1] == "tnt-allowed" || args[1] == "customjoinmessage")) return Arrays.asList("true", "false");

        return null;
    }

}
