package me.zbenjamin.tptpplugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class Methods {
    public static Block getTargetBlock(Player player) {
        return getTargetBlock(player, 0);
    }

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

}
