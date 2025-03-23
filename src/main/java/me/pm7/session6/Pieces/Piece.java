package me.pm7.session6.Pieces;

import me.pm7.session6.Pieces.Color.PieceColor;
import me.pm7.session6.Session6;
import me.pm7.session6.Utils.Direction;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.*;
import java.util.function.Predicate;

public class Piece {
    private static final Session6 plugin = Session6.getPlugin();

    // Keeps track of the pieces that exist
    private static final List<Piece> pieces = new ArrayList<>();

    // Keeps track of the players that are currently underneath pieces
    private static final HashMap<UUID, Piece> playersUnderPieces = new HashMap<>();

    // Keeps track of the players that should be dead
    private static final List<UUID> dead = new ArrayList<>();

    // constants
    private static final int spawnTime = 55; // Defines how long it takes for the piece to scale up before dropping (in ticks)
    private static final NamespacedKey pieceID = new NamespacedKey(plugin, "piece-face-entity");
    private static final int TELEPORT_DURATION = 10; // Higher numbers are slightly more efficient, but cause greater client desync

    private final int size;
    private final double x, z, speed;
    private double y;
    private final double voiceDistance;
    private final boolean[][] modelData;
    private final World world;
    private final List<UUID> faces;
    private final List<UUID> facesToRemove;
    private final PieceColor color;
    private boolean running;
    private boolean remove = false;
    private int spawnAnimationTicks = 0; // counts how many ticks the piece has been spawning for
    private final Random random;
    private final HashMap<UUID, Integer> endAnimationDestroyTick;

    public Piece(World world, int x, double y, int z, int size, double speed, boolean[][] modelData, PieceColor color) {
        this.world = world;
        this.x = x + 0.5d;
        this.y = y;
        this.z = z + 0.5d;
        this.size = size;
        this.speed = speed;
        this.color = color;
        this.modelData = modelData;
        this.faces = new ArrayList<>();
        this.facesToRemove = new ArrayList<>();
        this.voiceDistance = (size * 1.2) + 8;
        this.random = new Random();
        this.endAnimationDestroyTick = new HashMap<>();

        //Load all the chunks that this entity will be in
        for(double cx = x; cx < x+(modelData.length*size); cx+=16) {
            for(double cz = z; cz < z+(modelData.length*size); cz+=16) {
                Chunk chunk = world.getChunkAt((int) (cx/16), (int) (cz/16));
                if(!chunk.getPluginChunkTickets().contains(plugin)) chunk.addPluginChunkTicket(plugin);
            }
        }

        // Create all the piece's faces
        for(int i=0; i<modelData.length; i++) {
            for(int j=0; j<modelData.length; j++) {
                if(modelData[i][j]) { //REMEMBER it is Z and THEN X

                    // If this is an edge of the model, spawn a face normally, otherwise spawn a face that will be removed later
                    spawnFace(this.x+(j*size), this.z+(i*size), Direction.POSITIVE_Z, 0, i + 1 != modelData.length && modelData[i + 1][j]);
                    spawnFace(this.x+(j*size), this.z+(i*size), Direction.NEGATIVE_Z, 0, i - 1 != -1 && modelData[i - 1][j]);
                    spawnFace(this.x+(j*size), this.z+(i*size), Direction.POSITIVE_X, 0, j + 1 != modelData.length && modelData[i][j + 1]);
                    spawnFace(this.x+(j*size), this.z+(i*size), Direction.NEGATIVE_X, 0, j - 1 != -1 && modelData[i][j - 1]);

                    // Spawn the up/down face for each block
                    spawnFace(this.x+(j*size), this.z+(i*size), Direction.POSITIVE_X, 90, false);
                    spawnFace(this.x+(j*size), this.z+(i*size), Direction.POSITIVE_X, -90, false);
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

                float currentSize = (float) ((1 - Math.pow(1 - ((double) spawnAnimationTicks/spawnTime), 3))*size);
                for(UUID uuid : faces) {
                    TextDisplay face = (TextDisplay) Bukkit.getEntity(uuid);
                    if(face==null) continue;
                    face.setInterpolationDelay(0);
                    face.setTransformation(new Transformation(new Vector3f(-currentSize/2,-currentSize/2,currentSize/2),new AxisAngle4f(),new Vector3f(40*currentSize,2*currentSize,1),new AxisAngle4f()));
                }

                spawnAnimationTicks++;
            }
        }.runTaskTimer(plugin, 2L, 1L);

        // Start the piece after all the animations are done
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {

            // Remove the inner faces
            for(UUID uuid : facesToRemove) {
                Entity e = Bukkit.getEntity(uuid);
                if(e != null) e.remove();
                faces.remove(uuid);
            }
            facesToRemove.clear();

            for(UUID uuid : faces) {
                TextDisplay e = (TextDisplay) Bukkit.getEntity(uuid);
                if(e == null) continue;
                e.setInterpolationDelay(0);
                if(color.doesFading()) e.setInterpolationDuration(color.getFadeTicks());
                else e.setInterpolationDuration(0);
            }

            // running! yay
            this.running = true;
        }, spawnTime + 8);

        pieces.add(this);
    }

    private void spawnFace(double x, double z, Direction direction, float pitch, boolean remove) {
        Location spawnLoc = new Location(world, x+((double)size/2), y+((double)size/2), z+((double)size/2));
        spawnLoc.setYaw(direction.getCardinal());
        spawnLoc.setPitch(pitch);
        TextDisplay face = (TextDisplay) world.spawnEntity(spawnLoc, EntityType.TEXT_DISPLAY);
        face.getPersistentDataContainer().set(pieceID, PersistentDataType.BOOLEAN, true);

        face.setSeeThrough(false);
        face.setShadowed(false);
        face.setBrightness(new Display.Brightness(15, 15));
        face.setBillboard(Display.Billboard.FIXED);
        face.setDefaultBackground(false);
        face.setBackgroundColor(color.getColor());
        face.setAlignment(TextDisplay.TextAlignment.LEFT);
        face.setText("\n");

        face.setViewRange(999999999);
        face.setTransformation(new Transformation(new Vector3f(0,0,0),new AxisAngle4f(),new Vector3f(0,0,1),new AxisAngle4f()));
        face.setInterpolationDelay(0); // 1?
        face.setInterpolationDuration(1);
        face.setTeleportDuration(TELEPORT_DURATION);

        faces.add(face.getUniqueId());
        if(remove) facesToRemove.add(face.getUniqueId());
        else endAnimationDestroyTick.put(face.getUniqueId(), random.nextInt(176, 218));

    }

    // Moves every face down lol
    int soundTick = 0;
    int teleportTick = 0;
    public void moveDown() {

        // Multiplier used during the ending animation (will just be 1 the rest of the time)
        int endAnimationTick = plugin.getPieceKeeper().getEndAnimationTick();
        double multiplier = 1 - Math.sqrt(1 - Math.pow(((double) endAnimationTick/70) - 1, 2));
        if(!Double.isFinite(multiplier)) multiplier = 0;

        // Code that moves the piece down (also plays the ending animation flashing)
        double movementPerTick = (speed/20) * multiplier;
        if(teleportTick == 0 || endAnimationTick != 0) {
            for (UUID uuid : faces) {
                TextDisplay face = (TextDisplay) Bukkit.getEntity(uuid);
                if (face == null) continue;

                if(endAnimationTick>0) {
                    face.setTeleportDuration(1);
                }

                if (endAnimationTick <= 70) {
                    Location tpLoc = face.getLocation().clone();
                    if(endAnimationTick==0) tpLoc.setY(y - (movementPerTick * TELEPORT_DURATION) + (size/2d));
                    else tpLoc.setY(y - (movementPerTick) + (size/2d));
                    face.teleport(tpLoc);
                } else if (endAnimationTick <= 218) {
                    face.setDefaultBackground((random.nextInt(0, 3) == 0) == face.isDefaultBackground());
                    if(endAnimationTick > 175) {
                        if(endAnimationTick >= endAnimationDestroyTick.get(uuid)) {
                            face.remove();
                        }
                    }
                } else {
                    this.kill();
                    return;
                }
            }
        }
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

            if(!p.getWorld().getUID().equals(getWorld().getUID())) continue;

            double footY = p.getLocation().getY(); // If the player is fully above this piece, stop calculations
            if(footY > y + size) continue;

            UUID uuid = p.getUniqueId();
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
                                if (endAnimationTick >= 70) {
                                    if (endAnimationTick < 73) {
                                        p.playSound(soundLoc, "pieces:piece.broken_intro", volume * 2 + 1.2f, 0.9f);
                                    }
                                    p.playSound(soundLoc, "pieces:piece.broken", endAnimationTick <= 175 ? volume * 2 + 1.2f : (volume * 2 + 1.2f) * (1.0f - ((endAnimationTick - 175) / 45f)), 0.9f);
                                    //p.playSound(loc, "pieces:piece.bloop", volume * 2 + 1.2f, (random.nextFloat() * 1.2f) + 0.8f); this sounds so funny lol
                                } else p.playSound(soundLoc, "pieces:piece.ambience", (volume * 2 + 1.2f) * (1.0f - (endAnimationTick / 68f)), 0.8f);
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
                    if(endAnimationTick > 55) continue; // Don't run if the end animation is running
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

    public void tickColor() {
        color.tick();
        for(UUID uuid : faces) {
            TextDisplay face = (TextDisplay) Bukkit.getEntity(uuid);
            if(face==null) continue;
            if(face.getBackgroundColor() == null || !face.getBackgroundColor().equals(color.getColor())) {
                face.setInterpolationDelay(0);
                face.setBackgroundColor(color.getColor());
            }
        }
    }

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
            TextDisplay face = (TextDisplay) Bukkit.getEntity(uuid);
            if(face==null) continue;
            face.remove();
        }
        faces.clear();
        facesToRemove.clear();

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
                if(remove) chunk.addPluginChunkTicket(plugin);
            }
        }
    }
}
