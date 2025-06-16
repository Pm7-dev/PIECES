package me.pm7.session6.Granite;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Uninvisible implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(e.getPlayer().isInvisible()) {
            e.getPlayer().setInvisible(false);
        }
    }
}
