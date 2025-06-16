package me.pm7.session6.Granite;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BlockPlayer {
    private static final Session6 plugin = Session6.getPlugin();

    private static final List<Material> rocks = Arrays.asList(
            Material.STONE,
            Material.GRANITE,
            Material.DIORITE,
            Material.ANDESITE,
            Material.DEEPSLATE,
            Material.TUFF,
            Material.CALCITE,
            Material.GRAVEL
    );

    private final UUID displayUUID;
    private final UUID playerUUID;
    private final Integer taskID;

    public static final List<BlockPlayer> blockPlayers = new ArrayList<>();

    public BlockPlayer(Player p) {
        p.setInvisible(true);

        Location spawn = p.getLocation().clone();
        spawn.setPitch(0);
        spawn.setYaw(0);
        BlockDisplay bd = (BlockDisplay) p.getWorld().spawnEntity(spawn, EntityType.BLOCK_DISPLAY);
        bd.setTransformation(new Transformation(new Vector3f(-0.35f, -1.1f, -0.35f), new AxisAngle4f(), new Vector3f(0.7f, 1.3f, 0.7f), new AxisAngle4f()));
        bd.setBlock(rocks.get((int) (Math.random() * rocks.size())).createBlockData());
        bd.setTeleportDuration(1);
        displayUUID = bd.getUniqueId();

        playerUUID = p.getUniqueId();

        p.addPassenger(bd);

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::loop, 0L, 1L);

        blockPlayers.add(this);
    }

    private void loop() {
        BlockDisplay d = getDisplay();
        if(d==null) {
            kill();
            return;
        }

        Player p = (Player) d.getVehicle();
        if(p==null) {
            kill();
            return;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 1, true));
        Location ploc = p.getLocation();
        d.setRotation(ploc.getYaw(), 0);

        if(Math.abs(p.getEyeLocation().getY()-ploc.getY()) < 0.5) { // swim/crawl
            d.setTransformation(new Transformation(new Vector3f(-0.35f, -0.6f, -0.175f), new AxisAngle4f(), new Vector3f(0.7f, 1.3f, 0.7f), new AxisAngle4f()));
            d.setRotation(ploc.getYaw(), 90);
        } else {
            d.setTransformation(new Transformation(new Vector3f(-0.35f, -1.1f, -0.35f), new AxisAngle4f(), new Vector3f(0.7f, 1.3f, 0.7f), new AxisAngle4f()));
            d.setRotation(ploc.getYaw(), 0);
        }
    }

    public void setMaterial(Material mat) {
        BlockDisplay d = getDisplay();
        if(d==null) return;
        d.setBlock(mat.createBlockData());
    }

    private BlockDisplay getDisplay() {
        return (BlockDisplay) Bukkit.getEntity(displayUUID);
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    public void kill() {
        Bukkit.getScheduler().cancelTask(taskID);
        getDisplay().remove();
        getPlayer().setInvisible(false);
        blockPlayers.remove(this);
    }
}
