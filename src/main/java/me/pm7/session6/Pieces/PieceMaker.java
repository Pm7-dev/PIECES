package me.pm7.session6.Pieces;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;

// You'd think I'd be satisfied with the first pun of "Piece Keeper," but no, I need more
public class PieceMaker {
    private static final Session6 plugin = Session6.getPlugin();

    private int spawnY;
    private int minSize;
    private int maxSize;
    private Integer taskID;

    public PieceMaker() {
        spawnY = 250;
        taskID = null;
    }

    public void start() {
        if(taskID != null) return;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::loop, 0L, 1L);
    }

    public void stop() {
        if(taskID == null) return;
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
    }

    private void loop() {

    }
}
