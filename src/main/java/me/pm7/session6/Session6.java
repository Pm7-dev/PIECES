package me.pm7.session6;

import me.pm7.session6.Commands.test;
import me.pm7.session6.Pieces.PieceKeeper;
import me.pm7.session6.Pieces.PieceMaker;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;


public final class Session6 extends JavaPlugin {

    private static Session6 plugin;
    private static PieceKeeper pieceKeeper;
    private static PieceMaker pieceMaker;

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getCommand("test").setExecutor(new test());

        for(World world : Bukkit.getWorlds()) {
            if(world.getPluginChunkTickets().containsKey(this)) {
                for (Chunk chunk : world.getPluginChunkTickets().get(this)) {
                    chunk.removePluginChunkTicket(this);
                }
            }
        }

        pieceKeeper = new PieceKeeper();
        pieceKeeper.start();

        pieceMaker = new PieceMaker();
        pieceMaker.start(); //TODO: REMOVE

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Session6 getPlugin() {return plugin;}
    public static PieceKeeper getPieceKeeper() {return pieceKeeper;}
    public static PieceMaker getPieceMaker() {return pieceMaker;}
}
