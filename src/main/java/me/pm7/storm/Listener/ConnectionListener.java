package me.pm7.storm.Listener;

import me.pm7.storm.Storm;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {
    private static final Storm plugin = Storm.getPlugin();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(plugin.isStarted()) {
            Player p = e.getPlayer();
            plugin.getPieceKeeper().setImmunity(p, 240);
            plugin.getAnimationController().clearQueue(p.getUniqueId(), true);
        }
    }
}
