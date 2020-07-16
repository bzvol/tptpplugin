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
                    ItemMeta im = p.getInventory().getItemInMainHand().getItemMeta();
                    im.setDisplayName(finalargs.toString());
                    p.getInventory().getItemInMainHand().setItemMeta(im);
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
        else if (label.equalsIgnoreCase("maxhealth")){
            if (sender instanceof Player) ((Player) sender).setHealth(20);
            else Bukkit.getServer().getPlayerExact(args[0]).setHealth(20);
        }
        else if (label.equalsIgnoreCase("invulnerable")){
            if (sender instanceof Player) ((Player) sender).setInvulnerable(Boolean.parseBoolean(args[0]));
            else Bukkit.getServer().getPlayerExact(args[1]).setInvulnerable(Boolean.parseBoolean(args[0]));
        }

        else if (label.equalsIgnoreCase("gcmd")) {
            if (sender instanceof Player) ((Player) sender).getInventory().addItem(new ItemStack(Material.COMMAND_BLOCK, 1));
        }
        else if (label.equalsIgnoreCase("gbarr")) {
            if (sender instanceof Player) ((Player) sender).getInventory().addItem(new ItemStack(Material.BARRIER, 1));
        }
        return false;
    }
}
