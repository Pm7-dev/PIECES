package me.pm7.storm.Pieces;

import me.pm7.storm.Storm;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Random;

public class SpawnerDifficulty {
    private static final Storm plugin = Storm.getPlugin();

    private static final Random random = new Random();
    public int secondsBetweenSpawns;
    public double spawnMultiplier;
    public int minSize;
    public int maxSize;
    public double minSpeed;
    public double maxSpeed;

    SpawnerDifficulty(int secondsBetweenSpawns, double spawnMultiplier, int minSize, int maxSize, double minSpeed, double maxSpeed) {
        this.secondsBetweenSpawns = secondsBetweenSpawns;
        this.spawnMultiplier = spawnMultiplier;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    public int getRandomSize() {
        if(minSize==maxSize) return minSize;
        return random.nextInt(minSize, maxSize + 1);
    }
    public double getRandomSpeed() {
        if(minSpeed==maxSpeed) return minSpeed;
        return (random.nextDouble(minSpeed, maxSpeed));
    }

    public void saveToConfig(ConfigurationSection section) {
        if(section == null) {
            section = plugin.getConfig().createSection("difficulty");
        }

        section.set("secondsBetweenSpawns", secondsBetweenSpawns);
        section.set("spawnMultiplier", spawnMultiplier);
        section.set("minSize", minSize);
        section.set("maxSize", maxSize);
        section.set("minSpeed", minSpeed);
        section.set("maxSpeed", maxSpeed);

        plugin.saveConfig();
    }

    public static SpawnerDifficulty loadFromConfig(ConfigurationSection section) {
        if(section == null) {
            SpawnerDifficulty difficulty = new SpawnerDifficulty(
                    5,
                    1.0,
                    9,
                    12,
                    5,
                    7
            );
            difficulty.saveToConfig(plugin.getConfig().getConfigurationSection("difficulty"));
            return difficulty;
        }

        return new SpawnerDifficulty(
                section.getInt("secondsBetweenSpawns"),
                section.getDouble("spawnMultiplier"),
                section.getInt("minSize"),
                section.getInt("maxSize"),
                section.getDouble("minSpeed"),
                section.getDouble("maxSpeed")
        );
    }
}
