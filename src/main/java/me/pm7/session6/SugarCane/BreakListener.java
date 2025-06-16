package me.pm7.session6.SugarCane;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Bat;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class BreakListener implements Listener {
    private static final Session6 plugin = Session6.getPlugin();

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();
        if(p==null) return;

        if(e.getBlock().getType() != Material.SUGAR_CANE) return;

        e.setDropItems(false);

        Location sLoc = e.getBlock().getLocation().clone().add(0.5, 0, 0.5);
        Bat b = (Bat) sLoc.getWorld().spawnEntity(sLoc, EntityType.BAT);
        b.setHealth(0.1);
        b.setRemoveWhenFarAway(false);
        b.getScoreboardTags().add("sugarcane");
        b.getAttribute(Attribute.SCALE).setBaseValue(1.5);
        b.setSilent(true);
        b.setPersistent(true);
        b.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PotionEffect.INFINITE_DURATION, 1, true));
        b.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1, true));

        BlockDisplay d = (BlockDisplay) sLoc.getWorld().spawnEntity(sLoc, EntityType.BLOCK_DISPLAY);
        d.setBlock(Material.SUGAR_CANE.createBlockData());
        d.setTransformation(new Transformation(
                new Vector3f(-0.5f, -1.25f, -0.5f),
                new AxisAngle4f(),
                new Vector3f(1.1f, 1.1f, 1.1f),
                new AxisAngle4f()
        ));

        b.addPassenger(d);
    }
}
