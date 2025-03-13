package me.pm7.session6;

import me.pm7.session6.Commands.*;
import me.pm7.session6.Listener.ConnectionListener;
import me.pm7.session6.Listener.DeathListener;
import me.pm7.session6.Pieces.Piece;
import me.pm7.session6.Pieces.PieceKeeper;
import me.pm7.session6.Pieces.PieceMaker;
import me.pm7.session6.Utils.AnimationController;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public final class Session6 extends JavaPlugin {

    private static Session6 plugin;
    private static PieceKeeper pieceKeeper;
    private static PieceMaker pieceMaker;
    private static AnimationController animationController;

    @Override
    public void onEnable() {
        plugin = this;
        getCommand("test").setExecutor(new test());
        getCommand("raisetheroof").setExecutor(new raisetheroof());
        getCommand("stormdifficulty").setExecutor(new stormdifficulty());
        getCommand("stormdifficulty").setTabCompleter(new stormdifficulty());
        getCommand("startwildcard").setExecutor(new startwildcard());
        getCommand("thatonething").setExecutor(new thatonething());
        getCommand("stopwildcard").setExecutor(new stopwildcard());
        getServer().getPluginManager().registerEvents(new DeathListener(), plugin);
        getServer().getPluginManager().registerEvents(new ConnectionListener(), plugin);

        for(World world : Bukkit.getWorlds()) {
            if(world.getPluginChunkTickets().containsKey(this)) {
                for (Chunk chunk : world.getPluginChunkTickets().get(this)) {
                    chunk.removePluginChunkTicket(this);
                }
            }

            // Kill any remaining piece faces
            List<Entity> remove = new ArrayList<>();
            for(Entity e : world.getEntities()) {
                PersistentDataContainer c = e.getPersistentDataContainer();
                if(c.has(Piece.getPieceID(), PersistentDataType.BOOLEAN) && Boolean.TRUE.equals(c.get(Piece.getPieceID(), PersistentDataType.BOOLEAN))) {
                    remove.add(e);
                }
            }
            for(Entity e : remove) e.remove();
        }

        animationController = new AnimationController();
        getServer().getPluginManager().registerEvents(animationController, plugin);
        animationController.start();

        pieceKeeper = new PieceKeeper();
        pieceKeeper.start();

        pieceMaker = new PieceMaker();
    }

    @Override
    public void onDisable() {
        pieceMaker.stop();
        List<Piece> pieces = Piece.getPieces();
        while (!pieces.isEmpty()) pieces.getFirst().kill();
    }

    public static Session6 getPlugin() {return plugin;}
    public PieceKeeper getPieceKeeper() {return pieceKeeper;}
    public PieceMaker getPieceMaker() {return pieceMaker;}
    public AnimationController getAnimationController() {return animationController;}

    private static boolean started = false;
    public boolean isStarted() {return started;}
    public void start() {started = true;}
}
