package me.pm7.blockstorm.Listener;

import me.pm7.blockstorm.BlockStorm;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class DimensionChangeListener implements Listener {
    private static final BlockStorm plugin = BlockStorm.getPlugin();

    @EventHandler
    public void onPlayerChangeDimension(PlayerPortalEvent e) {
        if(plugin.isStarted()) {
            Player p = e.getPlayer();
            plugin.getPieceKeeper().setImmunity(p, 100);
            plugin.getAnimationController().clearQueue(p.getUniqueId(), true);
        }
    }
}
