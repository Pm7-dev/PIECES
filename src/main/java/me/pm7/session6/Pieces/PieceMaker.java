package me.pm7.session6.Pieces;

import me.pm7.session6.Pieces.Color.PieceColor;
import me.pm7.session6.Pieces.Color.PieceColorPattern;
import me.pm7.session6.Session6;
import me.pm7.session6.Utils.AreaCalculator;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.entity.Player;

import java.util.*;

// You'd think I'd be satisfied with the first pun of "Piece Keeper," but no, I need more
public class PieceMaker {
    private static final Session6 plugin = Session6.getPlugin();

    private int difficulty = 0;
    private int spawnHeight;
    private double spawnCountMultiplier;
    private int size;
    private double speed;
    private int secondsBeforeSpawn;
    private int secondsBeforeNextSpawn;
    private final Random random;
    private Integer taskID;

    private boolean funky = false;

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

    private int boringTimer = 0; // Counts up to Funky Mode
    private static final int FUNKY_TIME = 180;
    private void loop() {

        // This became such a mess super quickly lol
        if(boringTimer <= FUNKY_TIME + 5) boringTimer++;
        if(boringTimer == FUNKY_TIME - 3) {
            serverMessageAndSound("3","pieces:funk.funk_loading", false);
            for(Player p : Bukkit.getOnlinePlayers()) p.closeInventory();
        }
        if(boringTimer == FUNKY_TIME - 2) serverMessageAndSound("2","pieces:funk.funk_loading", false);
        if(boringTimer == FUNKY_TIME - 1) serverMessageAndSound("1","pieces:funk.funk_loading", false);
        if(boringTimer == FUNKY_TIME) {
            funky = true;
            serverMessageAndSound("Funky mode activated!","pieces:funk.funk_activate", true);
        }
        if(boringTimer == FUNKY_TIME + 4) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                BaseComponent[] component = new ComponentBuilder()
                        .append("[Funky Mode]: Confused? ").color(ChatColor.GOLD.asBungee())
                        .append("Click to check our FAQ!").color(ChatColor.GOLD.asBungee()).underlined(true).bold(true)
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/thatonething"))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to see the Funky Mode FAQ!").color(ChatColor.GREEN.asBungee()).create()))
                        .create();
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(component);
                    Location soundLoc = p.getLocation().clone().add(0, 500, 0);
                    p.playSound(soundLoc, "pieces:funk.faq", 99999, 1);
                }
            }, 10L);
        }

        tickPieceSpawn();
        tickDifficulty();
    }

    private void serverMessageAndSound(String message, String sound, boolean hold) { //Plays a sound to the entire server
        for(Player p : Bukkit.getOnlinePlayers()) {
            Location soundLoc = p.getLocation().clone().add(0, 500, 0);
            p.playSound(soundLoc, sound, 99999, 1);
            if(hold) p.sendTitle("", ChatColor.GREEN + message, 0, 50, 20);
            else p.sendTitle("", ChatColor.GREEN + message, 0, 0, 25);
        }
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
                Piece piece = createRandomPiece();
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

    private Piece createRandomPiece() {
        for(int i=0; i<30; i++) {

            // Generate the data of the piece we are making
            PieceType type;
            if(!funky) type = PieceType.getBoring();
            else type = PieceType.getRandom();
            boolean[][] model = type.getModel();
            model = PieceType.rotateModel(model, random.nextInt(4));
            model = PieceType.mirrorModel(model, Mirror.values()[random.nextInt(Mirror.values().length)]);
            int size = random.nextInt(this.size/2, this.size);
            double speed = random.nextDouble(-5.0, 5.0) + this.speed;

            PieceColor color; // COLOR !!
            if(!funky) color = PieceColorPattern.getRandomBoring().getColor();
            else color = PieceColorPattern.getRandom().getColor();

            // Wow this method really was stupid
//            // Find a spot to make the piece
//            List<Chunk> chunks = new ArrayList<>();
//            for (World w : Bukkit.getWorlds()) Collections.addAll(chunks, w.getLoadedChunks());
//            Chunk chunk = chunks.get(random.nextInt(chunks.size()));
//            int xShift = random.nextInt(16);
//            int zShift = random.nextInt(16);
//            Location spawn = new Location(chunk.getWorld(), chunk.getX() * 16 + xShift, 0, chunk.getZ() * 16 + zShift);
//
//            // Make sure the spot is somewhat close to some player
//            boolean nearby = false;
//            for (Location pLoc : pLocs) {
//                if (pLoc.getWorld() != spawn.getWorld()) continue;
//                if (Math.abs(pLoc.getX() - spawn.getX()) - ((double) (model.length * size) / 2) <= AreaCalculator.BOUNDING_BOX_SIZE) {
//                    if (Math.abs(pLoc.getZ() - spawn.getZ()) - ((double) (model.length * size) / 2) <= AreaCalculator.BOUNDING_BOX_SIZE) {
//                        nearby = true;
//                        break;
//                    }
//                }
//            }
//            if (!nearby) continue;

            List<Player> plrs = (List<Player>) Bukkit.getOnlinePlayers();
            Player rand = plrs.get(random.nextInt(plrs.size()));
            int lx = rand.getLocation().getBlockX() + (random.nextInt(AreaCalculator.BOUNDING_BOX_SIZE) - (AreaCalculator.BOUNDING_BOX_SIZE/2));
            int lz = rand.getLocation().getBlockZ() + (random.nextInt(AreaCalculator.BOUNDING_BOX_SIZE) - (AreaCalculator.BOUNDING_BOX_SIZE/2));
            Location spawn = new Location(rand.getWorld(), lx, 0, lz);

            // Get spawn location data
            int x = (int) (spawn.getX()-((double)(model.length*size)/2));
            int z = (int) (spawn.getZ()-((double)(model.length*size)/2));
            double y = spawnHeight;

            // Gather a list of pieces that might collide with this piece if it spawns in the planned location
            List<Piece> potentialCollisions = new ArrayList<>();
            for(Piece piece : Piece.getPieces()) {
                if(piece.isRunning()) continue; // only run for pieces that are also spawning
                int totalLength = model.length * size;
                int pieceTotalLength = piece.getModelData().length * piece.getSize();
                if((x<=piece.getX() && x+totalLength>piece.getX()) || (x>=piece.getX() && x<piece.getX()+pieceTotalLength)) {
                    if((z<=piece.getZ() && z+totalLength>piece.getZ()) || (z>=piece.getZ() && z<piece.getZ()+pieceTotalLength)) {
                        potentialCollisions.add(piece);
                    }
                }
            }

            // Check for a more precise collision between the collision candidates
            boolean collision = false;
            for(Piece pC : potentialCollisions) {
                boolean[][] pcModel = pC.getModelData();

                for(int pcX=0; pcX<pcModel.length; pcX++) {
                    for(int pcZ=0; pcZ<pcModel.length; pcZ++) {
                        if(!pcModel[pcZ][pcX]) continue;

                        for(int sX=0; sX<model.length; sX++) {
                            for(int sZ=0; sZ<model.length; sZ++) { // 5 for loops deep, wowzers
                                if(!model[sZ][sX]) continue;

                                // These two if statements might be my least favorite ever.
                                if(x+(sX*size)<=pC.getX()+(pcX*pC.getSize()) && x+((sX+1)*size)>pC.getX()+(pcX*pC.getSize()) || (x+(sX*size)>=pC.getX()+(pcX*pC.getSize()) && x+(sX*size)<pC.getX()+((pcX+1)*pC.getSize()))) {
                                    if(z+(sZ*size)<=pC.getZ()+(pcZ*pC.getSize()) && z+((sZ+1)*size)>pC.getZ()+(pcZ*pC.getSize()) || (z+(sZ*size)>=pC.getZ()+(pcZ*pC.getSize()) && z+(sZ*size)<pC.getZ()+((pcZ+1)*pC.getSize()))) {
                                        collision = true;
                                        break;
                                    }
                                }
                            }if(collision) break;
                        }if(collision) break;
                    }if(collision) break;
                }if(collision) break;
            }if(collision) continue; // break;ing the pattern


            return new Piece(spawn.getWorld(), x, y, z, size, speed, model, color);
        }

        // If a spot couldn't be found after 30 tries, cancel this spawn to not pause the thread
        return null;
    }

    public boolean isFunky() {return funky;}

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
                spawnCountMultiplier = 1.5;
                size = 20;
                speed = 8;
                secondsBeforeSpawn = 3;
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
                spawnCountMultiplier = 500.0;
                size = 5;
                speed = 35;
                secondsBeforeSpawn = 1;
            }
        }
    }

    public void setSpawnHeight(int newSpawnHeight) {
        this.spawnHeight = newSpawnHeight;
    }
}
