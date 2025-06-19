package me.pm7.storm.Listener;

import me.pm7.storm.Storm;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class ConnectionListener implements Listener {
    private static final Storm plugin = Storm.getPlugin();

    private static final String sha1string = "55e4cb58e557efeeea6da699cb58f545ea19bc7c";
    private static final UUID uuid = UUID.fromString("0facade0-feed-cafe-fade-0decaf0beef0");
    byte[] sha1 = null;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        if(sha1 == null) {
            int len = sha1string.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(sha1string.charAt(i), 16) << 4)
                        + Character.digit(sha1string.charAt(i+1), 16));
            }
            sha1 = data;
        }

        Player p = e.getPlayer();
        p.addResourcePack(uuid, "https://raw.githubusercontent.com/Pm7-dev/PIECES/refs/heads/main/storm_pack.zip", sha1, "hi there u need this :3", true);

        if(plugin.isStarted()) {
            plugin.getPieceKeeper().setImmunity(p, 240);
            plugin.getAnimationController().clearQueue(p.getUniqueId(), true);

        }
    }
}
