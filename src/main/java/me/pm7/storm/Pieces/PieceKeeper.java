package me.pm7.storm.Pieces;

import me.pm7.storm.Storm;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.*;

public class PieceKeeper {
    private static final Storm plugin = Storm.getPlugin();

    private static final HashMap<UUID, Integer> pieceInvincibilityTicks = new HashMap<>();
    private Integer taskID = null;

    public void start() {
        if(taskID !=null) return;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::loop, 0L, 1L);
    }
    public void stop() {
        pieceInvincibilityTicks.clear();
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
    }

    public boolean isRunning() {return taskID != null;}

    private int tick;
    private void loop() {

        List<Piece> remove = new ArrayList<>();
        for(Piece piece : Piece.getPieces()) {
            if(!piece.isRunning()) continue;
            piece.moveDown();

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
        if(pieceInvincibilityTicks.containsKey(p.getUniqueId())) {
            Integer value = pieceInvincibilityTicks.get(p.getUniqueId());
            if(value != null && value > ticks) return;
        }
        pieceInvincibilityTicks.put(p.getUniqueId(), ticks);
    }

    public static HashMap<UUID, Integer> getPieceInvincibilityTicks() {
        return pieceInvincibilityTicks;
    }
}
