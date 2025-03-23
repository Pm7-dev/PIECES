package me.pm7.session6.Pieces;

import me.pm7.session6.Pieces.Color.PieceColor;
import me.pm7.session6.Pieces.Color.PieceColorPattern;
import me.pm7.session6.Session6;
import me.pm7.session6.Utils.AreaCalculator;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.entity.Player;
import org.bukkit.spawner.Spawner;

import java.util.*;
import java.util.logging.Level;

// You'd think I'd be satisfied with the first pun of "Piece Keeper," but no, I need more
public class PieceMaker {
    private static final Session6 plugin = Session6.getPlugin();

    private int spawnHeight;
    private SpawnerDifficulty difficulty;
    private SpawnerDifficulty previousDifficulty;
    private boolean funky = false;
    private Integer taskID;
    private int spawnTick; // used to keep track of the time before the next group of pieces spawn
    private final Random random;
    private boolean cancelFunk = true;

    public PieceMaker() {
        spawnHeight = 190; //190

        this.difficulty = SpawnerDifficulty.LEVEL_1;
        this.previousDifficulty = null;

        taskID = null;
        this.spawnTick = difficulty.getSecondsBetweenSpawns();
        this.random = new Random();
    }

    public void start() {
        if(taskID != null) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            stormWatch("The forecast for the next few hours is looking quite dire. As strange as it may sound, it is in your best interest to STAY OUTDOORS. Good luck.");
        }, 140L);
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::loop, 220L, 20L);
    }

    public void stop() {
        if(taskID == null) return;
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
    }

    private int boringTimer = 0; // Counts up to Funky Mode
    private static final int FUNKY_TIME = 300;
    private void loop() {

        // This became such a mess super quickly lol
        if(!cancelFunk) {
            if (boringTimer <= FUNKY_TIME + 5) boringTimer++;
            if (boringTimer == FUNKY_TIME - 3) {
                serverMessageAndSound("3", "pieces:funk.funk_loading", false);
                for (Player p : Bukkit.getOnlinePlayers()) p.closeInventory();
            }
            if (boringTimer == FUNKY_TIME - 2) serverMessageAndSound("2", "pieces:funk.funk_loading", false);
            if (boringTimer == FUNKY_TIME - 1) serverMessageAndSound("1", "pieces:funk.funk_loading", false);
            if (boringTimer == FUNKY_TIME) {
                funky = true;
                serverMessageAndSound("Funky mode activated!", "pieces:funk.funk_activate", true);
            }
            if (boringTimer == FUNKY_TIME + 4) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    BaseComponent[] component = new ComponentBuilder()
                            .append("[Funky Mode]: Confused? ").color(ChatColor.GOLD.asBungee())
                            .append("Click to check our FAQ!").color(ChatColor.GOLD.asBungee()).underlined(true).bold(true)
                            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/thatonething"))
                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to see the Funky Mode FAQ!").color(ChatColor.GREEN.asBungee()).create()))
                            .create();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(component);
                        Location soundLoc = p.getLocation().clone().add(0, 500, 0);
                        p.playSound(soundLoc, "pieces:funk.faq", 99999, 1);
                    }
                }, 10L);
            }
        }

        tickPieceSpawn();
        tickDifficulty();
    }

    private void serverMessageAndSound(String message, String sound, boolean hold) { //Plays a sound to the entire server
        for(Player p : Bukkit.getOnlinePlayers()) {
            Location soundLoc = p.getLocation().clone().add(0, 500, 0);
            p.playSound(soundLoc, sound, 99999, 1);
            if(hold) p.sendTitle("", ChatColor.GREEN + message, 0, 30, 20);
            else p.sendTitle("", ChatColor.GREEN + message, 0, 0, 25);
        }
    }

    private void tickPieceSpawn() {
        if(spawnTick <= 0) {

            List<Location> pLocs = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if(p.getGameMode() != GameMode.SPECTATOR) pLocs.add(p.getLocation());
            }
            if(pLocs.isEmpty()) return;

            double area = AreaCalculator.getTotalArea(pLocs);
            double spawnCount = area/Math.pow(AreaCalculator.BOUNDING_BOX_SIZE, 2);
            int finalSpawnCount = (int) (spawnCount * difficulty.getSpawnMultiplier());
            for(int i=0; i<finalSpawnCount; i++) {
                Piece piece = createRandomPiece();
                if (piece != null) spawnTick = difficulty.getSecondsBetweenSpawns();
            }
        }
        spawnTick--;
    }

    private final int secondsBeforeDifficultyShift = 2200;
    private int secondsBeforeNextDifficultyShift = secondsBeforeDifficultyShift;
    private final int secondsBeforeAnomaly = 1100;
    private int secondsBeforeNextAnomaly = secondsBeforeAnomaly;
    private int anomalyCount = 0;
    private void tickDifficulty() {
        if(secondsBeforeNextDifficultyShift <= 0) {
            secondsBeforeNextDifficultyShift = secondsBeforeDifficultyShift;

            int current = getDifficulty();
            if(current == -1) return;
            if(current >= 6) return;

            SpawnerDifficulty newDifficulty = SpawnerDifficulty.fromInt(current + 1);
            if(newDifficulty == null) {
                plugin.getLogger().log(Level.WARNING, "ERROR! Next difficulty is null!");
                return;
            }

            // If a difficulty tick happens during an anomaly, set the stashed difficulty to the upgraded difficulty
            if(difficulty.getDifficultyNumber() != null) {
                setDifficulty(SpawnerDifficulty.fromInt(current + 1));
            } else {
                previousDifficulty = SpawnerDifficulty.fromInt(current + 1);
            }
        }

        if(secondsBeforeNextAnomaly <= 0) {
            anomalyCount++;
            secondsBeforeNextAnomaly = secondsBeforeAnomaly;
            this.previousDifficulty = difficulty;
            final SpawnerDifficulty anomaly;
            switch (anomalyCount) {
                case 1:
                case 3: {
                    anomaly = SpawnerDifficulty.ANOMALY_SPEED;
                    break;
                }
                case 2:
                case 4: {
                    anomaly = SpawnerDifficulty.ANOMALY_SCALE;
                    break;
                }
                default: {
                    anomaly = SpawnerDifficulty.ANOMALY_TOTAL_CHAOS;
                    break;
                }
            }
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation().clone().add(0, 200, 0), "pieces:anomaly", 999999, 1);
                p.sendTitle(ChatColor.RED + "", ChatColor.RED + "Storm Anomaly detected!", 0, 5, 45);
            }

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                stormWatch(ChatColor.RED + "The incoming anomaly has been designated type \"" + anomaly.getName() + "\" Be prepared and stay safe.");
            }, 80L);

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                difficulty = anomaly;

                // Reset the difficulty after a randomized time between 45 and 66 seconds
                long time = random.nextInt(45, 66);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    difficulty = previousDifficulty;

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        stormWatch(ChatColor.RED + "The anomaly appears to be easing up");
                    }, 65L);
                }, time * 20);
            }, 240L);
        }
        secondsBeforeNextDifficultyShift--;
        secondsBeforeNextAnomaly--;
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
            int size = difficulty.getRandomSize();
            double speed = difficulty.getRandomSpeed();

            PieceColor color; // COLOR !!
            if(!funky) color = PieceColorPattern.getRandomBoring().getColor();
            else color = PieceColorPattern.getRandom().getColor();

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
                int checkX = (int) (piece.getX() - 0.5);
                int checkZ = (int) (piece.getZ() - 0.5);
                if(piece.isRunning()) continue; // only run for pieces that are also spawning
                int totalLength = model.length * size;
                int pieceTotalLength = piece.getModelData().length * piece.getSize();
                if((x<=checkX && x+totalLength>checkX) || (x>=checkX && x<checkX+pieceTotalLength)) {
                    if((z<=checkZ && z+totalLength>checkZ) || (z>=checkZ && z<checkZ+pieceTotalLength)) {
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

    public int getDifficulty() {
        if(difficulty.getDifficultyNumber() == null) {
            if(previousDifficulty.getDifficultyNumber() == null) return -1;
            return previousDifficulty.getDifficultyNumber();
        }
        return difficulty.getDifficultyNumber();
    }
    public void setDifficulty(SpawnerDifficulty difficulty) {this.difficulty = difficulty;}

    public void setSpawnHeight(int newSpawnHeight) {
        this.spawnHeight = newSpawnHeight;
    }

    public void stormWatch(String message) {
        Bukkit.broadcastMessage(ChatColor.RED + "[Storm Watch]: " + message);
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation().clone().add(0, 200, 0), "pieces:storm_watch_notif", 999999, 1);
        }
    }

    public void cancelTheFunk() {
        cancelFunk = true;
        if(funky) {
            Bukkit.broadcastMessage(ChatColor.RED + "Canceling funky mode for lag reasons :(");
            funky = false;
        }
    }
}
