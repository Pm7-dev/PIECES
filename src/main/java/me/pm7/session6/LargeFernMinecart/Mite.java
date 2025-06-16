package me.pm7.session6.LargeFernMinecart;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.UUID;

public class Mite {

    private final UUID miteUUID;
    private final UUID displayUUID;
    private final Pack pack;

    public Mite(Location loc, Pack pack) {

        loc = loc.add((Math.random()-0.5d)*2, 0, (Math.random()-0.5d)*2);
        loc.setY(loc.getWorld().getHighestBlockAt(loc).getLocation().getY() + 1.5);

        Silverfish m = (Silverfish) loc.getWorld().spawnEntity(loc, EntityType.SILVERFISH);
        m.getAttribute(Attribute.SCALE).setBaseValue(1.5);
        m.setSilent(true);
        m.setHealth(2.5d);
        m.setCustomName("Large Fern Minecart");
        m.setCustomNameVisible(false);
        m.setPortalCooldown(Integer.MAX_VALUE);
        m.setRemoveWhenFarAway(false);
        m.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, -1, 1, true, false));
        m.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, -1, 1, true, false));
        m.getScoreboardTags().add("mite");

        ItemDisplay display = (ItemDisplay) loc.getWorld().spawnEntity(loc, EntityType.ITEM_DISPLAY);
        Transformation t = new Transformation(
                new Vector3f(0, 0, 0),
                new AxisAngle4f(),
                new Vector3f(0.65f, 0.65f, 0.65f),
                new AxisAngle4f()
        );
        display.setTransformation(t);
        ItemStack item = new ItemStack(Material.POPPED_CHORUS_FRUIT, 1);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent cmdc = meta.getCustomModelDataComponent();
        cmdc.setStrings(Collections.singletonList("large_fern_minecart"));
        meta.setCustomModelDataComponent(cmdc);
        item.setItemMeta(meta);
        display.setItemStack(item);
        display.setTeleportDuration(5);
        displayUUID = display.getUniqueId();
        miteUUID = m.getUniqueId();

        m.addPassenger(display);

        this.pack = pack;
    }

    public Silverfish getMite() {
        return (Silverfish) Bukkit.getEntity(miteUUID);
    }

    public ItemDisplay getDisplay() {
        return (ItemDisplay) Bukkit.getEntity(displayUUID);
    }

    public void tick() {
        getDisplay().setRotation(getMite().getLocation().getYaw() + 90, 0);
    }

    public void kill() {
        Silverfish mite = getMite();
        if(mite!=null) {
            mite.remove();
        }

        ItemDisplay display = getDisplay();
        if(display!=null) {
            display.remove();
        }

        pack.getMites().remove(this);
    }
}
