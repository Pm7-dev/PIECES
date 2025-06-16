package me.pm7.session6.LargeFernMinecart;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class RidingEntityManagement implements Listener {
    @EventHandler
    public void onEntityDie(EntityDeathEvent e) {
        List<Entity> passengers = e.getEntity().getPassengers();

        if(passengers.isEmpty()) {
            return;
        }

        for(Entity p : passengers) {
            if(p.getType() == EntityType.ITEM_DISPLAY) {
                p.remove();
            }
        }
    }


    @EventHandler
    public void h(CreatureSpawnEvent e) {
        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void s(EntityChangeBlockEvent e) {
        if(e.getEntity().getType() == EntityType.SILVERFISH) {
            if(e.getEntity().getScoreboardTags().contains("mite")) e.setCancelled(true);
        }
    }
}
