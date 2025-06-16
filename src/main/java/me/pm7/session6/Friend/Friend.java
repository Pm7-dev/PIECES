package me.pm7.session6.Friend;

import me.pm7.session6.Session6;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.*;

public class Friend {
    private static final Session6 plugin = Session6.getPlugin();

    private final UUID ownerUUID;
    private final UUID displayUUID;
    private final UUID hitboxUUID;
    private final Random r = new Random();
    private final Integer taskID;

    public static List<Friend> friends = new ArrayList<>();

    private int invincibilityTicks;

    public Friend(Player owner) {
        ownerUUID = owner.getUniqueId();

        Location spawnLoc = owner.getLocation().clone().add(0, 50, 0);
        spawnLoc.setPitch(90.0f);
        spawnLoc.setYaw(0);
        ItemDisplay d = (ItemDisplay) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.ITEM_DISPLAY);
        ItemStack item = new ItemStack(Material.POPPED_CHORUS_FRUIT, 1);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent cmdc = meta.getCustomModelDataComponent();
        if(r.nextBoolean()) cmdc.setStrings(Collections.singletonList("friend"));
        else cmdc.setStrings(Collections.singletonList("friend2"));
        meta.setCustomModelDataComponent(cmdc);
        item.setItemMeta(meta);
        d.setItemStack(item);
        d.setTeleportDuration(5);
        displayUUID = d.getUniqueId();

        Interaction i = (Interaction) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.INTERACTION);
        i.setInteractionWidth(1.0f);
        i.setInteractionHeight(1.0f);
        hitboxUUID = i.getUniqueId();

        friends.add(this);

        invincibilityTicks = 4;

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::tick, 0L, 5L);
    }

    private int soundtick = 0;
    private void tick() {
        if(invincibilityTicks > 0) invincibilityTicks--;

        Player owner = getOwner();
        ItemDisplay display = getDisplay();
        Interaction hitbox = getHitbox();

        if(owner==null || display==null || hitbox==null) {
            return;
        }

        Location ploc = owner.getEyeLocation();
        Location floc = display.getLocation();
        double xDist = ploc.getX()-floc.getX();
        if(Math.abs(xDist) > 1.75) xDist = (xDist/Math.abs(xDist)) * 1.75;
        double yDist = ploc.getY()-floc.getY();
        if(Math.abs(yDist) > 1.75) yDist = (yDist/Math.abs(yDist)) * 1.75;
        double zDist = ploc.getZ()-floc.getZ();
        if(Math.abs(zDist) > 1.75) zDist = (zDist/Math.abs(zDist)) * 1.75;

        Location newLoc = ploc.subtract(xDist, yDist, zDist);

        newLoc.setPitch(r.nextFloat(-90, 90));
        newLoc.setYaw(r.nextFloat(-180, 180));

        display.teleport(newLoc);
        hitbox.teleport(newLoc.clone().subtract(0,  hitbox.getInteractionHeight()/2, 0));

        soundtick++;

        if(soundtick >= 5) {
            if(r.nextBoolean()) {
                newLoc.getWorld().playSound(floc, "friend:ambient", SoundCategory.RECORDS, 0.5f, 1);
                soundtick = 0;
            }
        }
    }

    public Player getOwner() {
        return Bukkit.getPlayer(ownerUUID);
    }

    private ItemDisplay getDisplay() {
        return (ItemDisplay) Bukkit.getEntity(displayUUID);
    }

    public Interaction getHitbox() {
        return (Interaction) Bukkit.getEntity(hitboxUUID);
    }

    public void kill() {
        if (invincibilityTicks > 0) return;

        Bukkit.getScheduler().cancelTask(taskID);

        Player p = getOwner();
        if (p == null) return;

        ItemDisplay display = getDisplay();
        if (display == null) return;

        ItemStack item = new ItemStack(Material.POPPED_CHORUS_FRUIT, 1);
        ItemMeta meta = item.getItemMeta();
        CustomModelDataComponent cmdc = meta.getCustomModelDataComponent();
        cmdc.setStrings(Collections.singletonList("friend3"));
        meta.setCustomModelDataComponent(cmdc);
        item.setItemMeta(meta);
        display.setItemStack(item);

        p.stopSound("friend:ambient");
        p.playSound(display.getLocation(), "friend:scream", 20000, 1);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            display.remove();
            getHitbox().remove();
            friends.remove(this);
        }, 5L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            p.sendMessage(ChatColor.GREEN + "You successfully killed your friend!");
            p.playSound(p.getLocation().clone().add(0, 2, 0), "pieces:storm_watch_notif", SoundCategory.RECORDS, 1, 1);
        }, 55L);

    }
}
