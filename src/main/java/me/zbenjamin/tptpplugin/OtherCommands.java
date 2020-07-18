package me.zbenjamin.tptpplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class OtherCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("rename")){
            if (sender instanceof Player){
                if (args.length != 0){
                    Player p = (Player) sender;
                    StringBuilder finalargs = new StringBuilder();
                    for (String arg : args) if (!arg.equals(" ")) finalargs.append(arg).append(" ");
                    finalargs.deleteCharAt(finalargs.length()-1);
                    ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
                    assert meta != null;
                    meta.setDisplayName(finalargs.toString());
                    p.getInventory().getItemInMainHand().setItemMeta(meta);
                    return true;
                }
            }
        }
        else if(label.equalsIgnoreCase("blockcoord")){
            if(sender instanceof Player){
                Block targetBlock = Methods.getTargetBlock((Player) sender);
                int x = targetBlock.getX(), y = targetBlock.getY(), z = targetBlock.getZ();
                sender.sendMessage("Block's coord: " + ChatColor.YELLOW + x + " " + y + " " + z);
                return true;
            }
        }
        else if(label.equalsIgnoreCase("getcoord")){
            if (sender instanceof Player){
                Player p = (Player) sender;
                int x = (int) p.getLocation().getX(), y = (int) p.getLocation().getY(), z = (int) p.getLocation().getZ() - 1;
                Bukkit.getServer().broadcastMessage(p.getDisplayName() + "'s coord: " + ChatColor.YELLOW + x + " " + y + " " + z);
                return true;
            }
        }
        else if (label.equalsIgnoreCase("heal")){
            if (sender instanceof Player) ((Player) sender).setHealth(20);
            else Objects.requireNonNull(Bukkit.getServer().getPlayerExact(args[0])).setHealth(20);
            return true;
        }
        else if (label.equalsIgnoreCase("feed")){
            if (sender instanceof Player) ((Player) sender).setFoodLevel(20);
            else Objects.requireNonNull(Bukkit.getServer().getPlayerExact(args[0])).setFoodLevel(20);
            return true;
        }
        else if (label.equalsIgnoreCase("invulnerable")){
            if (sender instanceof Player) ((Player) sender).setInvulnerable(Boolean.parseBoolean(args[0]));
            else Objects.requireNonNull(Bukkit.getServer().getPlayerExact(args[1])).setInvulnerable(Boolean.parseBoolean(args[0]));
            return true;
        }

        else if (label.equalsIgnoreCase("gcmd")) {
            if (sender instanceof Player) ((Player) sender).getInventory().addItem(new ItemStack(Material.COMMAND_BLOCK, 1));
            return true;
        }
        else if (label.equalsIgnoreCase("gbarr")) {
            if (sender instanceof Player) ((Player) sender).getInventory().addItem(new ItemStack(Material.BARRIER, 1));
            return true;
        }
        return false;
    }
}
