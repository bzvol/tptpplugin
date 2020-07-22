package me.zbenjamin.tptpplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        switch (args[0]) {
            case "save":
                TptpPlugin.getPlugin(TptpPlugin.class).saveConfig();
                if (Methods.getLocaleHu())
                    sender.sendMessage("[Tptp Plugin] A konfiguráció sikeresen elmentve.");
                else sender.sendMessage("[Tptp Plugin] Config saved successfully.");
                return true;
            case "reload":
                TptpPlugin.getPlugin(TptpPlugin.class).reloadConfig();
                if (Methods.getLocaleHu())
                    sender.sendMessage("[Tptp Plugin] A konfiguráció újratöltve.");
                else sender.sendMessage("[Tptp Plugin] Config reloaded.");
                return true;
            case "set":
                if (args[1].equals("locale") || args[1].equals("joinmessage")) {
                    TptpPlugin.getPlugin(TptpPlugin.class).getConfig().set(args[1], args[2]);
                    TptpPlugin.getPlugin(TptpPlugin.class).saveConfig();
                    if (Methods.getLocaleHu())
                        sender.sendMessage(ChatColor.WHITE + "[Tptp Plugin] "
                                + ChatColor.BLUE + args[1] + ChatColor.WHITE + "-t sikeresen " + ChatColor.BLUE + args[2] + ChatColor.WHITE + " értékre állítottad.");
                    else
                        sender.sendMessage(ChatColor.WHITE + "[Tptp Plugin] You have set " + ChatColor.BLUE + args[1] + ChatColor.WHITE
                                + " to " + ChatColor.BLUE + args[2] + ChatColor.WHITE + " successfully.");
                    return true;
                } else if (args[1].equals("tnt-allowed") || args[1].equals("customjoinmessage")) {
                    TptpPlugin.getPlugin(TptpPlugin.class).getConfig().set(args[1], Boolean.parseBoolean(args[2]));
                    TptpPlugin.getPlugin(TptpPlugin.class).saveConfig();
                    if (Methods.getLocaleHu())
                        sender.sendMessage(ChatColor.WHITE + "[Tptp Plugin] "
                                + ChatColor.BLUE + args[1] + ChatColor.WHITE + "-t sikeresen " + ChatColor.BLUE + args[2] + ChatColor.WHITE + " értékre állítottad.");
                    else
                        sender.sendMessage(ChatColor.WHITE + "[Tptp Plugin] You have set " + ChatColor.BLUE + args[1] + ChatColor.WHITE
                                + " to " + ChatColor.BLUE + args[2] + ChatColor.WHITE + " successfully.");
                    return true;
                } else return false;
            default:
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("set", "reload", "save");
        if (args.length == 2 && args[0].equals("set")) return new ArrayList<>(TptpPlugin.getPlugin(TptpPlugin.class).getConfig().getKeys(false));
        if (args.length == 3 && args[0].equals("set") && args[1].equals("locale")) return Arrays.asList("hu", "en");
        else if (args.length == 3 && args[0].equals("set") && (args[1].equals("tnt-allowed") || args[1].equals("customjoinmessage"))) return Arrays.asList("true", "false");

        return null;
    }

}
