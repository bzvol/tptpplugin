package me.zbenjamin.tptpplugin;

import me.zbenjamin.tptpplugin.enums.BroadcastType;
import me.zbenjamin.tptpplugin.enums.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OtherCommands implements TabExecutor {
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

                    Methods.langBasedMessage(
                            "Tárgy átnevezve: " + ChatColor.GOLD + finalargs,
                            "Item renamed: " + ChatColor.GOLD + finalargs,
                            BroadcastType.Sender, MessageType.Info, sender
                    );
                    return true;
                }
            } else {
                Methods.langBasedMessage(
                        "Csak játékosok használhatják ezt a parancsot!",
                        "Only players are allowed to use this command!",
                        BroadcastType.Sender, MessageType.Error, sender
                );
                return true;
            }
        }
        else if(label.equalsIgnoreCase("blockcoords")){
            if (sender instanceof Player) {
                Block targetBlock = Methods.getTargetBlock((Player) sender);
                int x = targetBlock.getX(), y = targetBlock.getY(), z = targetBlock.getZ();
                Methods.langBasedMessage(
                        "A blokk koordinátái: " + ChatColor.YELLOW + x + " " + y + " " + z,
                        "Block's coords: " + ChatColor.YELLOW + x + " " + y + " " + z,
                        BroadcastType.Sender, MessageType.Info, sender
                );
            } else {
                Methods.langBasedMessage(
                        "Csak játékosok használhatják ezt a parancsot!",
                        "Only players are allowed to use this command!",
                        BroadcastType.Sender, MessageType.Error, sender
                );
            }
            return true;
        }
        else if(label.equalsIgnoreCase("getcoords")){
            if (sender instanceof Player) {
                Player p = (Player) sender;
                int x = (int) p.getLocation().getX(), y = (int) p.getLocation().getY(), z = (int) p.getLocation().getZ() - 1;
                Bukkit.getServer().broadcastMessage(p.getDisplayName() + "'s coord: " + ChatColor.YELLOW + x + " " + y + " " + z);
                Methods.langBasedMessage(
                        p.getDisplayName() + " koordinátái: " + ChatColor.YELLOW + x + " " + y + " " + z,
                        p.getDisplayName() + "'s coords: " + ChatColor.YELLOW + x + " " + y + " " + z,
                        BroadcastType.Server, MessageType.Info
                );
            } else {
                Methods.langBasedMessage(
                        "Csak játékosok használhatják ezt a parancsot!",
                        "Only players are allowed to use this command!",
                        BroadcastType.Sender, MessageType.Error, sender
                );
            }
            return true;
        }
        else if (label.equalsIgnoreCase("heal")){
            if (args.length == 0 && sender instanceof Player) {
                ((Player) sender).setHealth(20);
                Methods.langBasedMessage(
                        "Jó egészséget!",
                        "Have a good health!",
                        BroadcastType.Sender, MessageType.Info, sender
                );
            }
            else {
                Objects.requireNonNull(Bukkit.getServer().getPlayerExact(args[0])).setHealth(20);
                Methods.langBasedMessage(
                        "Jó egészséget!",
                        "Good health!",
                        BroadcastType.Sender, MessageType.Info, Bukkit.getServer().getPlayerExact(args[0])
                );
            }
            return true;
        }
        else if (label.equalsIgnoreCase("feed")){
            if (args.length == 0 && sender instanceof Player) {
                ((Player) sender).setFoodLevel(20);
                Methods.langBasedMessage(
                        "Jó étvágyat!",
                        "Bon appetite!",
                        BroadcastType.Sender, MessageType.Info, sender
                );
            }
            else {
                Objects.requireNonNull(Bukkit.getServer().getPlayerExact(args[0])).setFoodLevel(20);
                Methods.langBasedMessage(
                        "Jó étvágyat!",
                        "Bon appetite!",
                        BroadcastType.Sender, MessageType.Info, Bukkit.getServer().getPlayerExact(args[0])
                );
            }
            return true;
        }
        else if (label.equalsIgnoreCase("invulnerable")){
            if (args.length == 0) return false;
            if (args.length == 1 && sender instanceof Player) {
                ((Player) sender).setInvulnerable(Boolean.parseBoolean(args[0]));
                Methods.langBasedMessage(
                        Boolean.parseBoolean(args[0]) ? "Mostantól halhatatlan vagy." : "Mostantól nem vagy halhatatlan.",
                        Boolean.parseBoolean(args[0]) ? "You are now invulnerable." : "Now you aren't invulnerable.",
                        BroadcastType.Sender, MessageType.Info, sender
                );
            }
            else {
                Objects.requireNonNull(Bukkit.getServer().getPlayerExact(args[1])).setInvulnerable(Boolean.parseBoolean(args[0]));
                Methods.langBasedMessage(
                        Boolean.parseBoolean(args[0]) ? "Mostantól halhatatlan vagy." : "Mostantól nem vagy halhatatlan.",
                        Boolean.parseBoolean(args[0]) ? "You are now invulnerable." : "Now you aren't invulnerable.",
                        BroadcastType.Sender, MessageType.Info, Bukkit.getServer().getPlayerExact(args[1])
                );
            }
            return true;
        }

        else if (label.equalsIgnoreCase("gcmd")) {
            if (sender instanceof Player) {
                ((Player) sender).getInventory().addItem(new ItemStack(Material.COMMAND_BLOCK, 1));
                Methods.langBasedMessage(
                        "Tessék, egy parancsblokk!",
                        "Here it is, a command block!",
                        BroadcastType.Sender, MessageType.Info, sender
                );
            } else Methods.langBasedMessage(
                    "Csak játékosok használhatják ezt a parancsot!",
                    "Only players are allowed to use this command!",
                    BroadcastType.Sender, MessageType.Error, sender
            );

            return true;
        }
        else if (label.equalsIgnoreCase("gbarr")) {
            if (sender instanceof Player) {
                ((Player) sender).getInventory().addItem(new ItemStack(Material.BARRIER, 1));
                Methods.langBasedMessage(
                        "Tessék, egy akadály!",
                        "Here it is, a barrier!",
                        BroadcastType.Sender, MessageType.Info, sender
                );
            } else Methods.langBasedMessage(
                    "Csak játékosok használhatják ezt a parancsot!",
                    "Only players are allowed to use this command!",
                    BroadcastType.Sender, MessageType.Error, sender
            );

            return true;
        }
        else if (label.equalsIgnoreCase("killitems")){
            List<Item> items = new ArrayList<>();
            for (Entity entity : Bukkit.getServer().getWorlds().get(0).getEntities()) if (entity instanceof Item) items.add((Item) entity);
            Methods.langBasedMessage(
                    items.size() + " tárgy eltávolítva.",
                    items.size()  + " item has been removed.",
                    BroadcastType.Server, MessageType.Info
            );
            for (Item item : items) item.remove();
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player player : players) playerNames.add(player.getName());

        if (
                (alias.equalsIgnoreCase("heal")
                        || alias.equalsIgnoreCase("feed"))
                        && args.length == 1
                        || alias.equalsIgnoreCase("invulnerable")
                        && args.length == 2
        ) return playerNames;
        else if (alias.equalsIgnoreCase("invulnerable") && args.length == 1)
            return Arrays.asList("true", "false");

        return null;
    }
}
