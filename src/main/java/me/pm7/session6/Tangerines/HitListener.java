package me.pm7.session6.Tangerines;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class HitListener implements Listener {
    public static HashMap<UUID, Integer> scores = new HashMap<>();
    public static boolean enabled = true;

    @EventHandler
    public void onPlayerHit(ProjectileHitEvent e) {
        Projectile p = e.getEntity();
        if(p.getType() != EntityType.SNOWBALL) return;

        Snowball s = (Snowball) p;
        ItemStack item = s.getItem();
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;

        CustomModelDataComponent cmdc = meta.getCustomModelDataComponent();
        if(cmdc == null || !cmdc.getStrings().contains("tangerine")) return;

        if(!(e.getHitEntity() instanceof Player hit)) {return;}

        ProjectileSource ps = p.getShooter();
        if(!(ps instanceof Player shooter)) return;

        // Orange-ify the screen of the hit player
        if(enabled) hit.sendTitle("\uE000", "", 0, 5, 10);

        // add score to shooter
        UUID shooterUUID = shooter.getUniqueId();
        if(scores.containsKey(shooterUUID)) scores.put(shooterUUID, scores.get(shooterUUID) + 1);
        else scores.put(shooterUUID, 1);

        // sounds
        hit.playSound(hit.getLocation(), "tangerine:splat", SoundCategory.RECORDS, 1, 1);
        shooter.playSound(shooter.getLocation(), "tangerine:hit", SoundCategory.RECORDS, 1, 1);

        // score "+1" thingy animation
        new ScoreMarker(shooter, hit.getLocation());
    }

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        if(e.getEntity().getType() == EntityType.SNOWBALL) {
            if(e.getEntity().getShooter() instanceof Player p) {
                p.setCooldown(Material.SNOWBALL, 20);
            }
        }
    }

    // also throwing this in there
    @EventHandler
    public void onCraft(CraftItemEvent e) {
        ItemStack tangerine = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta meta = tangerine.getItemMeta();
        CustomModelDataComponent cmdp = meta.getCustomModelDataComponent();
        cmdp.setStrings(Collections.singletonList("tangerine"));
        meta.setItemName("Tangerine");
        meta.setRarity(ItemRarity.UNCOMMON);
        meta.setMaxStackSize(11);
        meta.setCustomModelDataComponent(cmdp);
        tangerine.setItemMeta(meta);

        if(e.getInventory().containsAtLeast(tangerine, 1)) {
            e.setCancelled(true);
        }
    }
}
