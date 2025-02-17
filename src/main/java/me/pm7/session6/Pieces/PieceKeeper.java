package me.pm7.session6.Pieces;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class PieceKeeper {
    private static final Session6 plugin = Session6.getPlugin();

    private final List<UUID> dead = new ArrayList<>();
    private Integer taskID = null;

    public void start() {
        if(taskID !=null) return;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::loop, 0L, 1L);
    }
    public void stop() {
        if(taskID==null) return;
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
    }

    private final Predicate<Entity> isPlayer = e -> e instanceof Player p && (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE);
    private int tick;
    private void loop() {
        for(Piece piece : Piece.getPieces()) {
            if(piece.isRunning()) {
                piece.moveDown();
                piece.tickColor();
                if(tick==0) piece.playAmbience();
            }
        }

        // collision detection
        for(Piece piece : Piece.getPieces()) {
            if(!piece.isRunning()) continue;

            double size = piece.getSize();
            double x = piece.getX();
            double z = piece.getZ();
            double y = piece.getY();

            for(int z1=0;z1<piece.getModelData().length;z1++) {
                for(int x1=0;x1<piece.getModelData().length;x1++) {
                    if(piece.getModelData()[z1][x1]) {
                        Location loc = new Location(piece.getWorld(),x+(x1*size)+(size/2),y+(size/2), z+(z1*size)+(size/2));
                        for(Entity entity : piece.getWorld().getNearbyEntities(loc, size/2, size/2, size/2, isPlayer)) {
                            Player p = (Player) entity;
                            dead.add(p.getUniqueId());
                            p.damage(99999);
                        }
                    }
                }
            }
        }

        tick++;
        if(tick>=3) tick = 0;
    }

    public List<UUID> getDead() {return dead;}
}
