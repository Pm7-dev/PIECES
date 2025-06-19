package me.pm7.storm.Pieces;

import me.pm7.storm.Storm;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.*;

public class Piece {
    private static final Storm plugin = Storm.getPlugin();

    // The materials that pieces can be made out of
    private static final List<BlockData> colors = List.of(
            //Material.SHULKER_BOX.createBlockData(), missing something somewhere; not finding it.
            Material.BLACK_SHULKER_BOX.createBlockData(),
            Material.BLUE_SHULKER_BOX.createBlockData(),
            Material.BROWN_SHULKER_BOX.createBlockData(),
            Material.CYAN_SHULKER_BOX.createBlockData(),
            Material.GRAY_SHULKER_BOX.createBlockData(),
            Material.GREEN_SHULKER_BOX.createBlockData(),
            Material.LIGHT_BLUE_SHULKER_BOX.createBlockData(),
            Material.LIGHT_GRAY_SHULKER_BOX.createBlockData(),
            Material.LIME_SHULKER_BOX.createBlockData(),
            Material.MAGENTA_SHULKER_BOX.createBlockData(),
            Material.ORANGE_SHULKER_BOX.createBlockData(),
            Material.PINK_SHULKER_BOX.createBlockData(),
            Material.PURPLE_SHULKER_BOX.createBlockData(),
            Material.RED_SHULKER_BOX.createBlockData(),
            Material.WHITE_SHULKER_BOX.createBlockData(),
            Material.YELLOW_SHULKER_BOX.createBlockData(),
            Material.PRISMARINE_BRICKS.createBlockData(),
            Material.DARK_PRISMARINE.createBlockData(),
            Material.END_STONE.createBlockData(),
            Material.PURPUR_PILLAR.createBlockData(),
            Material.END_STONE_BRICKS.createBlockData(),
            Material.JIGSAW.createBlockData()
            //Material.PURPUR_BLOCK.createBlockData(), THIS IS THE INVISIBLE ONE
    );

    // Keeps track of the pieces that exist
    private static final List<Piece> pieces = new ArrayList<>();

    // Keeps track of the players that are currently underneath pieces
    private static final HashMap<UUID, Piece> playersUnderPieces = new HashMap<>();

    // Keeps track of the players that should be dead
    private static final List<UUID> dead = new ArrayList<>();

    // constants
    private static final int spawnTime = 55; // Defines how long it takes for the piece to scale up before dropping (in ticks)
    private static final NamespacedKey pieceID = new NamespacedKey(plugin, "piece-face-entity");
    private static final int TELEPORT_DURATION = 5; // Higher numbers are slightly more efficient, but seem to cause greater client desync, unless I can figure out how to fix it

    private final BlockData color;
    private final int size;
    private final double x, z, speed;
    private double y;
    private final double voiceDistance;
    private final boolean[][] modelData;
    private final World world;
    private final List<UUID> faces;
    private boolean running;
    private boolean remove = false;
    private int spawnAnimationTicks = 0; // counts how many ticks the piece has been spawning for
    private final Random random;
    private final HashMap<UUID, Integer> endAnimationDestroyTick;

    public Piece(World world, int x, double y, int z, int size, double speed, boolean[][] modelData) {
        this.world = world;
        this.x = x + 0.5d;
        this.y = y;
        this.z = z + 0.5d;
        this.size = size;
        this.speed = speed;
        this.modelData = modelData;
        this.faces = new ArrayList<>();
        this.voiceDistance = (size * 1.2) + 8;
        this.random = new Random();
        this.endAnimationDestroyTick = new HashMap<>();

        this.color = colors.get((int) (random.nextDouble() * colors.size()));

        //Load all the chunks that this entity will be in
        for(double cx = x; cx < x+(modelData.length*size); cx+=16) {
            for(double cz = z; cz < z+(modelData.length*size); cz+=16) {
                Chunk chunk = world.getChunkAt((int) (cx/16), (int) (cz/16));
                if(!chunk.getPluginChunkTickets().contains(plugin)) chunk.addPluginChunkTicket(plugin);
            }
        }

        // Create all the piece's faces
        for (int i = 0; i < modelData.length; i++) {
            for (int j = 0; j < modelData.length; j++) {
                if (modelData[i][j]) { //REMEMBER it is Z and THEN X
                    spawnFace(this.x + (j * size), this.z + (i * size));
                }
            }
        }

        // Start the spawning animation
        spawnAnimationTicks = 0;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(spawnAnimationTicks > spawnTime) {
                    cancel();
                    return;
                }

                float currentSize = (float) ((1 - Math.pow(1 - ((double) spawnAnimationTicks/spawnTime), 3))*(size+0.01d));
                for(UUID uuid : faces) {
                    BlockDisplay face = (BlockDisplay) Bukkit.getEntity(uuid);
                    if (face == null) continue;
                    face.setInterpolationDelay(0);
                    face.setTransformation(new Transformation(new Vector3f(-currentSize / 2, -currentSize / 2, -currentSize / 2), new AxisAngle4f(), new Vector3f(currentSize, currentSize, currentSize), new AxisAngle4f()));
                }

                spawnAnimationTicks++;
            }
        }.runTaskTimer(plugin, 2L, 1L);

        // Start the piece after all the animations are done
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {

            // running! yay
            this.running = true;
        }, spawnTime + 8);

        pieces.add(this);
    }

    private void spawnFace(double x, double z) {
        Location spawnLoc = new Location(world, x + ((double) size / 2), y + ((double) size / 2), z + ((double) size / 2));

        spawnLoc.setYaw(0);
        spawnLoc.setPitch(0);
        BlockDisplay face = (BlockDisplay) world.spawnEntity(spawnLoc, EntityType.BLOCK_DISPLAY);
        face.getPersistentDataContainer().set(pieceID, PersistentDataType.BOOLEAN, true);

        face.setBlock(color);
        face.setShadowStrength(0);
        face.setBrightness(new Display.Brightness(15, 15));

        face.setViewRange(999999999);
        face.setTransformation(new Transformation(new Vector3f(0, 0, 0), new AxisAngle4f(), new Vector3f(0, 0, 1), new AxisAngle4f()));
        face.setInterpolationDelay(0); // 1?
        face.setInterpolationDuration(1);
        face.setTeleportDuration(TELEPORT_DURATION);

        // It's a surprise tool that will help us later
        endAnimationDestroyTick.put(face.getUniqueId(), random.nextInt(175, 245));

        faces.add(face.getUniqueId());
    }

    // Moves every face down lol
    int soundTick = 0;
    int teleportTick = 0;
    public void moveDown() {
        // Code that moves the piece down (also plays the ending animation flashing)
        double movementPerTick = speed/20;
        y-=movementPerTick;

        teleportTick++;
        if(teleportTick==TELEPORT_DURATION) teleportTick = 0;

        // If the piece is fully below the world, remove it
        if(y < -65-size) remove = true;

        // Check for collisions of all kinds! (Ambience sound, warning detection, and kill hitbox detection)
        double halfSize = (modelData.length * size)/2d;
        double centerX = x + halfSize;
        double centerZ = z + halfSize;
        for(Player p : Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getUniqueId();

            if(!p.getWorld().getUID().equals(getWorld().getUID())) {
                if(playersUnderPieces.containsKey(uuid)) {
                    if(playersUnderPieces.get(uuid).equals(this)) {
                        playersUnderPieces.remove(uuid);
                    }
                }
                continue;
            }

            double footY = p.getLocation().getY(); // If the player is fully above this piece, stop calculations
            if(footY > y + size) continue;

            Location head = p.getEyeLocation();
            double pX = head.getX();
            double pZ = head.getZ();
            double headY = head.getY();
            double hitboxBuffer = p.getBoundingBox().getWidthX()/2;

            double centerDistX = Math.abs(centerX-pX);
            double centerDistZ = Math.abs(centerZ-pZ);

            // If the player is completely out of range of this piece, stop running code
            if(centerDistX > voiceDistance + halfSize|| centerDistZ > voiceDistance + halfSize) {
                if(playersUnderPieces.containsKey(uuid)) {
                    if(playersUnderPieces.get(uuid).equals(this)) {
                        playersUnderPieces.remove(uuid);
                    }
                }
                continue;
            }

            double yDist = y-headY;
            boolean playerIsUnder = false;
            for(int row = 0; row < modelData.length; row++) {
                for(int col = 0; col < modelData[row].length; col++) {
                    if(!modelData[row][col]) continue;
                    double mX = x+(size/2d) + (col*size);
                    double mZ = z+(size/2d) + (row*size);

                    double xDist = mX-pX;
                    double zDist = mZ-pZ;

                    // If the player's ears are close to the block, play the ambience
                    if(soundTick == 0) {
                        if (Math.abs(yDist) - (size / 2d) < voiceDistance && Math.abs(xDist) - (size / 2d) < voiceDistance && Math.abs(zDist) - (size / 2d) < voiceDistance) {
                            double distance = Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));
                            distance -= (double) size / 2;
                            if (distance < 0) distance = 0;
                            float volume = (float) (1.0f - (distance / (voiceDistance)));

                            if (volume > 0) {
                                Location soundLoc = new Location(getWorld(), mX, y + (size * 0.25), mZ);
                                p.playSound(soundLoc, "pieces:piece.ambience", SoundCategory.RECORDS, volume + 1.2f, 0.8f);
                            }
                        }
                    }

                    // If the player is directly under a piece, add them to the list of people to alert
                    if(yDist > 0 && Math.abs(xDist) <= (size/2d) + hitboxBuffer && Math.abs(zDist) <= (size/2d) + hitboxBuffer) {
                        playerIsUnder = true;
                        if(playersUnderPieces.containsKey(uuid)) {
                            if(!playersUnderPieces.get(uuid).equals(this)) {
                                double currentY = playersUnderPieces.get(uuid).getY();
                                if(currentY > y) {
                                    playersUnderPieces.put(uuid, this);
                                }
                            }
                        } else {
                            playersUnderPieces.put(uuid, this);
                        }
                    }

                    // If the player is touching a piece, it's time for death!
                    if(Math.abs(xDist) < (size/2d) + hitboxBuffer && Math.abs(zDist) < (size/2d) + hitboxBuffer) {
                        if((footY >= y && footY <= y + size) || (headY >= y && headY <= y + size)) {

                            HashMap<UUID, Integer> pieceInvincibilityTicks = PieceKeeper.getPieceInvincibilityTicks();
                            if(pieceInvincibilityTicks.containsKey(p.getUniqueId())) {
                                if(pieceInvincibilityTicks.get(p.getUniqueId()) > 0) continue;
                            }

                            dead.add(p.getUniqueId());
                            p.damage(99999);
                            continue;
                        }
                    }
                    dead.remove(p.getUniqueId());
                }
            }

            if(!playerIsUnder && playersUnderPieces.containsKey(uuid)) {
                if(playersUnderPieces.get(uuid).equals(this)) {
                    playersUnderPieces.remove(uuid);
                }
            }
        }

        soundTick++;
        if(soundTick >= 3) soundTick = 0;
    }
    public boolean shouldRemove() {return remove;}

    public double getX() {return x;}
    public double getZ() {return z;}
    public double getY() {return y;}
    public int getSize() {return size;}
    public World getWorld() {return world;}
    public boolean[][] getModelData() {return modelData;}
    public boolean isRunning() {return running;}
    public static List<Piece> getPieces() {return pieces;}
    public static NamespacedKey getPieceID() {return pieceID;}
    public double getSpeed() {return speed;}

    public static List<UUID> getDead() {return dead;}
    public static HashMap<UUID, Piece> getPlayersUnderPieces() {return playersUnderPieces;}

    public void kill() {

        // This piece is no longer running
        running = false;
        pieces.remove(this);

        // Kill all the faces
        for(UUID uuid : faces) {
            BlockDisplay face = (BlockDisplay) Bukkit.getEntity(uuid);
            if(face == null) continue;
            face.remove();
        }
        faces.clear();

        if(!plugin.isEnabled()) return;

        // Stop force loading the chunks that keep this piece loaded (as long as no other piece is occupying the chunk)
        for(double cx = x; cx < x+(modelData.length*size); cx+=16) {
            for(double cz = z; cz < z+(modelData.length*size); cz+=16) {
                Chunk chunk = world.getChunkAt((int) (cx/16), (int) (cz/16));

                boolean remove = true;
                if(!chunk.getPluginChunkTickets().contains(plugin)) {
                    for(Entity e : chunk.getEntities()) {
                        PersistentDataContainer c = e.getPersistentDataContainer();
                        if(c.has(pieceID, PersistentDataType.BOOLEAN) && Boolean.TRUE.equals(c.get(pieceID, PersistentDataType.BOOLEAN))) {
                            remove = false;
                        }
                    }
                }
                if(remove) chunk.removePluginChunkTicket(plugin);
            }
        }
    }
}
