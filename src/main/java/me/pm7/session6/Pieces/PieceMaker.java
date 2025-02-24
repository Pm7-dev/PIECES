package me.pm7.session6.Pieces;

import me.pm7.session6.Pieces.Color.PieceColor;
import me.pm7.session6.Pieces.Color.PieceColorPattern;
import me.pm7.session6.Session6;
import me.pm7.session6.Utils.AreaCalculator;
import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;

// You'd think I'd be satisfied with the first pun of "Piece Keeper," but no, I need more
public class PieceMaker {
    private static final Session6 plugin = Session6.getPlugin();

    private int difficulty = 0;
    private final int spawnHeight;
    private double spawnCountMultiplier;
    private int size;
    private double speed;
    private int secondsBeforeSpawn;
    private int secondsBeforeNextSpawn;
    private final Random random;
    private Integer taskID;

    public PieceMaker() {
        spawnHeight = 190; //190

        spawnCountMultiplier = 1.0;
        size = 20;
        speed = 8;
        secondsBeforeSpawn = 5;


        taskID = null;
        this.random = new Random();
        this.secondsBeforeNextSpawn = secondsBeforeSpawn;
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
        tickPieceSpawn();
        tickDifficulty();
    }

    private void tickPieceSpawn() {
        if(secondsBeforeNextSpawn <= 0) {

            List<Location> pLocs = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if(p.getGameMode() != GameMode.SPECTATOR) pLocs.add(p.getLocation());
            }
            if(pLocs.isEmpty()) return;

            double area = AreaCalculator.getTotalArea(pLocs);
            double spawnCount = area/Math.pow(AreaCalculator.BOUNDING_BOX_SIZE, 2);
            int finalSpawnCount = (int) (spawnCount * spawnCountMultiplier);
            for(int i=0; i<finalSpawnCount; i++) {
                Piece piece = createRandomPiece(pLocs);
                if (piece != null) secondsBeforeNextSpawn = secondsBeforeSpawn;
            }
        }
        secondsBeforeNextSpawn--;
    }

    private final int secondsBeforeDifficultyShift = 1080;
    private int secondsBeforeNextDifficultyShift = secondsBeforeDifficultyShift;
    private void tickDifficulty() {
        if(secondsBeforeNextDifficultyShift <= 0) {
            if(difficulty >= 5) return;
            setDifficulty(difficulty + 1);
        }
        secondsBeforeNextDifficultyShift--;
    }

    private Piece createRandomPiece(List<Location> pLocs) {
        for(int i=0; i<50; i++) {

            // Generate the data of the piece we are making
            PieceType type = PieceType.getRandom();
            boolean[][] model = type.getModel();
            model = PieceType.rotateModel(model, random.nextInt(4));
            model = PieceType.mirrorModel(model, Mirror.values()[random.nextInt(Mirror.values().length)]);
            int size = random.nextInt(this.size/2, this.size);
            double speed = random.nextDouble(-5.0, 5.0) + this.speed;
            PieceColor color = PieceColorPattern.getRandom().getColor();

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
                if (Math.abs(pLoc.getX() - spawn.getX()) - ((double) (model.length * size) / 2) <= AreaCalculator.BOUNDING_BOX_SIZE) {
                    if (Math.abs(pLoc.getZ() - spawn.getZ()) - ((double) (model.length * size) / 2) <= AreaCalculator.BOUNDING_BOX_SIZE) {
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
            double y = random.nextDouble() + spawnHeight;
            return new Piece(spawn.getWorld(), x, y, z, size, speed, model, color);
        }

        // If a spot couldn't be found after 50 tries, cancel this spawn to not pause the thread
        plugin.getLogger().log(Level.WARNING, "Piece spawn spot could not be found after 50 tries :(");
        return null;
    }

    public int getDifficulty() {return difficulty;}
    public void setDifficulty(int difficulty) {
        secondsBeforeNextDifficultyShift = secondsBeforeDifficultyShift;

        if(difficulty > this.difficulty) {
            Bukkit.broadcastMessage(ChatColor.RED + "The storm is worsening...");
            for(Player p : Bukkit.getOnlinePlayers()) {//TODO: sound
                p.getWorld().playSound(p.getLocation().clone().add(0, 500, 0), "insert_sound", 9999 ,1);
            }
        } else if (difficulty < this.difficulty) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "The storm seems to have eased a little...");
            for(Player p : Bukkit.getOnlinePlayers()) {//TODO: sound
                p.getWorld().playSound(p.getLocation().clone().add(0, 500, 0), "insert_sound", 9999 ,1);
            }
        } else return;

        this.difficulty = difficulty;

        //TODO: test and balance
        switch (this.difficulty) {
            default: {
                spawnCountMultiplier = 1.0;
                size = 20;
                speed = 8;
                secondsBeforeSpawn = 4;
                break;
            }
            case 1: {
                spawnCountMultiplier = 2.0;
                size = 22;
                speed = 10;
                secondsBeforeSpawn = 3;
                break;
            }
            case 2: {
                spawnCountMultiplier = 2.0;
                size = 24;
                speed = 15;
                secondsBeforeSpawn = 3;
                break;
            }
            case 3: {
                spawnCountMultiplier = 2.5;
                size = 26;
                speed = 15;
                secondsBeforeSpawn = 2;
                break;
            }
            case 4: {
                spawnCountMultiplier = 2.5;
                size = 28;
                speed = 17;
                secondsBeforeSpawn = 2;
                break;
            }
            case 5: {
                spawnCountMultiplier = 2.5;
                size = 30;
                speed = 20;
                secondsBeforeSpawn = 2;
                break;
            }
            case 6: { // DO NOT USE!! TESTING/FUN PURPOSES ONLY (mainly fun)
                spawnCountMultiplier = 4.0;
                size = 32;
                speed = 20;
                secondsBeforeSpawn = 2;
            }
        }
    }
}
