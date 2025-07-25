package me.pm7.blockstorm.Utils;

import me.pm7.blockstorm.Pieces.Piece;
import me.pm7.blockstorm.BlockStorm;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class AnimationController implements Listener {
    private static final BlockStorm plugin = BlockStorm.getPlugin();
    private static final NamespacedKey acKey = new NamespacedKey(plugin, "animation-controller");
    private Integer taskID = null;

    public void start() {
        if(taskID !=null) return;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::animationLoop, 0L, 1L);

        // Clear any previous animation frames
        for(Player p : Bukkit.getOnlinePlayers()) {
            ItemStack helmet = p.getInventory().getHelmet();
            if (helmet == null) {
                p.getInventory().setHelmet(createControllerItem());
                continue;
            }
            p.getInventory().setHelmet(null);
        }
    }
    public void stop() {
        if(taskID==null) return;
        this.stopping = true;
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Bukkit.getScheduler().cancelTask(taskID);
            taskID = null;
            for(Player p : Bukkit.getOnlinePlayers()) {
                ItemStack helmet = p.getInventory().getHelmet();
                if(helmet.getItemMeta().getPersistentDataContainer().has(acKey)) {
                    p.getInventory().setHelmet(new ItemStack(Material.AIR));
                }
            }
        }, 20L);
    }

    private int tick = 0;
    private boolean stopping = false;
    private void animationLoop() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            ItemStack helmet = p.getInventory().getHelmet();
            if(helmet == null) {
                p.getInventory().setHelmet(createControllerItem());
                continue;
            }

            HashMap<UUID, Piece> playersUnderPieces = Piece.getPlayersUnderPieces();
            if(playersUnderPieces.containsKey(p.getUniqueId())) {
                Piece lowestPiece = playersUnderPieces.get(p.getUniqueId());
                if (lowestPiece != null && !stopping) {
                    Location loc = p.getLocation();
                    double distance = lowestPiece.getY() - loc.getY();

                    if (distance < 0) {
                        playersUnderPieces.remove(p.getUniqueId());
                        continue;
                    }

                    float pitch = (float) ((distance / (3.2 * lowestPiece.getSpeed() * 3) * -0.3) + 1.2f); //*3 for 3 sections of sound?

                    int pulseSection = (int) Math.floor(distance / (lowestPiece.getSpeed() * 3.2)); // *3.2 at the end, now 6.4?
                    if (pulseSection < 3) {
                        int ticksBetweenPulses;
                        if (distance <= lowestPiece.getSpeed() * 1.4) { // Override when it gets *really* close for a stressful effect
                            ticksBetweenPulses = 2;
                            pitch += 0.05f;
                        } else ticksBetweenPulses = (int) Math.pow(2, pulseSection + 2);
                        if (tick % ticksBetweenPulses == 0) {
                            clearQueue(p.getUniqueId(), false);
                            for (int i = 0; i < 32; i += 5) addFrame(p.getUniqueId(), "warning_overlay/" + i);
                            addFrame(p.getUniqueId(), "warning_overlay/blank");
                            p.playSound(loc.clone().add(0, 2, 0), "pieces:warning", SoundCategory.RECORDS, 1, pitch);
                        }
                    }
                }
            }
            animate(helmet, p.getUniqueId());
        }

        tick++;
        if(tick >= 32) tick = 0;
    }

    private final HashMap<UUID, List<String>> frameQueues = new HashMap<>();
    private void animate(ItemStack item, UUID uuid) {

        if(!frameQueues.containsKey(uuid)) {
            frameQueues.put(uuid, new ArrayList<>());
            return;
        }

        List<String> frameQueue = frameQueues.get(uuid);
        if(frameQueue.isEmpty()) return;

        ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        EquippableComponent equippable = meta.getEquippable();
        equippable.setCameraOverlay(new NamespacedKey("pieces", frameQueue.getFirst()));
        meta.setEquippable(equippable);
        item.setItemMeta(meta);

        frameQueue.removeFirst();
    }

    public void clearQueue(UUID uuid, boolean putBlank) {
        frameQueues.get(uuid).clear();
        if(putBlank) addFrame(uuid, "warning_overlay/blank");
    }

    private void addFrame(UUID uuid, String frame) {
        frameQueues.get(uuid).add(frame);
    }

    @EventHandler
    public void onInvChange(InventoryClickEvent e) {
        ItemStack helmet = e.getWhoClicked().getInventory().getHelmet();
        if(helmet != null && helmet.equals(e.getCurrentItem())) {
            if(e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(acKey)) {
                e.setCurrentItem(null);
            }
        }
    }

    private static final List<Material> helmets = Arrays.asList(
            Material.CHAINMAIL_HELMET,
            Material.DIAMOND_HELMET,
            Material.GOLDEN_HELMET,
            Material.IRON_HELMET,
            Material.LEATHER_HELMET,
            Material.NETHERITE_HELMET,
            Material.TURTLE_HELMET
    );
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            Material mat = e.getPlayer().getInventory().getItemInMainHand().getType();
            if(helmets.contains(mat)) {
                e.setCancelled(true);
                return;
            }
            mat = e.getPlayer().getInventory().getItemInOffHand().getType();
            if(helmets.contains(mat)) e.setCancelled(true);
        }
    }

    private ItemStack createControllerItem() {
        ItemStack item = new ItemStack(Material.POPPED_CHORUS_FRUIT);
        ItemMeta meta = item.getItemMeta();
        meta.setItemName(" ");
        meta.setDisplayName(" ");

        meta.getPersistentDataContainer().set(acKey, PersistentDataType.BOOLEAN, true);

        EquippableComponent eq = meta.getEquippable();
        eq.setSwappable(false);
        eq.setSlot(EquipmentSlot.HEAD);
        eq.setAllowedEntities(EntityType.PLAYER);
        eq.setModel(new NamespacedKey(plugin, "none"));
        eq.setCameraOverlay(new NamespacedKey("pieces", "warning_overlay/blank"));
        eq.setDamageOnHurt(false);
        eq.setDispensable(false);
        meta.setEquippable(eq);

        CustomModelDataComponent cmdc = meta.getCustomModelDataComponent();
        cmdc.setStrings(List.of("animation_controller"));
        meta.setCustomModelDataComponent(cmdc);

        item.setItemMeta(meta);
        return item;
    }
}
