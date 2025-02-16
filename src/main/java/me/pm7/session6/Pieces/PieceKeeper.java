package me.pm7.session6.Pieces;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PieceKeeper {
    private static final Session6 plugin = Session6.getPlugin();
    private static final double soundDist = 4.0d;

    private final List<UUID> dead = new ArrayList<>();
    private Integer taskID = null;

    public void start() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::loop, 0L, 1L);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
    }

    private void loop() {
        for(Piece piece : Piece.getPieces()) {
            if(piece.isRunning()) piece.moveDown();
            piece.tickColor();
        }

        // collision detection & piece ambient sound possibility
        for(Player p : Bukkit.getOnlinePlayers()) {
            Location loc = p.getLocation();
            Location eyeLoc = p.getEyeLocation();

            // Gather a list of pieces that might be near the player based on some loose criteria
            List<Piece> nearbyPieces = new ArrayList<>();
            for(Piece piece : Piece.getPieces()) {
                if(piece.getY()-eyeLoc.getY()>soundDist||p.getEyeLocation().getY()-piece.getY()+piece.getSize()>soundDist) continue;
                if(piece.getX()-loc.getX()>soundDist||loc.getX()-piece.getX()+(piece.getSize()*piece.getModelData().length)>soundDist) continue;
                if(piece.getZ()-loc.getZ()>soundDist||loc.getZ()-piece.getZ()+(piece.getSize()*piece.getModelData().length)>soundDist) continue;
                nearbyPieces.add(piece);
            }

            for(Piece piece : nearbyPieces) {
                //TODO: get center of piece and play sound assuming you get a sound

                // If the bottom of the piece is between the player's head and feet, it's safe to be a collision
                if(loc.getY()>=piece.getY()&&piece.getY()<=eyeLoc.getY()) continue; //TODO: fix, this is broken lol
                for(int z=0; z<piece.getModelData().length; z++) {
                    for(int x=0; x<piece.getModelData().length; x++) {
                        if(piece.getModelData()[z][x]) {
                            //if(loc.getX())
                        }
                    }
                }
            }
        }
    }
}
