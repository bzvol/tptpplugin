package me.zbenjamin.tptpplugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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

}
