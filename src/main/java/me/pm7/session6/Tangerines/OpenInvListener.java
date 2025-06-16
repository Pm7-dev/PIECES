package me.pm7.session6.Tangerines;

import me.pm7.session6.Friend.Friend;
import org.bukkit.entity.Interaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.UUID;

// waah waah organization police
// this also handles other interactions between interaction entities

public class OpenInvListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        if(e.getRightClicked() instanceof Interaction i) {
            UUID uuid = i.getUniqueId();
            for(Dispensary d : Dispensary.dispensaries) {
                if(d.interactionUUID.equals(uuid)) {
                    e.getPlayer().openInventory(d.getInventory());
                    return;
                }
            }
//            for(Friend f : Friend.friends) {
//                if(f.getHitbox().getUniqueId().equals(uuid)) {
//
//                }
//            }
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Interaction i) {
            UUID uuid = i.getUniqueId();
            for(Friend f : Friend.friends) {
                if(f.getHitbox().getUniqueId().equals(uuid)) {
                    if(f.getOwner().getUniqueId().equals(e.getDamager().getUniqueId())) {
                        f.kill();
                    }
                }
            }
        }
    }
}
