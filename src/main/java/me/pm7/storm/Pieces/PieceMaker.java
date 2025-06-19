package me.pm7.storm.Pieces;

import me.pm7.storm.Storm;
import me.pm7.storm.Utils.AreaCalculator;
import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.entity.Player;

import java.util.*;

// You'd think I'd be satisfied with the first pun of "Piece Keeper," but no, I need more
public class PieceMaker {
    private static final Storm plugin = Storm.getPlugin();

    private final Random random;

    private int spawnHeight;
    private SpawnerDifficulty difficulty;
    private Integer taskID;
    private int spawnTick; // used to keep track of the time before the next group of pieces spawn

    public PieceMaker() {
        spawnHeight = 210; //190

        // These values mimic the "difficulty 1" in session
        this.difficulty = SpawnerDifficulty.loadFromConfig(plugin.getConfig().getConfigurationSection("difficulty"));

        taskID = null;
        this.spawnTick = difficulty.secondsBetweenSpawns;
        this.random = new Random();
    }

    public void start() {
        if(taskID != null) return;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::tickPieceSpawn, 40L, 20L);
    }

    public void stop() {
        spawnTick = 0;
        if(taskID == null) return;
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
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
            int finalSpawnCount = (int) (spawnCount * difficulty.spawnMultiplier);
            for(int i=0; i<finalSpawnCount; i++) {
                Piece piece = createRandomPiece();
                if (piece != null) spawnTick = difficulty.secondsBetweenSpawns;
            }
        }
        spawnTick--;
    }

    private Piece createRandomPiece() {
        for(int i=0; i<30; i++) {

            // Generate the data of the piece we are making
            PieceType type;
            type = PieceType.getRandom();
            boolean[][] model = type.getModel();
            model = PieceType.rotateModel(model, random.nextInt(4));
            model = PieceType.mirrorModel(model, Mirror.values()[random.nextInt(Mirror.values().length)]);
            int size = difficulty.getRandomSize();
            double speed = difficulty.getRandomSpeed();

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

                                // This is the single worst if statement ever made.
                                if((x+(sX*size)<=pC.getX()+(pcX*pC.getSize())&&x+((sX+1)*size)>pC.getX()+(pcX*pC.getSize()))||(x+(sX*size)>=pC.getX()+(pcX*pC.getSize())&&x+(sX*size)<pC.getX()+((pcX+1)*pC.getSize()))&&(z+(sZ*size)<=pC.getZ()+(pcZ*pC.getSize())&&z+((sZ+1)*size)>pC.getZ()+(pcZ*pC.getSize()))||(z+(sZ*size)>=pC.getZ()+(pcZ*pC.getSize())&&z+(sZ*size)<pC.getZ()+((pcZ+1)*pC.getSize()))) {
                                    collision = true;
                                    break;
                                }
                            }if(collision) break;
                        }if(collision) break;
                    }if(collision) break;
                }if(collision) break;
            }if(collision) continue; // break;ing the pattern


            return new Piece(spawn.getWorld(), x, y, z, size, speed, model);
        }

        // If a spot couldn't be found after 30 tries, cancel this spawn to not pause the thread
        return null;
    }

    public SpawnerDifficulty getDifficulty() {return difficulty;}
    public void setDifficulty(SpawnerDifficulty difficulty) {this.difficulty = difficulty;}

    public void setSpawnHeight(int newSpawnHeight) {
        this.spawnHeight = newSpawnHeight;
    }
}
