package me.pm7.session6.Pieces;

import me.pm7.session6.Pieces.Color.PieceColor;
import me.pm7.session6.Pieces.Color.PieceColorPattern;
import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.structure.Mirror;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;

// You'd think I'd be satisfied with the first pun of "Piece Keeper," but no, I need more
public class PieceMaker {
    private static final Session6 plugin = Session6.getPlugin();
    private static final int maxPlayerDistanceFromSpawn = 128;

    private int spawnHeight;
    private int minSize;
    private int maxSize;
    private double minSpeed;
    private double maxSpeed;
    private int minSecondsBeforeSpawn;
    private int maxSecondsBeforeSpawn;
    private int secondsBeforeSpawn;
    private final Random random;
    private Integer taskID;

    public PieceMaker() {

        // TODO: get good defaults for all of these
        spawnHeight = 190;

        minSize = 3;
        maxSize = 20;

        minSpeed = 12;
        maxSpeed = 26;

        minSecondsBeforeSpawn = 1; // ESPECIALLY REWRITE THESE
        maxSecondsBeforeSpawn = 1;


        taskID = null;
        this.random = new Random();
        this.secondsBeforeSpawn = random.nextInt(minSecondsBeforeSpawn, maxSecondsBeforeSpawn + 1);
    }

    public void start() {
        if(taskID != null) return;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::loop, 200L, 20L);
    }

    public void stop() {
        if(taskID == null) return;
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
    }

    private void loop() {
        if(secondsBeforeSpawn <= 0) {
            Piece piece = createRandomPiece();
            if(piece != null) {
                secondsBeforeSpawn = random.nextInt(minSecondsBeforeSpawn, maxSecondsBeforeSpawn + 1);
            }
        }
        secondsBeforeSpawn--;
    }

    private Piece createRandomPiece() {
        for(int i=0; i<50; i++) {

            // Generate the data of the piece we are making
            PieceType type = PieceType.getRandom();
            boolean[][] model = type.getModel();
            model = PieceType.rotateModel(model, random.nextInt(4));
            model = PieceType.mirrorModel(model, Mirror.values()[random.nextInt(Mirror.values().length)]);
            int size = random.nextInt(minSize, maxSize + 1);
            double speed = random.nextDouble(minSpeed, maxSpeed);
            PieceColor color = PieceColorPattern.getRandom().getColor();

            // Get a list of player locations
            List<Location> pLocs = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) pLocs.add(p.getLocation());
            if(pLocs.isEmpty()) { //exit early if no players are online
                return null;
            }

            // Find a spot to make the piece
            List<Chunk> chunks = new ArrayList<>();
            for (World w : Bukkit.getWorlds()) Collections.addAll(chunks, w.getLoadedChunks());
            Chunk chunk = chunks.get(random.nextInt(chunks.size()));
            int xShift = random.nextInt(17);
            int zShift = random.nextInt(17);
            Location spawn = new Location(chunk.getWorld(), chunk.getX() * 16 + xShift, 0, chunk.getZ() * 16 + zShift);

            // Make sure the spot is somewhat close to some player
            boolean nearby = false;
            for (Location pLoc : pLocs) {
                if (pLoc.getWorld() != spawn.getWorld()) continue;
                if (Math.abs(pLoc.getX() - spawn.getX()) - ((double) (model.length * size) / 2) <= maxPlayerDistanceFromSpawn) {
                    if (Math.abs(pLoc.getZ() - spawn.getZ()) - ((double) (model.length * size) / 2) <= maxPlayerDistanceFromSpawn) {
                        nearby = true;
                        break;
                    }
                }
            }
            if (!nearby) continue;

            // Make sure the piece is not colliding with any other existing pieces upon spawning
            // TODO: Write this plz

            // Create one piece.
            int x = (int) (spawn.getX()-((double)(model.length*size)/2));
            int z = (int) (spawn.getZ()-((double)(model.length*size)/2));
            return new Piece(spawn.getWorld(), x, spawnHeight, z, size, speed, model, color);
        }

        // If a spot couldn't be found after 50 tries, cancel this spawn to not pause the thread
        plugin.getLogger().log(Level.WARNING, "Piece spawn spot could not be found after 50 tries :(");
        return null;
    }
}
