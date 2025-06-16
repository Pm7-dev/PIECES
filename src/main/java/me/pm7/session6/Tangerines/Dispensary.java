package me.pm7.session6.Tangerines;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.*;

public class Dispensary implements InventoryHolder {
    private static final Session6 plugin = Session6.getPlugin();

    public static List<Dispensary> dispensaries = new ArrayList<>();
    public static void startloop() {Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
        if(dispensaries == null) dispensaries = new ArrayList<>();

        for(Dispensary d : dispensaries) {
            d.fill();
        }
    }, 0L, 5L);}


    private final Inventory inv;
    public final UUID interactionUUID;
    private final UUID displayUUID;

    public Dispensary(Vector loc) {
        Location l = new Location(Bukkit.getWorlds().getFirst(), loc.getX(), loc.getY(), loc.getZ());
        l.getWorld().addPluginChunkTicket(l.getChunk().getX(), l.getChunk().getZ(), plugin);

        inv = Bukkit.createInventory(this, 9, "Tangerine Dispensary");
        Interaction i = (Interaction) l.getWorld().spawnEntity(l, EntityType.INTERACTION);
        interactionUUID = i.getUniqueId();
        i.setInteractionHeight(1);
        i.setInteractionWidth(1);

        BlockDisplay d = (BlockDisplay) l.getWorld().spawnEntity(l, EntityType.BLOCK_DISPLAY);
        d.setBlock(Material.PURPLE_SHULKER_BOX.createBlockData());
        Transformation t = new Transformation(
                new Vector3f(-0.5f, 0, -0.5f),
                new AxisAngle4f(),
                new Vector3f(1, 1, 1),
                new AxisAngle4f()
        );
        d.setTransformation(t);
        d.setTeleportDuration(5);
        displayUUID = d.getUniqueId();

        TextDisplay td = (TextDisplay) l.getWorld().spawnEntity(l, EntityType.TEXT_DISPLAY);
        td.setText("OwO"); // acceptable text to hide I think
        td.setBackgroundColor(Color.fromRGB(255, 136, 26));
        td.setViewRange(9999999999.9f);
        td.setBillboard(Display.Billboard.VERTICAL);
        td.setTextOpacity((byte) 20);
        td.setAlignment(TextDisplay.TextAlignment.CENTER);
        Transformation t2 = new Transformation(
                new Vector3f(0, 0, 0),
                new AxisAngle4f(),
                new Vector3f(1, 100000, 1),
                new AxisAngle4f()
        );
        td.setTransformation(t2);

        dispensaries.add(this);
    }

    public void fill() {
        ItemStack tangerine = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta meta = tangerine.getItemMeta();
        CustomModelDataComponent cmdp = meta.getCustomModelDataComponent();
        cmdp.setStrings(Collections.singletonList("tangerine"));
        meta.setItemName("Tangerine");
        meta.setRarity(ItemRarity.UNCOMMON);
        meta.setMaxStackSize(11);
        meta.setCustomModelDataComponent(cmdp);
        tangerine.setItemMeta(meta);

        inv.addItem(tangerine);

        BlockDisplay display = (BlockDisplay) Bukkit.getEntity(displayUUID);
        Location l = display.getLocation().clone();
        l.setYaw(l.getYaw() + 90);
        display.teleport(l);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

}
