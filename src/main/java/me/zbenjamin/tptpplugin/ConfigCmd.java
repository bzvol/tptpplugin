package me.zbenjamin.tptpplugin;

import me.zbenjamin.tptpplugin.enums.BroadcastType;
import me.zbenjamin.tptpplugin.enums.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConfigCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        switch (args[0]) {
            case "save":
                TptpPlugin.getPlugin(TptpPlugin.class).saveConfig();

                Methods.langBasedMessage(
                        "A konfiguráció sikeresen elmentve.",
                        "Config saved successfully.",
                        BroadcastType.Sender, MessageType.Info, sender
                );

                return true;
            case "reload":
                TptpPlugin.getPlugin(TptpPlugin.class).reloadConfig();

                Methods.langBasedMessage(
                        "A konfiguráció újratöltve.",
                        "Config reloaded.",
                        BroadcastType.Sender, MessageType.Info, sender
                );

                return true;
            case "set":
                if (args.length != 3) return false;
                if (Methods.getPlugin().getConfig().contains(args[1])) {
                    Object setvalue = null;
                    try {
                        setvalue = Objects.requireNonNull(Methods.getPlugin().getConfig()
                                .get(args[1])).getClass().getConstructor(new Class[]{String.class})
                                .newInstance(args[2]);
                    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        switch (Objects.requireNonNull(Methods.getPlugin().getConfig().get(args[1])).getClass().getSimpleName()){
                            case "String":
                                Methods.getPlugin().getConfig().set(args[1], args[2]);
                                break;
                            case "Integer":
                                Methods.getPlugin().getConfig().set(args[1], Integer.parseInt(args[2]));
                                break;
                            case "Boolean":
                                Methods.getPlugin().getConfig().set(args[1], Boolean.parseBoolean(args[2]));
                                break;
                            case "Double":
                                Methods.getPlugin().getConfig().set(args[1], Double.parseDouble(args[2]));
                                break;
                        }
                    }
                    Methods.getPlugin().getConfig().set(args[1], setvalue);
                    Methods.getPlugin().saveConfig();
                    Methods.getPlugin().reloadConfig();
                    Methods.langBasedMessage(
                            ChatColor.BLUE + args[1] + ChatColor.RESET + "-t sikeresen " + ChatColor.BLUE + args[2] + ChatColor.RESET + " értékre állítottad.",
                            "You have set " + ChatColor.BLUE + args[1] + ChatColor.RESET + " to " + ChatColor.BLUE + args[2] + ChatColor.RESET + " successfully.",
                            BroadcastType.Sender, MessageType.Info, sender
                    );
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
        else if (args.length == 3 && args[0].equals("set")
                && Objects.requireNonNull(Methods.getPlugin().getConfig().get(args[1])).getClass().getSimpleName().equalsIgnoreCase("Boolean"))
            return Arrays.asList("true", "false");

        return null;
    }

}
