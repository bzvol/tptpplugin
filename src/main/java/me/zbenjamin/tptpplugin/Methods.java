package me.zbenjamin.tptpplugin;

import me.zbenjamin.tptpplugin.enums.BroadcastType;
import me.zbenjamin.tptpplugin.enums.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;

import java.util.Objects;

public class Methods {
    /**
     * @param player The player who looks at the target block
     * @return The block that is targeted by the player
     */
    public static Block getTargetBlock(Player player) {
        return getTargetBlock(player, 0);
    }

    /**
     * @param player the player who looks at the targeted block
     * @param maxDistance distance for player's targeting
     * @return the block that is targeted by the player
     */
    public static Block getTargetBlock(Player player, int maxDistance) {
        BlockIterator iter;
        if (maxDistance < 1) iter = new BlockIterator(player);
        else iter = new BlockIterator(player, maxDistance);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    /**
     * @return {@code true} if plugin's config is hungarian, otherwise {@code false}
     */
    public static boolean getLocaleHu() { return Objects.equals((TptpPlugin.getPlugin(TptpPlugin.class).getConfig().getString("locale")), "hu"); }

    public static void langBasedMessage(String messagehu,
                                            String messageother,
                                            BroadcastType broadcastType,
                                            MessageType messageType) {
        langBasedMessage(messagehu, messageother, broadcastType, messageType, null);
    }
    public static void langBasedMessage(String messagehu,
                                                String messageother,
                                                BroadcastType broadcastType,
                                                MessageType messageType,
                                                CommandSender sender) {
        switch (broadcastType) {
            case Server:
                switch (messageType) {
                    case Info:
                        if (getLocaleHu()) Bukkit.getServer().broadcastMessage("[" + ChatColor.BLUE + "TptpPlugin" + ChatColor.RESET + "] " + messagehu);
                        else Bukkit.getServer().broadcastMessage("[" + ChatColor.BLUE + "TptpPlugin" + ChatColor.RESET + "] " + messageother);
                        break;
                    case Error:
                        if (getLocaleHu()) Bukkit.getServer().broadcastMessage("[" + ChatColor.RED + "TptpPlugin" + ChatColor.RESET + "] " + messagehu);
                        else Bukkit.getServer().broadcastMessage("[" + ChatColor.RED + "TptpPlugin" + ChatColor.RESET + "] " + messageother);
                        break;
                    case Warning:
                        if (getLocaleHu()) Bukkit.getServer().broadcastMessage("[" + ChatColor.YELLOW + "TptpPlugin" + ChatColor.RESET + "] " + messagehu);
                        else Bukkit.getServer().broadcastMessage("[" + ChatColor.YELLOW + "TptpPlugin" + ChatColor.RESET + "] " + messageother);
                        break;
                    /*case Successful:
                        if (getLocaleHu()) Bukkit.getServer().broadcastMessage("[" + ChatColor.GREEN + "TptpPlugin" + ChatColor.RESET + "] " + messagehu);
                        else Bukkit.getServer().broadcastMessage("[" + ChatColor.GREEN + "TptpPlugin" + ChatColor.RESET + "] " + messageother);
                        break;
                    case NoColor:
                        if (getLocaleHu()) Bukkit.getServer().broadcastMessage("[TptpPlugin] " + messagehu);
                        else Bukkit.getServer().broadcastMessage("[TptpPlugin] " + messageother);
                        break;*/
                }
                break;
            case Sender:
                if (sender != null) {
                    switch (messageType) {
                        case Info:
                            if (getLocaleHu()) sender.sendMessage("[" + ChatColor.BLUE + "TptpPlugin" + ChatColor.RESET + "] " + messagehu);
                            else sender.sendMessage("[" + ChatColor.BLUE + "TptpPlugin" + ChatColor.RESET + "] " + messageother);
                            break;
                        case Error:
                            if (getLocaleHu()) sender.sendMessage("[" + ChatColor.RED + "TptpPlugin" + ChatColor.RESET + "] " + messagehu);
                            else sender.sendMessage("[" + ChatColor.RED + "TptpPlugin" + ChatColor.RESET + "] " + messageother);
                            break;
                        case Warning:
                            if (getLocaleHu()) sender.sendMessage("[" + ChatColor.YELLOW + "TptpPlugin" + ChatColor.RESET + "] " + messagehu);
                            else sender.sendMessage("[" + ChatColor.YELLOW + "TptpPlugin" + ChatColor.RESET + "] " + messageother);
                            break;
                        /*case Successful:
                            if (getLocaleHu()) sender.sendMessage("[" + ChatColor.GREEN + "TptpPlugin" + ChatColor.RESET + "] " + messagehu);
                            else sender.sendMessage("[" + ChatColor.GREEN + "TptpPlugin" + ChatColor.RESET + "] " + messageother);
                            break;
                        case NoColor:
                            if (getLocaleHu()) sender.sendMessage("[TptpPlugin] " + messagehu);
                            else sender.sendMessage("[TptpPlugin] " + messageother);
                            break;*/
                    }
                }
                else throw new NullPointerException("CommandSender required in this case!");
                break;
        }
    }

    public static Plugin getPlugin(){return TptpPlugin.getPlugin(TptpPlugin.class);}
}
