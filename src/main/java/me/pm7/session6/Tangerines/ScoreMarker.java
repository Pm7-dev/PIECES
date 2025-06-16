package me.pm7.session6.Tangerines;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScoreMarker {
    private static final Session6 plugin = Session6.getPlugin();

    private static List<ScoreMarker> markers;
    public static void startloop() {Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
        if(markers == null) markers = new ArrayList<>();

        for(int i=0; i<markers.size(); i++) {
            if(markers.get(i).remove) {
                markers.get(i).kill();
                i--;
            }
        }

        for(ScoreMarker sm : markers) {
            sm.animate();
        }
    }, 0L, 1L);}

    private final TextDisplay display;
    private int ticks;
    public boolean remove;

    public ScoreMarker(Player p, Location loc) {
        if(markers == null) markers = new ArrayList<>();

        Random r = new Random();
        loc.add(r.nextDouble(-0.5, 0.5), 0.65, r.nextDouble(-0.5, 0.5));
        loc.setPitch(0);
        loc.setYaw(0);

        display = (TextDisplay) loc.getWorld().spawnEntity(loc, EntityType.TEXT_DISPLAY);
        display.setVisibleByDefault(false);
        p.showEntity(plugin, display);
        display.setDefaultBackground(false);
        display.setText("+1");
        display.setBillboard(Display.Billboard.VERTICAL);
        Color color = Color.fromARGB(0, 0, 0, 0);
        display.setBackgroundColor(color);
        display.setSeeThrough(true);
        display.setTeleportDuration(1);
        display.setInterpolationDuration(1);
        display.setInterpolationDelay(0);
        display.setBrightness(new Display.Brightness(15, 15));

        ticks = 0;

        markers.add(this);
    }

    private void animate() {
        ticks++;

        display.setInterpolationDelay(0);
        Location loc = display.getLocation().clone();
        loc.add(0, getChange(ticks), 0);
        display.setTextOpacity((byte) (260 - (250 * ((double)ticks/20))));

        display.teleport(loc);

        if(ticks >= 20) {
            remove = true;
        }

    }

    private void kill() {
        markers.remove(this);
        display.remove();
    }

    private double getChange(int ticks) {
        if(ticks==0) return 0;
        return (Math.pow(ticks, 0.33) - Math.pow(ticks-1, 0.33))/2;
    }
}
