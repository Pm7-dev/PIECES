package me.pm7.session6.LargeFernMinecart;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;

import java.util.ArrayList;
import java.util.List;

public class Pack {
    private static final Session6 plugin = Session6.getPlugin();
    private static final double SIGHT = 40.0d;

    private final int taskID;

    public static List<Pack> packs = new ArrayList<>();

    private final List<Mite> mites;
    private LivingEntity target;

    public Pack(Location loc, int count) {
        mites = new ArrayList<>();

        loc.setYaw(0);
        loc.setPitch(0);

        for(int i=0; i<count; i++) {
            mites.add(new Mite(loc, this));
        }

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::tick, 0L, 5L);

        packs.add(this);
    }

    private void tick() {
        for(int i=0; i<mites.size(); i++) {
            Mite m = mites.get(i);
            if(m.getMite() == null) continue;
            if(m.getMite().isDead()) {
                m.kill();
                i--;
                continue;
            }

            m.tick();
        }

        if(mites.isEmpty()) {
            kill();
            return;
        }

        Location avg = getAvgLocation();

        if(target != null) {
            Location tLoc = target.getLocation();
            double dist = Math.sqrt(Math.pow(avg.getX()-tLoc.getX(), 2) + Math.pow(avg.getY()-tLoc.getY(), 2) + Math.pow(avg.getZ()-tLoc.getZ(), 2));
            if(dist > SIGHT) {
                target = null;
                return;
            }

            for (Mite m : mites) {
                Silverfish s = m.getMite();
                if(s == null) continue;
                if(!s.isAware()) {m.getMite().setAware(true);}
                s.setTarget(target);
            }
        } else {
            for(Mite m : mites) {
                Silverfish s = m.getMite();
                if(s == null) continue;
                if(s.isAware()) {m.getMite().setAware(false);}
                s.setTarget(null);
            }

            double shortestDist = Double.MAX_VALUE;
            Player closest = null;
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.getGameMode() == GameMode.SPECTATOR) continue;
                if(p.getWorld() == avg.getWorld()) {
                    Location pLoc = p.getLocation();
                    double dist = Math.sqrt(Math.pow(avg.getX()-pLoc.getX(), 2) + Math.pow(avg.getY()-pLoc.getY(), 2) + Math.pow(avg.getZ()-pLoc.getZ(), 2));
                    if(dist < shortestDist) {
                        shortestDist = dist;
                        closest = p;
                    }
                }
            }

            if(shortestDist < SIGHT) {
                setTarget(closest);
            }
        }
    }

    public void setTarget(LivingEntity l) {
        target = l;
    }

    public List<Mite> getMites() {
        return mites;
    }

    private Location getAvgLocation() {
        double x = 0;
        double y = 0;
        double z = 0;
        int count = 0;
        for (Mite m : mites) {
            if(m.getMite() == null) continue;
            Location loc = m.getMite().getLocation();
            x += loc.getX();
            y += loc.getY();
            z += loc.getZ();
            count++;
        }
        x /= count;
        y /= count;
        z /= count;

        return new Location(Bukkit.getWorlds().getFirst(), x, y, z);
    }

    public void kill() {
        Bukkit.getScheduler().cancelTask(taskID);
        packs.remove(this);
        while(!mites.isEmpty()) {
            mites.getFirst().kill();
            mites.removeFirst();
        }
    }
}
