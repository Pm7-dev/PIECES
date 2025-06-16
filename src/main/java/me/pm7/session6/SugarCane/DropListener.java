package me.pm7.session6.SugarCane;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DropListener implements Listener {
    @EventHandler
    public void onEntityDie(EntityDeathEvent e) {
        if(e.getEntity().getType() != EntityType.BAT) return;
        if(e.getEntity().getScoreboardTags().contains("sugarcane")) {
            for(Entity p : e.getEntity().getPassengers()) {
                p.remove();
            }
            e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.SUGAR_CANE, 1));
        }
    }
}
