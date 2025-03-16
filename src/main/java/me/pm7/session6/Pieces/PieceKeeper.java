package me.pm7.session6.Pieces;

import me.pm7.session6.Session6;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Predicate;

public class PieceKeeper {
    private static final Session6 plugin = Session6.getPlugin();

    private static final HashMap<UUID, Integer> pieceInvincibilityTicks = new HashMap<>();
    private Integer taskID = null;
    private int endAnimationTick = 0;
    private boolean endingAnimation = false;

    public void start() {
        if(taskID !=null) return;
        endAnimationTick = 0;
        endingAnimation = false;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::loop, 0L, 1L);
    }
    public void stop() {
        if(taskID==null) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            endingAnimation = true;
            plugin.getAnimationController().stop();
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.getScheduler().cancelTask(taskID);
                taskID = null;
            }, 70L + 150L); //70 for the slowing down, and an extra 150 for the flashing/disappearing
        }, 60L); // Wait 60 ticks to make sure any pieces still spawning correctly finish
    }

    public boolean isRunning() {return taskID != null;}

    private int tick;
    private void loop() {
        if(endingAnimation) endAnimationTick++;

        List<Piece> remove = new ArrayList<>();
        for(Piece piece : Piece.getPieces()) {
            if(!piece.isRunning()) continue;
            piece.moveDown();

            // Color animation/player collisions can stop after the slowing down has finished
            if(endAnimationTick > 55) continue;

            piece.tickColor();
            if(piece.shouldRemove()) remove.add(piece);
        }

        // Remove pieces that are marked for removal
        while(!remove.isEmpty()) {
            remove.getFirst().kill();
            remove.removeFirst();
        }

        // Tick down player invincibility
        for(Map.Entry<UUID, Integer> entry : pieceInvincibilityTicks.entrySet()) {
            Player p = Bukkit.getPlayer(entry.getKey());
            if(p == null) continue;
            if(entry.getValue() >= 0) {

                // Send hotbar message
                if(entry.getValue()==0) p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(" "));
                else {
                    int remainingTicks = entry.getValue();
                    int seconds = (int) Math.floor((double)remainingTicks/20);
                    int minutes = (int) Math.floor((double)seconds/60);
                    seconds %= 60;
                    String stringSeconds = String.valueOf(seconds);
                    if(stringSeconds.length() == 1) stringSeconds = "0" + stringSeconds;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Falling Block Invincibility: " + minutes + ":" + stringSeconds));
                }

                entry.setValue(entry.getValue()-1);
            }
        }

        tick++;
        if(tick>=3) tick = 0;
    }

    public void setImmunity(Player p, int ticks) {
        pieceInvincibilityTicks.put(p.getUniqueId(), ticks);
    }

    public static HashMap<UUID, Integer> getPieceInvincibilityTicks() {
        return pieceInvincibilityTicks;
    }

    public int getEndAnimationTick() {return endAnimationTick;}
}
