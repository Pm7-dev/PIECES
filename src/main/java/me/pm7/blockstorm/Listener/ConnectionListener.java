package me.pm7.blockstorm.Listener;

import me.pm7.blockstorm.BlockStorm;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class ConnectionListener implements Listener {
    private static final BlockStorm plugin = BlockStorm.getPlugin();

    private static final UUID uuid = UUID.fromString("0facade1-feed-cafe-fade-0decaf0beef0");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        p.addResourcePack(uuid, "https://github.com/Pm7-dev/PIECES/raw/refs/heads/main/storm_pack_1.21.9.zip", null, "hi there u need this :3", true);

        if(plugin.isStarted()) {
            plugin.getPieceKeeper().setImmunity(p, 240);
            plugin.getAnimationController().clearQueue(p.getUniqueId(), true);

        }
    }
}
