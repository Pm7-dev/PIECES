package me.pm7.session6.Listener;

import me.pm7.session6.Session6;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class DimensionChangeListener implements Listener {
    private static final Session6 plugin = Session6.getPlugin();

    @EventHandler
    public void onPlayerChangeDimension(PlayerPortalEvent e) {
        if(plugin.isStarted()) {
            Player p = e.getPlayer();
            plugin.getPieceKeeper().setImmunity(p, 100);
            plugin.getAnimationController().clearQueue(p.getUniqueId(), true);
        }
    }
}
