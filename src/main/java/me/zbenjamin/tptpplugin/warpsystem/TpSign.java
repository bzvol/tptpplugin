package me.zbenjamin.tptpplugin.warpsystem;

import me.zbenjamin.tptpplugin.Methods;
import me.zbenjamin.tptpplugin.enums.BroadcastType;
import me.zbenjamin.tptpplugin.enums.MessageType;
import me.zbenjamin.tptpplugin.files.WarpConfig;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TpSign implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            if (Methods.getTargetBlock(player).getState() instanceof Sign){
                Sign e = (Sign) Methods.getTargetBlock(player).getState();
                if (
                        Objects.requireNonNull(e.getLine(0)).equalsIgnoreCase("Tptp")
                                && WarpConfig.get().contains("warps." + Objects.requireNonNull(e.getLine(1)))
                                && !Objects.requireNonNull(e.getLine(2)).equalsIgnoreCase("")
                ) {
                    e.setLine(0, "§f[§1Tptp§f]");
                    e.setLine(3, "§8(" + Objects.requireNonNull(e.getLine(1)) + ")");
                    e.setLine(1, "§r" + e.getLine(2));
                    e.setLine(2, "");
                    e.update();
                }
                else {
                    e.setLine(0, "");
                    if (Methods.getLocaleHu()) {
                        e.setLine(1, "§4Ez a Tptp");
                        e.setLine(2, "§4nem létezik.");
                    }
                    else{
                        e.setLine(1, "§4This Tptp does");
                        e.setLine(2, "§4not exists.");
                    }
                    e.setLine(3, "");
                    e.update();
                }
            }
            else{
                Methods.langBasedMessage(
                        "Nem található tábla.",
                        "Can't find any sign.",
                        BroadcastType.Sender, MessageType.Error, sender
                );
            }
        }
        else{
            Methods.langBasedMessage(
                    "Csak játékosok használhatják ezt a parancsot.",
                    "Only players are allowed to use this command.",
                    BroadcastType.Sender, MessageType.Error, sender
            );
        }
        return true;
    }
}
