package me.pm7.session6.Tangerines;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Salmon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Random;

public class FishBigInator implements Listener {
    private final Random r = new Random();
    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if(e.getEntityType() == EntityType.SALMON) {
            Salmon s = (Salmon) e.getEntity();
            if(s.getVariant() == Salmon.Variant.MEDIUM) {
                s.getAttribute(Attribute.SCALE).setBaseValue(r.nextDouble(0.5, 4.2));
            }
        }
    }
}
