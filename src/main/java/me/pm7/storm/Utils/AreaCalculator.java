package me.pm7.storm.Utils;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.*;
import java.awt.Point;

// This was initially going to be a much larger class that was more efficient, but I got lazy and did this stupid approach

public class AreaCalculator {
    private static final int INACCURACY = 1; // improves performance at the cost of area accuracy (might be broken, works at 1)
    public static final int BOUNDING_BOX_SIZE = 160;
    private static final int HALF_SIZE = BOUNDING_BOX_SIZE/INACCURACY/2;

    public static int getTotalArea(List<Location> centers) {
        Set<Point> overworldPairs = new HashSet<>();
        Set<Point> netherPairs = new HashSet<>();
        Set<Point> endPairs = new HashSet<>();
        Set<Point> otherPairs = new HashSet<>();
        for (Location center : centers) {
            int x = center.getBlockX()/INACCURACY;
            int z = center.getBlockZ()/INACCURACY;

            for(int x1=x-HALF_SIZE; x1<x+HALF_SIZE; x1++) {
                for(int z1=z-HALF_SIZE; z1<z+HALF_SIZE; z1++) {
                    World.Environment env = center.getWorld().getEnvironment();
                    switch (env) {
                        case NORMAL -> overworldPairs.add(new Point(x1, z1));
                        case NETHER -> netherPairs.add(new Point(x1, z1));
                        case THE_END -> endPairs.add(new Point(x1, z1));
                        default -> otherPairs.add(new Point(x1, z1));
                    }
                }
            }
        }
        return (int) ((overworldPairs.size() * Math.pow(INACCURACY, 2)) + (netherPairs.size() * Math.pow(INACCURACY, 2)) + (endPairs.size() * Math.pow(INACCURACY, 2)) + (otherPairs.size() * Math.pow(INACCURACY, 2)));
    }
}
