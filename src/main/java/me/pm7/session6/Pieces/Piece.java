package me.pm7.session6.Pieces;

import me.pm7.session6.Pieces.Color.PieceColor;
import me.pm7.session6.Session6;
import me.pm7.session6.Utils.Direction;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Piece {
    private static final Session6 plugin = Session6.getPlugin();

    // Keeps track of the pieces that exist
    private static final List<Piece> pieces = new ArrayList<>();

    // constant(s)
    private static final int spawnTime = 55; // Defines how long it takes for the piece to scale up before dropping (in ticks)

    private final int x, z, size;
    private final double speed;
    private final boolean[][] modelData;
    private final World world;
    private double y;
    private final List<UUID> faces;
    private final List<UUID> facesToRemove;
    private final PieceColor color;
    private boolean running;
    private int spawnAnimationTicks = 0; // counts how many ticks the piece has been spawning for

    public Piece(World world, int x, int z, int size, double speed, boolean[][] modelData, PieceColor color) {
        this.world = world;
        this.x = x;
        this.z = z;
        this.y = PieceMaker.getSpawnY();
        this.size = size;
        this.speed = speed;
        this.color = color;
        this.modelData = modelData;
        this.faces = new ArrayList<>();
        this.facesToRemove = new ArrayList<>();

        //Load all the chunks that this entity will be in
        for(int cx = 0; cx < 1 + (x/16); cx++) {
            for(int cz = 0; cz < 1 + (z/16); cz++) {
                world.addPluginChunkTicket((x/16) + cx, (z/16) + cz, plugin);
            }
        }

        // Create all the piece's faces
        for(int i=0; i<modelData.length; i++) {
            for(int j=0; j<modelData.length; j++) {
                if(modelData[i][j]) { //REMEMBER it is Z and THEN X

                    // If this is an edge of the model, spawn a face normally, otherwise spawn a face that will be removed later
                    spawnFace(x+(j*size), z+(i*size), Direction.POSITIVE_Z, 0, i + 1 != modelData.length && modelData[i + 1][j]);
                    spawnFace(x+(j*size), z+(i*size), Direction.NEGATIVE_Z, 0, i - 1 != -1 && modelData[i - 1][j]);
                    spawnFace(x+(j*size), z+(i*size), Direction.POSITIVE_X, 0, j + 1 != modelData.length && modelData[i][j + 1]);
                    spawnFace(x+(j*size), z+(i*size), Direction.NEGATIVE_X, 0, j - 1 != -1 && modelData[i][j - 1]);

                    // Spawn the up/down face for each block
                    spawnFace(x+(j*size), z+(i*size), Direction.POSITIVE_X, 90, false);
                    spawnFace(x+(j*size), z+(i*size), Direction.POSITIVE_X, -90, false);
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

            // Remove all the inner faces
            for(UUID uuid : facesToRemove) {
                Entity e = Bukkit.getEntity(uuid);
                if(e != null) e.remove();
                faces.remove(uuid);
            }
            facesToRemove.clear();

            // If the color isn't supposed to fade, don't let the client do smooth interpolation
            if(!color.doesFading()) {
                for(UUID uuid : faces) {
                    TextDisplay e = (TextDisplay) Bukkit.getEntity(uuid);
                    if(e == null) continue;
                    e.setInterpolationDuration(0);
                }
            }

            //TODO: sounds

            // running! yay
            this.running = true;
        }, spawnTime + 5);

        pieces.add(this);
    }

    private void spawnFace(int x, int z, Direction direction, float pitch, boolean remove) {
        Location spawnLoc = new Location(world, x+((double)size/2), y+((double)size/2), z+((double)size/2));
        spawnLoc.setYaw(direction.getCardinal());
        spawnLoc.setPitch(pitch);
        TextDisplay face = (TextDisplay) world.spawnEntity(spawnLoc, EntityType.TEXT_DISPLAY);

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
        face.setInterpolationDelay(0);
        face.setInterpolationDuration(1);
        face.setTeleportDuration(1);

        faces.add(face.getUniqueId());
        if(remove) facesToRemove.add(face.getUniqueId());
    }

    // Moves every face down lol
    public void moveDown() {
        y-=speed/20;
        for(UUID uuid : faces) {
            TextDisplay face = (TextDisplay) Bukkit.getEntity(uuid);
            if(face==null) continue;
            face.teleport(face.getLocation().clone().subtract(0, speed/20, 0));
        }
    }

    public void tickColor() {
        color.tick();
        for(UUID uuid : faces) {
            TextDisplay face = (TextDisplay) Bukkit.getEntity(uuid);
            if(face==null) continue;
            face.setBackgroundColor(color.getColor());
        }
    }

    public int getX() {return x;}
    public int getZ() {return z;}
    public double getCenterX() {return x + ((double) modelData.length /2) * size;}
    public double getCenterZ() {return z + ((double) modelData.length /2) * size;}
    public double getY() {return y;}
    public double getCenterY() {return y + (double) size /2;}
    public int getSize() {return size;}
    public boolean[][] getModelData() {return modelData;}
    public boolean isRunning() {return running;}
    public static List<Piece> getPieces() {return pieces;}

    public void kill() {

        // Remove the chunks that this piece keeps loaded
        for(int cx = 0; cx < 1 + (x/16); cx++) {
            for(int cz = 0; cz < 1 + (z/16); cz++) {
                world.removePluginChunkTicket((x/16) + cx, (z/16) + cz, plugin);
            }
        }

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

    }
}
